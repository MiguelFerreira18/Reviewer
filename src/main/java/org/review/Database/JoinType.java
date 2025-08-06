package org.review.Database;

public enum JoinType {
    INNER("INNER"),
    LEFT("LEFT"),
    RIGHT("RIGHT"),
    FULL("FULL OUTER");

    private final String type;

    JoinType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
