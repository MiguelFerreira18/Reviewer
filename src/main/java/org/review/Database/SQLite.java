package org.review.Database;

import org.review.Logger.Logger;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite implements DatabaseStrategy {

    private String databaseUrl;
    private String seed;
    private Connection connection;

    public SQLite(String databaseUrl, String seed) {
        this.databaseUrl = databaseUrl;
        this.seed = seed;
        connection = null;
    }

    public void startUp() throws Exception {
        connect();
        if (!isConnected()) {
            Logger.getInstance().error("Failed to connect to the database at " + databaseUrl);
            throw new Exception("Failed to connect to the database at " + databaseUrl);
        }
        seed();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(databaseUrl);
            if (connection != null) {
                Logger.getInstance().info("Connection to SQLite has been established.");
            } else {
                Logger.getInstance().error("Failed to make connection to SQLite database.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Logger.getInstance().info("Connection to SQLite has been closed.");
            }
        } catch (SQLException e) {
            Logger.getInstance().error("Error closing connection to SQLite: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            Logger.getInstance().error("Error checking connection status: " + e.getMessage());
            return false;
        }
    }

    public void seed() throws Exception {
        InputStream input = getClass().getClassLoader().getResourceAsStream(seed);
        if (isSeedEmpty(input) && !seed.isEmpty()) {
            Logger.getInstance().error("Seed file is empty or not found: " + seed);
            return;
        }

        String sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        String[] statements = sql.split(";");

        if (connection == null) {
            this.connect();
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
            for (String statement : statements){
                statement = statement.trim();
                if (!statement.isEmpty()) {
                    stmt.execute(statement);
                }
            }
            Logger.getInstance().info("All SQL statements executed successfully.");
        } catch (SQLException e) {
            Logger.getInstance().error("Error executing SQL statements: " + e.getMessage());
            e.printStackTrace();
        }

    }
    private boolean isSeedEmpty(InputStream input) throws Exception {
        return input == null || input.available() == 0;
    }


    public boolean executeQuery(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        try (Statement stmt = connection.createStatement()) {
            return stmt.execute(query);
        } catch (SQLException e) {
            Logger.getInstance().error("Error executing query: " + e.getMessage());
            return false;
        }
    }
}
