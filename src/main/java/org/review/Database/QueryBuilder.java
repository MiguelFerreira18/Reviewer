package org.review.Database;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private List<String> selectColumns = new ArrayList<>();
    private String fromTable;
    private List<JoinClause> joins = new ArrayList<>();
    private List<String> whereConditions = new ArrayList<>();
    private List<OrderByClause> orderByClauses = new ArrayList<>();
    private QueryBuilder subQuery;



    public QueryBuilder select(String... columns) {
        if (columns.length == 0) {
            selectColumns.add("*");
        } else {
            for (String column : columns) {
                selectColumns.add(column);
            }
        }
        return this;
    }

    public QueryBuilder from(String table) {
        this.fromTable = table;
        return this;
    }

    /**
     * Adds a WHERE condition to the query.
     * If more than one where method clause is called instead of the orWhere or andWhere
     * methods, it defaults to using "AND" as the conjunction.
     * @param condition the condition to add, e.g., "column = value" or "column > value".
     * @return this QueryBuilder instance for method chaining.
     */
    public QueryBuilder where(String condition) {
        whereConditions.add(condition);
        return this;
    }

    public QueryBuilder andWhere(String condition) {
        whereConditions.add(" AND " + condition);
        return this;
    }

    public QueryBuilder orWhere(String condition) {
        whereConditions.add(" OR " + condition);
        return this;
    }

    public QueryBuilder orderBy(String column, boolean ascending) {
        orderByClauses.add(new OrderByClause(column, ascending));
        return this;
    }

    public QueryBuilder join(String table, String condition, JoinType type) {
        joins.add(new JoinClause(table, condition, type));
        return this;
    }

    public String build() {
        if (fromTable == null) {
            throw new IllegalStateException("FROM clause is required");
        }

        StringBuilder query = new StringBuilder("SELECT ");

        if (selectColumns.isEmpty()) {
            query.append("*");
        } else {
            query.append(String.join(", ", selectColumns));
        }

        query.append(" FROM ").append(fromTable);

        joinClause(query);

        if (!whereConditions.isEmpty()) {
            whereClause(query);
        }

        if (!orderByClauses.isEmpty()) {
            orderByClause(query);
        }

        return query.toString();
    }

    private void orderByClause(StringBuilder query) {
        query.append(" ORDER BY ");
        List<String> orderBys = new ArrayList<>();
        for (OrderByClause clause : orderByClauses) {
            orderBys.add(clause.column + (clause.ascending ? " ASC" : " DESC"));
        }
        query.append(String.join(", ", orderBys));
    }

    private void joinClause(StringBuilder query) {
        for (JoinClause join : joins) {
            query.append(" ").append(join.type)
                    .append(" JOIN ").append(join.table)
                    .append(" ON ").append(join.condition);
        }
    }

    private void whereClause(StringBuilder query) {
        query.append(" WHERE ");
        boolean first = true;
        for (String condition : whereConditions) {
            if (!first && !condition.startsWith(" AND ") && !condition.startsWith(" OR ")) {
                query.append(" AND ");
            }
            query.append(condition);
            first = false;
        }
    }


    private static class JoinClause {
        String table;
        String condition;
        JoinType type;

        JoinClause(String table, String condition, JoinType type) {
            this.table = table;
            this.condition = condition;
            this.type = type;
        }
    }

    private static class OrderByClause {
        String column;
        boolean ascending;

        OrderByClause(String column, boolean ascending) {
            this.column = column;
            this.ascending = ascending;
        }
    }
}
