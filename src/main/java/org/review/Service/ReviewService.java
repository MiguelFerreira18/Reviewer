package org.review.Service;

import org.review.Database.*;
import org.review.Mapper.ReviewMapper;
import org.review.Model.Review;
import org.review.Model.Tag;

import java.sql.ResultSet;
import java.util.List;

public class ReviewService {
    private DatabaseStrategy database;

    public ReviewService(DatabaseStrategy database) {
        this.database = database;
    }

    public void addReview(Review review) {
        CRUDBuilder crudBuilder = new CRUDBuilder();
        crudBuilder.operation(Operation.INSERT).table("review").values(
                review.getTitle(),
                review.getContent(),
                String.valueOf(review.getRating())
        );
        String query = crudBuilder.build();
        try {
            database.executeQuery(query, Execution.UPDATE);
            System.out.println("Review added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding review: " + e.getMessage());
        }

        int reviewId = getReviewId(review);
        if (reviewId != -1) {
            for (Tag tag : review.getTag()) {
                crudBuilder = new CRUDBuilder();
                crudBuilder.operation(Operation.INSERT)
                        .table("review_tag")
                        .values(
                                String.valueOf(reviewId),
                                String.valueOf(tag.getId())
                        );
                String tagQuery = crudBuilder.build();
                try {
                    database.executeQuery(tagQuery, Execution.UPDATE);
                } catch (Exception e) {
                    System.err.println("Error adding tag to review: " + e.getMessage());
                }
            }
        } else {
            System.err.println("Review ID not found after insertion.");
        }

    }

    private int getReviewId(Review review) {
        QueryBuilder queryBuilder = new QueryBuilder()
                .select("id")
                .from("review")
                .where("title = '" + review.getTitle() + "' AND content = '" + review.getContent() + "'"
                        + " AND rating = " + review.getRating() + " AND created_at = '" + review.getCreatedAt() + "'");
        ResultSet rs = database.executeQuery(queryBuilder.build(), Execution.READ);
        try {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.err.println("Error retrieving review ID: " + e.getMessage());
        }
        return -1;
    }

    public void removeReviewById(int id) {
        CRUDBuilder crudBuilder = new CRUDBuilder();
        crudBuilder.operation(Operation.DELETE)
                .table("review")
                .where("id = " + id);
        String query = crudBuilder.build();
        try {
            database.executeQuery(query, Execution.UPDATE);
            System.out.println("Review removed successfully.");
        } catch (Exception e) {
            System.err.println("Error removing review: " + e.getMessage());
        }
    }

    public void updateReview(int reviewId, Review review) {
        CRUDBuilder crudBuilder = new CRUDBuilder();
        crudBuilder.operation(Operation.UPDATE)
                .table("review")
                .set("title",review.getTitle())
                .set("content", review.getContent())
                .set("rating", String.valueOf(review.getRating()))
                .set("updated_at", review.updateDateTime().toString())
                .where("id = " + reviewId);
        String query = crudBuilder.build();
        try {
            database.executeQuery(query, Execution.UPDATE);
            System.out.println("Review updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating review: " + e.getMessage());
        }
    }

    private void updateTags(int reviewId, List<Tag> tags) {
        CRUDBuilder crudBuilder = new CRUDBuilder();
        crudBuilder.operation(Operation.DELETE)
                .table("review_tag")
                .where("review_id = " + reviewId);
        String deleteQuery = crudBuilder.build();
        database.executeQuery(deleteQuery, Execution.UPDATE);

        for (Tag tag : tags) {
            crudBuilder = new CRUDBuilder();
            crudBuilder.operation(Operation.INSERT)
                    .table("review_tag")
                    .values(String.valueOf(reviewId), String.valueOf(tag.getId()));
            String insertQuery = crudBuilder.build();
            database.executeQuery(insertQuery, Execution.UPDATE);
        }
    }

    public Review getReviewById(int id) {
        QueryBuilder queryBuilder = new QueryBuilder()
                .select()
                .from("review")
                .where("id = " + id);
        ResultSet rs = database.executeQuery(queryBuilder.build(), Execution.READ);
        ReviewMapper reviewMapper = new ReviewMapper(database);
        return reviewMapper.mapToReview(rs);
    }

    public List<Review> getReviews() {
        QueryBuilder queryBuilder = new QueryBuilder()
                .select()
                .from("review");
        ResultSet rs = database.executeQuery(queryBuilder.build(), Execution.READ);
        ReviewMapper reviewMapper = new ReviewMapper(database);
        return reviewMapper.mapToReviews(rs);
    }

    public List<Review> getFilteredReviewsByTag(String tag) {
        QueryBuilder queryBuilder = new QueryBuilder()
                .select("review.*")
                .from("review")
                .join("review_tag", "review.id = review_tag.review_id", JoinType.INNER)
                .join("tag", "review_tag.tag_id = tag.id", JoinType.INNER)
                .where("tag.name = '" + tag + "'");
        ResultSet rs = database.executeQuery(queryBuilder.build(), Execution.READ);
        ReviewMapper reviewMapper = new ReviewMapper(database);

        return reviewMapper.mapToReviews(rs);
    }

    public List<Review> getFilteredReviewsByDate(String date) {
        QueryBuilder queryBuilder = new QueryBuilder()
                .select()
                .from("review")
                .where("DATE(created_at) = '" + date + "'");
        ResultSet rs = database.executeQuery(queryBuilder.build(), Execution.READ);
        ReviewMapper reviewMapper = new ReviewMapper(database);

        return reviewMapper.mapToReviews(rs);
    }

    public List<Review> getFilteredReviewsByRating(int rating) {
        QueryBuilder queryBuilder = new QueryBuilder()
                .select()
                .from("review")
                .where("rating = " + rating);
        ResultSet rs = database.executeQuery(queryBuilder.build(), Execution.READ);
        ReviewMapper reviewMapper = new ReviewMapper(database);

        return reviewMapper.mapToReviews(rs);
    }
}
