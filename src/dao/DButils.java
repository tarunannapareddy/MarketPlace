package dao;
import java.sql.*;
public class DButils {
    private final String user = "postgres";
    private final String password = "admin";

    private String url;
    public DButils(String url){
        this.url = url;
    }
    public Connection connect(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

}
