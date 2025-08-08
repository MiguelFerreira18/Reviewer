package org.review.Mapper;

import org.review.Database.DatabaseStrategy;
import org.review.Database.Execution;
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

            List<Tag> tags = returnTags(id);

            return new Review(id, title, content, rating, createdAt.toLocalDateTime(), updatedAt.toLocalDateTime(), tags);

        } catch (Exception e) {
            Logger.getInstance().error("Error mapping ResultSet to Review: " + e.getMessage());
            return null;
        }
    }

    private List<Tag> returnTags(int reviewId) {
        QueryBuilder query = new QueryBuilder()
                .select()
                .from("review_tag")
                .where("review_id = " + reviewId);
        ResultSet rs = db.executeQuery(query.build(), Execution.READ);
        List<Tag> tagList = new ArrayList<>();

        try {
            while (rs.next()) {
                int tagId = rs.getInt("tag_id");
                QueryBuilder tagQuery = new QueryBuilder()
                        .select()
                        .from("tag")
                        .where("id = " + tagId);
                ResultSet tagRs = db.executeQuery(tagQuery.build(), Execution.READ);
                if (tagRs.next()) {
                    TagMapper tagMapper = new TagMapper();
                    Tag tag = tagMapper.mapToTag(tagRs);
                    if (tag != null) {
                        tagList.add(tag);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getInstance().error("Error retrieving tag IDs for review: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return tagList;
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
            Logger.getInstance().error("Error mapping ResultSet to List<Review>: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return reviews;
    }
}
