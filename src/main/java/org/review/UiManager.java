package org.review;

import org.review.Dto.DisplayTagDTO;
import org.review.Logger.Logger;
import org.review.Model.Review;
import org.review.Model.Tag;
import org.review.View.ReviewView;
import org.review.View.TagView;

import java.util.List;
import java.util.Scanner;


public class UiManager {
    private Logger logger;
    private ReviewView reviewView;
    private TagView tagView;
    private Scanner sca;

    public UiManager(Logger logger, ReviewView reviewView, TagView tagView, Scanner scanner) {
        this.logger = logger;
        this.reviewView = reviewView;
        this.tagView = tagView;
        this.sca = scanner;
    }

    public void start() {
        displayBanner();
        boolean isRunning = true;
        do {
            displayMenu();
            int choice = sca.nextInt();
            isRunning = manageMainMenuOptions(choice);
        } while (isRunning);

    }

    private void displayBanner() {
        System.out.println("\n" +
                "██████╗ ███████╗██╗    ██╗██╗███████╗██╗    ██╗███████╗██████╗ \n" +
                "██╔══██╗██╔════╝██║    ██║██║██╔════╝██║    ██║██╔════╝██╔══██╗\n" +
                "██████╔╝█████╗  ██║ █╗ ██║██║█████╗  ██║ █╗ ██║█████╗  ██████╔╝\n" +
                "██╔══██╗██╔══╝  ██║███╗██║██║██╔══╝  ██║███╗██║██╔══╝  ██╔══██╗\n" +
                "██║  ██║███████╗╚███╔███╔╝██║███████╗╚███╔███╔╝███████╗██║  ██║\n" +
                "╚═╝  ╚═╝╚══════╝ ╚══╝╚══╝ ╚═╝╚══════╝ ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝\n");

    }

    private void displayMenu() {
        System.out.println("\n");
        System.out.println("1. Review Management");
        System.out.println("2. Tag Management");
        System.out.println("3. Exit");
    }

    private boolean manageMainMenuOptions(int choice) {
        switch (choice) {
            case 1:
                manageReviews();
                break;
            case 2:
                manageTags();
                break;
            case 3:
                System.out.println("Exiting the application...");
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    private void manageReviews() {
        managingReviewsBanner();
        boolean isRunning = true;
        do {
            managingReviewsMenu();
            int choice = sca.nextInt();
            isRunning = manageReviewsMenuOptions(choice);
        } while (isRunning);
    }

    private void managingReviewsMenu() {
        System.out.println("1. Add Review");
        System.out.println("2. Edit Review");
        System.out.println("3. Delete Review");
        System.out.println("4. View Reviews");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private boolean manageReviewsMenuOptions(int choice) {
        switch (choice) {
            case 1:
                reviewView.addReview();
                break;
            case 2:
                reviewView.updateReview();
                break;
            case 3:
                reviewView.deleteReview();
                break;
            case 4:
                manageReadReviews();
                break;
            case 5:
                System.out.println("Returning to Main Menu...");
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    private void managingReviewsBanner() {
        System.out.println("\n" +
                "╔═══════════════════════════════╗\n" +
                "║       Review Management       ║\n" +
                "╚═══════════════════════════════╝\n");
    }

    private void manageReadReviewMenu() {
        System.out.println("1. See a review");
        System.out.println("2. get all reviews");
        System.out.println("3. Filter reviews by tag");
        System.out.println("4. Filter reviews by rating");
        System.out.println("5. Filter reviews by date");
        System.out.println("6. Filter reviews by custom filter (not implemented)");
        System.out.println("7. Back to Main Menu");
        System.out.print("Enter your choice: ");

    }

    private void manageReadReviews() {
        sca.nextLine();

        boolean isRunning = true;
        do {
            manageReadReviewMenu();
            int readChoice = sca.nextInt();
            isRunning = manageReadReviewMenuOptions(readChoice);
        } while (isRunning);
    }

    private boolean manageReadReviewMenuOptions(int choice) {
        List<Review> reviews = null;
        switch (choice) {
            case 1:
                Review review = reviewView.getReviewById();
                System.out.println("Review Details: \n" + review);
                break;
            case 2:
                reviews = reviewView.getAllReviews();
                if (reviews.isEmpty()) {
                    System.out.println("No reviews found.");
                } else {
                    System.out.println("All Reviews:");
                    reviews.forEach(System.out::println);
                }
                break;
            case 3:
                reviews = reviewView.getReviewsByTag();
                if (reviews.isEmpty()) {
                    System.out.println("No reviews found with the specified tag.");
                } else {
                    System.out.println("Reviews with the specified tag:");
                    reviews.forEach(System.out::println);
                }
                break;
            case 4:
                reviews = reviewView.getReviewsByRating();
                if (reviews.isEmpty()) {
                    System.out.println("No reviews found with the specified rating.");
                } else {
                    System.out.println("Reviews with the specified rating:");
                    reviews.forEach(System.out::println);
                }
                break;
            case 5:
                reviews = reviewView.getReviewsByDate();
                if (reviews.isEmpty()) {
                    System.out.println("No reviews found for the specified date.");
                } else {
                    System.out.println("Reviews for the specified date:");
                    reviews.forEach(System.out::println);
                }
                break;
            case 6:
                // TODO: Implement custom filter logic
                break;
            case 7:
                System.out.println("Returning to Main Menu...");
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    private void manageTags() {
        managingTagsBanner();
        boolean isRunning = true;
        do {
            managingTagsMenu();
            int choice = sca.nextInt();
            isRunning = manageTagsMenuOptions(choice);
        } while (isRunning);
    }

    private void managingTagsMenu() {
        System.out.println("1. Add Tag");
        System.out.println("2. Edit Tag");
        System.out.println("3. Delete Tag");
        System.out.println("4. View Tags");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }


    private void managingTagsBanner() {
        System.out.println("\n" +
                "╔════════════════════════════════╗\n" +
                "║         Tag Management         ║\n" +
                "╚════════════════════════════════╝\n");
    }

    private boolean manageTagsMenuOptions(int choice) {
        switch (choice) {
            case 1:
                tagView.addTag();
                break;
            case 2:
                tagView.updateTag();
                break;
            case 3:
                tagView.deleteTag();
                break;
            case 4:
                manageReadTags();
                break;
            case 5:
                System.out.println("Returning to Main Menu...");
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    private void manageReadTagsMenu() {
        System.out.println("1. get all tags");
        System.out.println("2. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private void manageReadTags() {
        boolean isRunning = true;
        do {
            manageReadTagsMenu();
            int choice = sca.nextInt();
            isRunning = manageReadTagsMenuOptions(choice);
        } while (isRunning);
    }

    private boolean manageReadTagsMenuOptions(int choice) {
        switch (choice) {
            case 1:
                List<DisplayTagDTO> tags = tagView.getAllTags();
                if (tags.isEmpty()) {
                    System.out.println("No tags found.");
                } else {
                    System.out.println("All Tags:");
                    tags.forEach(System.out::println);
                }
                break;
            case 2:
                System.out.println("Returning to Main Menu...");
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }
}
