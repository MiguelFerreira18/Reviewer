package org.review.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseStrategy {
    void startUp();
    void connect();
    void close();
    boolean isConnected() ;
    ResultSet executeQuery(String query,Execution execution);
    void closeResultSet(ResultSet rs);
    Connection getConnection();







}
