package org.review.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseStrategy {
    void startUp();
    void connect();
    void close();
    boolean isConnected() ;
//    void seed();
    ResultSet executeQuery(String query,Execution execution);
    public void closeResultSet(ResultSet rs);
    public Connection getConnection();







}
