package org.review;

import org.review.Database.DatabaseStrategy;
import org.review.Database.QueryBuilder;
import org.review.Database.SQLite;
import org.review.Logger.LogLevel;
import org.review.Logger.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:Reviewer.db";
    private static final String SEED_FILE = "db/seed.sql";
    private static final Scanner scanner = new Scanner(System.in);
    static Logger logger = new Logger(LogLevel.DEBUG);

    public static void main(String[] args) {

        DatabaseStrategy db = new SQLite(DB_URL, SEED_FILE);
        try {
            db.startUp();
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println("Error starting up the database: " + e.getMessage());
        }
        QueryBuilder query = new QueryBuilder().select().from("tag");

        try {
            logger.info(db.isConnected() ? "Database is connected." : "Database is not connected.");
            ResultSet rs = db.executeQuery(query.build());
            logger.info("Query executed: " + query.build());
            while (rs.next()) {
                System.out.println("Tag Name: " + rs.getString("tag_name"));
            }
            logger.info("Query executed successfully.");

        } catch (SQLException e) {
            logger.error("Error creating statement: " + e.getMessage());
        }
        scanner.nextInt();
    }
}