package org.review;

import org.flywaydb.core.Flyway;
import org.review.Database.DatabaseStrategy;
import org.review.Database.Execution;
import org.review.Database.QueryBuilder;
import org.review.Database.SQLite;
import org.review.Logger.LogLevel;
import org.review.Logger.Logger;
import org.review.Mapper.TagMapper;
import org.review.Service.ReviewService;
import org.review.Service.TagService;
import org.review.View.ReviewView;
import org.review.View.TagView;

import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:Reviewer.db";
    private static final Scanner scanner = new Scanner(System.in);
    static Logger logger = new Logger(LogLevel.DEBUG);


    public static void migrateDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(DB_URL, null, null)
                .loggers()
                .load();
        flyway.migrate();
    }
    public static void main(String[] args) {
        migrateDatabase();

        DatabaseStrategy db = new SQLite(DB_URL);
        try {
            db.startUp();
        } catch (Exception e) {
            logger.error("Error starting up the database: " + e.getMessage());
        }
        scanner.nextInt();

        TagService tagService = new TagService(db);
        TagView tagView = new TagView(tagService, scanner);

        ReviewService reviewService = new ReviewService(db);
        ReviewView reviewView = new ReviewView(reviewService, tagService,scanner);



        UiManager uiManager = new UiManager(logger,reviewView,tagView, scanner);
        uiManager.start();
    }

}