package org.review.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Review {
    private int id;
    private String title;
    private String content;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Tag> tag;

    public Review(int id, String title, String content, int rating, LocalDateTime createdAt, LocalDateTime updatedAt, List<Tag> tag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tag = tag;
    }

    public Review(int id, String title, String content, int rating, List<Tag> tag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.tag = tag;
    }

    public Review(String title, String content, int rating,List<Tag> tag) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.tag = tag;
    }

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime updateDateTime() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime();
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public void addTag(Tag tag) {
        if (this.tag == null) {
            this.tag = new java.util.ArrayList<>();
        }
        this.tag.add(tag);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Review{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", rating=").append(rating);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
