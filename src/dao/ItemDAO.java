package dao;

import dbConnector.DBConnector;

import java.sql.Connection;

public class ItemDAO {
    public Connection conn;

    public ItemDAO() {
        this.conn = DBConnector.getCustomerConnection();
    }



}
