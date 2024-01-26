package dao;

import pojos.Account;
import pojos.Buyer;

import java.sql.*;

public class UsersDAO {
    private String url = "jdbc:postgresql://localhost/users";
    DButils dbdetails= new DButils(url);
    private Connection conn = null;

    public Integer createUsers(String password){
        if (conn==null){
                conn = dbdetails.connect();
        }
        String  query= "insert into users (password) values (?) returning user_id";
        int user_id=0;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, password);
            try {
                ResultSet rs =preparedStatement.executeQuery();
                while (rs.next()) {
                    user_id = rs.getInt("user_id");
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return user_id;
    }

    public Account getUserDetails(Integer userid){
        if (conn==null) {
            conn = dbdetails.connect();
        }
        Account accountInfo = null;
        String query = "SELECT * from users where user_id = "+userid;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                accountInfo = new Account();
                accountInfo.setId(rs.getInt(1));
                accountInfo.setPassword(rs.getString(2));
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accountInfo;

    }
}
