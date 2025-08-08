package org.review.View;

import org.review.Dto.DisplayTagDTO;
import org.review.Model.Review;
import org.review.Model.Tag;
import org.review.Service.ReviewService;
import org.review.Service.TagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReviewView {
    private ReviewService reviewService;
    private Scanner scanner;
    private TagService tagService;

    public ReviewView(ReviewService reviewService, TagService tagService, Scanner scanner) {
        this.reviewService = reviewService;
        this.scanner = scanner;
        this.tagService = tagService;
    }

    public void addReview() {
        System.out.println("Enter review details:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Content: ");
        String content = scanner.nextLine();
        System.out.print("Rating (1-5): ");
        int rating = scanner.nextInt();

        scanner.nextLine(); // Consume the newline character left by nextInt()

        List<Tag> tags = addTagsToReview();

        Review review = new Review(title, content, rating, tags);

        reviewService.addReview(review);
    }

    private void printTagsWithId() {
        List<DisplayTagDTO> tags = tagService.getAllDisplayTags();
        System.out.println("Available Tags:");
        tags.forEach(tag -> System.out.println("Option: " + tag.id() + ", Name: " + tag.name()));
    }

    private List<Tag> addTagsToReview() {
        List<Tag> tags = new ArrayList<>();
        System.out.println("Select tags for the review (write 0 to leave)");
        do {
            printTagsWithId();
            System.out.print("Enter tag ID (or 0 to finish): ");
            int tagId = scanner.nextInt();
            if (tagId == 0) {
                break;
            }
            Tag tag = tagService.getTagById(tagId);
            if (tag != null) {
                tags.add(tag);
                System.out.println("Tag added: " + tag.getName());
            } else {
                System.out.println("Invalid tag ID. Please try again.");
            }
        } while (true);
        return tags;
    }


    public void updateReview() {

        System.out.println("Select the review you want to update:");
        displayReviews();
        System.out.print("Enter the review ID to update: ");
        int reviewId = scanner.nextInt();
        scanner.nextLine();
        Review review = reviewService.getReviewById(reviewId);

        System.out.println("Enter new details for the review (leave blank to keep current value):");
        System.out.print("Title (" + review.getTitle() + "): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) {
            review.setTitle(title);
        }
        System.out.print("Content (" + review.getContent() + "): ");
        String content = scanner.nextLine();
        if (!content.isEmpty()) {
            review.setContent(content);
        }
        System.out.print("Rating (" + review.getRating() + "): ");
        int rating = scanner.nextInt();
        if (rating > 0 && rating <= 5) {
            review.setRating(rating);
        }

        scanner.nextLine();

        System.out.println("These are the current tags for this review:");
        getTagsFromReview(review);

        System.out.println("Select new tags for the review (write 0 to leave current tags)");
        List<Tag> newTags = addTagsToReview();
        if (!newTags.isEmpty()) {
            review.setTag(newTags);
        }


        reviewService.updateReview(reviewId, review);


    }

    private void displayReviews() {
        List<Review> reviews = reviewService.getReviews();
        if (reviews.isEmpty()) {
            System.out.println("No reviews found.");
        } else {
            System.out.println("Reviews:");
            for (Review review : reviews) {
                System.out.println("Option: " + review.getId() + ", Title: " + review.getTitle() + ", Rating: " + review.getRating());
            }
        }
    }

    private void getTagsFromReview(Review review) {
        List<Tag> tags = new ArrayList<>();
        if (review.getTag() != null && !review.getTag().isEmpty()) {
            review.getTag().forEach(tag -> {
                System.out.println("Tag: " + tag.getName());
            });
        }
    }


    public void deleteReview() {
        System.out.println("Select the review you want to delete:");
        displayReviews();
        System.out.print("Enter the review ID to delete: ");
        int reviewId = scanner.nextInt();

        reviewService.removeReviewById(reviewId);
    }

    public Review getReviewById() {
        displayReviews();
        System.out.print("Enter the review ID to view: ");
        int reviewId = scanner.nextInt();
        scanner.nextLine();
        do {
            if (reviewId <= 0) {
                System.out.println("Invalid ID. Please enter a valid review ID.");
                System.out.print("Enter the review ID to view: ");
                reviewId = scanner.nextInt();
            }
        } while (reviewId <= 0);

        return reviewService.getReviewById(reviewId);
    }

    public List<Review> getReviewsByTag() {
        System.out.println("Enter the tag to filter reviews by:");
        String tag = scanner.nextLine();
        if (tag.isEmpty()) {
            System.out.println("Tag cannot be empty. Please try again.");
            return new ArrayList<>();
        }

        return reviewService.getFilteredReviewsByTag(tag);
    }

    public List<Review> getAllReviews() {
        return reviewService.getReviews();
    }

    public List<Review> getReviewsByRating() {
        System.out.println("Enter the rating to filter reviews by (1-10):");
        int rating = scanner.nextInt();
        do {
            if (rating < 1 || rating > 10) {
                System.out.println("Invalid rating. Please enter a rating between 1 and 10.");
                System.out.print("Enter the rating to filter reviews by (1-10): ");
                rating = scanner.nextInt();
            }
        } while (rating < 1 || rating > 10);
        return reviewService.getFilteredReviewsByRating(rating);
    }

    @SuppressWarnings("t")
    public List<Review> getReviewsByDate() {
        String day, month, year;
        System.out.println("Enter a day:");
        day = scanner.nextLine();
        do {
            if (day.isEmpty() || !day.matches("\\d{1,2}")) {
                System.out.println("Invalid day. Please enter a valid day (1-31):");
                day = scanner.nextLine();
            }
        } while (day.isEmpty() || !day.matches("\\d{1,2}"));


        System.out.println("Enter a month:");
        month = scanner.nextLine();
        do {
            if (month.isEmpty() || !month.matches("\\d{1,2}")) {
                System.out.println("Invalid month. Please enter a valid month (1-12):");
                month = scanner.nextLine();
            }
        } while (month.isEmpty() || !month.matches("\\d{1,2}"));

        System.out.println("Enter a year:");
        year = scanner.nextLine();
        do {
            if (year.isEmpty() || !year.matches("\\d{4}")) {
                System.out.println("Invalid year. Please enter a valid year (YYYY):");
                year = scanner.nextLine();
            }
        } while (year.isEmpty() || !year.matches("\\d{4}"));

        String date = day + "-" + month + "-" + year;

        return reviewService.getFilteredReviewsByDate(date);
    }

}
