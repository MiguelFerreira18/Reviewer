package org.review.Database;

import java.sql.SQLException;

public interface DatabaseStrategy {
    void startUp() throws Exception;
    void connect() throws SQLException;
    void close() throws SQLException;
    boolean isConnected() throws SQLException;
    void seed() throws Exception;
    boolean executeQuery(String query) throws SQLException;







}
