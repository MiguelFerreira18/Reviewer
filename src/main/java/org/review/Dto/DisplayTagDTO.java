package org.review.Dto;

public record DisplayTagDTO(int id, String name) {

    public DisplayTagDTO(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.id = id;
        this.name = name;
    }



}
