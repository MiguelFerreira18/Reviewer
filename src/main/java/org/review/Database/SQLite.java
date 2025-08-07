package org.review.Database;

import org.review.Logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class SQLite implements DatabaseStrategy {

    private String databaseUrl;
    private Connection connection;
    private Statement currentStatement;

    public SQLite(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        connection = null;
    }

    public void startUp() {
        connect();
        if (!isConnected()) {
            Logger.getInstance().error("Failed to connect to the database at " + databaseUrl);
        }
//        seed();
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

    private boolean isSeedEmpty(InputStream input) {
        try {
            return input == null || input.available() == 0;
        } catch (Exception e) {
            Logger.getInstance().error("Error checking seed file: " + e.getMessage());
            return true;
        }
    }


    public ResultSet executeQuery(String query, Execution execution) {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
                Logger.getInstance().info("Reconnected to the database.");
            }

            if (currentStatement != null && !currentStatement.isClosed()) {
                currentStatement.close();
            }
            currentStatement = connection.createStatement();
            if (execution == Execution.UPDATE) {
                currentStatement.executeUpdate(query);
                Logger.getInstance().info("Executed update query: " + query);
                return null;
            }
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
