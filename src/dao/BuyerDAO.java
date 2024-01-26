package dao;

import pojos.Account;
import pojos.Buyer;
import pojos.Request.BuyerRequest;


import java.sql.*;


public class BuyerDAO {
    private String url = "jdbc:postgresql://localhost/users";
    DButils dbdetails= new DButils(url);
    private Connection conn = null;
    public Buyer getBuyerInfo(int buyerId){
        if (conn==null){
            try {
                conn = dbdetails.connect();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        Buyer buyerInfo = null;
        String  query= "SELECT buyer_id,buyer_name,num_items_purch FROM buyers where buyer_id="+buyerId;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            buyerInfo = new Buyer();
            while (rs.next()) {
                buyerInfo.setBuyerId(rs.getInt(1));
                buyerInfo.setName(rs.getString(2));
                buyerInfo.setItemsPurchased(rs.getInt(3));
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return buyerInfo;
    }
}
