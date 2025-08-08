package org.review.Database;

import java.util.ArrayList;
import java.util.List;

public class CRUDBuilder {
    private String tableName;
    private Operation operation; // e.g., "INSERT", "UPDATE", "DELETE"
    private List<String> setClause = new ArrayList<>();
    private List<String> whereClause = new ArrayList<>();
    private StringBuilder valuesClause = new StringBuilder();

    public CRUDBuilder table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public CRUDBuilder operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public CRUDBuilder set(String column) {
        setClause.add(column);
        return this;
    }
    public CRUDBuilder set(String column, String value) {
        setClause.add(column + " = '" + value + "'");
        return this;
    }

    public CRUDBuilder from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public CRUDBuilder where(String condition) {
        whereClause.add(condition);
        return this;
    }

    public CRUDBuilder andWhere(String condition) {
        whereClause.add(" AND " + condition);
        return this;
    }

    public CRUDBuilder orWhere(String condition) {
        whereClause.add(" OR " + condition);
        return this;
    }

    public CRUDBuilder values(String... values) {
        if (values.length > 0) {
            valuesClause.append(" VALUES (");
            for (int i = 0; i < values.length; i++) {
                valuesClause.append("'").append(values[i]).append("'");
                if (i < values.length - 1) {
                    valuesClause.append(", ");
                }
            }
            valuesClause.append(")");
        }
        return this;
    }

    public String build() {
        StringBuilder query = new StringBuilder();

        switch (operation) {
            case INSERT:
                query.append("INSERT INTO ").append(tableName).append(" (");
                query.append(String.join(", ", setClause));
                query.append(")").append(valuesClause);
                break;
            case UPDATE:
                query.append("UPDATE ").append(tableName).append(" SET ");
                query.append(String.join(", ", setClause));
                if (!whereClause.isEmpty()) {
                    whereClause(query);
                }
                break;
            case DELETE:
                query.append("DELETE FROM ").append(tableName);
                if (!whereClause.isEmpty()) {
                    whereClause(query);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported operation: " + operation);
        }

        return query.toString();
    }

    private void whereClause(StringBuilder query) {
        query.append(" WHERE ");
        boolean first = true;
        for (String condition : whereClause) {
            if (!first && !condition.startsWith(" AND ") && !condition.startsWith(" OR ")) {
                query.append(" AND ");
            }
            query.append(condition);
            first = false;
        }
    }


}
