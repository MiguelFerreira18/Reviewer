CREATE TABLE IF NOT EXISTS review
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    title      VARCHAR(64)   NOT NULL,
    content    VARCHAR(1024) NOT NULL,
    rating     INT           NOT NULL CHECK (rating > 0 AND rating <= 10),
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);