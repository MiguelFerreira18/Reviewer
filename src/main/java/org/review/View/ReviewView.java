package org.review.View;

import org.review.Model.Review;
import org.review.Service.ReviewService;

import java.util.List;

public class ReviewView {
    ReviewService reviewService;

    public ReviewView(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void addReview(Review review) {
        reviewService.addReview(review);
    }

    public void updateReview(int reviewId, Review review) {
        reviewService.updateReview(reviewId, review);
    }

    public void deleteReview(int reviewId) {
        reviewService.removeReviewById(reviewId);
    }

    public Review getReviewById(int reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    public List<Review> getFilteredReviewsByTag(String tag) {
        return reviewService.getFilteredReviewsByTag(tag);
    }

    public List<Review> getAllReviews() {
        return reviewService.getReviews();
    }

    public List<Review> getReviewsByRating(int rating) {
        return reviewService.getFilteredReviewsByRating(rating);
    }

    public List<Review> getReviewsByDate(String date) {
        return reviewService.getFilteredReviewsByDate(date);
    }


}
