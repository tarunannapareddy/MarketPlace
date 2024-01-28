package dao;

import dbConnector.DBConnector;

import java.sql.Connection;

public class FeedBackDAO {
    private Connection conn;

    public FeedBackDAO() {
        this.conn = DBConnector.getCustomerConnection();
    }

}
