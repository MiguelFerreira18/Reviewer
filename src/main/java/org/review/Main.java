package org.review;

import org.review.Database.DatabaseStrategy;
import org.review.Database.SQLite;
import org.review.Logger.LogLevel;
import org.review.Logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:Reviewer.db";
    private static final String  SEED_FILE = "db/seed.sql";
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

        int choice = scanner.nextInt();

    }
}