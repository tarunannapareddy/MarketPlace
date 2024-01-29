package dao;

import dbConnector.DBConnector;

import java.sql.Connection;

public class OrderDAO {
    public Connection conn;

    public OrderDAO() {
        this.conn = DBConnector.getProductConnection();
    }


}
