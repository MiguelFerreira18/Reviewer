package org.review.Mapper;

import org.review.Database.DatabaseStrategy;
import org.review.Database.QueryBuilder;
import org.review.Logger.Logger;
import org.review.Model.Review;
import org.review.Model.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {
    DatabaseStrategy db;

    public ReviewMapper(DatabaseStrategy db) {
        this.db = db;
    }

    public Review mapToReview(ResultSet rs) {
        try {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            int rating = rs.getInt("rating");
            Timestamp createdAt = rs.getTimestamp("created_at");
            Timestamp updatedAt = rs.getTimestamp("updated_at");
            int tagId = rs.getInt("tag_id");

            Tag tag = returnTag(tagId);

            return new Review(id, title, content, rating, createdAt.toLocalDateTime(), updatedAt.toLocalDateTime(), tag);

        } catch (Exception e) {
            Logger.getInstance().error("Error mapping ResultSet to Review: " + e.getMessage());
            return null;
        }
    }

    private Tag returnTag(int tagId) {
        QueryBuilder query = new QueryBuilder()
                .select()
                .from("tag")
                .where("id" + " = " + tagId);
        ResultSet rs = db.executeQuery(query.build());

        return new TagMapper().mapToTag(rs);
    }

    public List<Review> mapToReviews(ResultSet rs) {
        List<Review> reviews = new ArrayList<>();

        try {
            while (rs.next()) {
                Review review = mapToReview(rs);
                if (review != null) {
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reviews;
    }
}
