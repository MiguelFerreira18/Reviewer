package org.review.Database;

import org.review.Logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class SQLite implements DatabaseStrategy {

    private String databaseUrl;
    private String seed;
    private Connection connection;
    private Statement currentStatement;

    public SQLite(String databaseUrl, String seed) {
        this.databaseUrl = databaseUrl;
        this.seed = seed;
        connection = null;
    }

    public void startUp() {
        connect();
        if (!isConnected()) {
            Logger.getInstance().error("Failed to connect to the database at " + databaseUrl);
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
            if (currentStatement != null && !currentStatement.isClosed()) {
                currentStatement.close();
                Logger.getInstance().info("Current statement has been closed.");
            }
        } catch (SQLException e) {
            Logger.getInstance().error("Error closing connection to SQLite: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        try {
            Logger.getInstance().info("Checking connection status...");
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            Logger.getInstance().error("Error checking connection status: " + e.getMessage());
            return false;
        }
    }

    public void seed() {
        InputStream input = getClass().getClassLoader().getResourceAsStream(seed);
        if (isSeedEmpty(input) && !seed.isEmpty()) {
            Logger.getInstance().error("Seed file is empty or not found: " + seed);
            return;
        }

        String sql = "";
        try {
            sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] statements = sql.split(";");

        if (connection == null) {
            this.connect();
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
            for (String statement : statements) {
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

    private boolean isSeedEmpty(InputStream input) {
        try {
            return input == null || input.available() == 0;
        } catch (Exception e) {
            Logger.getInstance().error("Error checking seed file: " + e.getMessage());
            return true;
        }
    }


    public ResultSet executeQuery(String query) {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
                Logger.getInstance().info("Reconnected to the database.");
            }

            if (currentStatement != null && !currentStatement.isClosed()) {
                currentStatement.close();
            }
            currentStatement = connection.createStatement();
            return currentStatement.executeQuery(query);

        } catch (SQLException e) {
            Logger.getInstance().error("Error checking connection status: " + e.getMessage());
            return null;
        }
    }

    public void closeResultSet(ResultSet rs) {
        try {


            if (rs != null) {
                rs.close();
            }
            if (currentStatement != null && !currentStatement.isClosed()) {
                currentStatement.close();
            }
        } catch (SQLException e) {
            Logger.getInstance().error("Error closing ResultSet: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
