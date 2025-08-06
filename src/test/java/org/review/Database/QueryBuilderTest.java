package org.review.Database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class QueryBuilderTest {
    @Test
    @DisplayName("Test Query Builder with only select")
    void testQueryBuilderSelectOnly() {
        String expectedQuery = "SELECT * FROM users";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.select().from("users").build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query Build with select and columns")
    void testQueryBuilderSelectAndColumns() {
        String expectedQuery = "SELECT id, name FROM users";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.select("id", "name").from("users").build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query Builder with where clause")
    void testQueryBuilderWithWhere() {
        String expectedQuery = "SELECT id, name FROM users WHERE age > 18";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.select("id", "name").from("users").where("age > 18").build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("TEst Query Builder with order by")
    void testQueryBuilderWithOrderBy() {
        String expectedQuery = "SELECT id, name FROM users ORDER BY age ASC";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.select("id", "name").from("users").orderBy("age", true).build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query Builder with left join")
    void testQueryBuilderWithJoin() {
        String expectedQuery = "SELECT id, name FROM users LEFT JOIN reviews ON users.id = reviews.user_id";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.select("id", "name").from("users").join("reviews", "users.id = reviews.user_id", JoinType.LEFT).build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query Builder with full join")
    void testQueryBuilderWithFullJoin() {
        String expectedQuery = "SELECT id, name FROM users FULL OUTER JOIN reviews ON users.id = reviews.user_id";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.select("id", "name").from("users").join("reviews", "users.id = reviews.user_id", JoinType.FULL).build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query Builder with multiple joins")
    void testQueryBuilderWithMultipleJoins() {
        String expectedQuery = "SELECT id, name FROM users LEFT JOIN reviews ON users.id = reviews.user_id LEFT JOIN comments ON reviews.id = comments.review_id";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.select("id", "name").from("users")
                .join("reviews", "users.id = reviews.user_id", JoinType.LEFT)
                .join("comments", "reviews.id = comments.review_id", JoinType.LEFT)
                .build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query for random order of query functions")
    void testQueryBuilderRandomOrder() {
        String expectedQuery = "SELECT id, name FROM users WHERE age > 18 ORDER BY name DESC";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.from("users")
                .where("age > 18")
                .orderBy("name", false)
                .select("id", "name")
                .build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query Builder for subquery")
    void testQueryBuilderWithSubquery() {
        String expectedQuery = "SELECT id, name FROM users WHERE id IN (SELECT id FROM users WHERE age > 18)";
        QueryBuilder queryBuilder = new QueryBuilder();
        QueryBuilder subQueryBuilder = new QueryBuilder();

        String subquery = subQueryBuilder.select("id").from("users").where("age > 18").build();
        String query = queryBuilder.select("id", "name")
                .from("users")
                .where("id IN (" + subquery + ")")
                .build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query Builder for and where method")
    void testQUeryBuilderWithAndWhere() {
        String expectedQuery = "SELECT * FROM users WHERE age > 18 AND name = 'Jhon'";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder
                .select()
                .where("age > 18")
                .andWhere("name = 'Jhon'")
                .from("users")
                .build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query Builder for and where method")
    void testQUeryBuilderWithOrWhere() {
        String expectedQuery = "SELECT * FROM users WHERE age > 18 OR name = 'Jhon'";
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder
                .select()
                .where("age > 18")
                .orWhere("name = 'Jhon'")
                .from("users")
                .build();
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test Query without from clauses")
    void testQueryBuilderWithoutFrom() {
        String expectedMessage = "FROM clause is required";

        QueryBuilder queryBuilder = new QueryBuilder();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            queryBuilder.select("id", "name").build();
        });

        assertTrue(exception.getMessage().contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }

}