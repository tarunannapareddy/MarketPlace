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

    public void updateCartId(int buyer_id,int cart_id){
        if (conn==null){
            conn = dbdetails.connect();
        }
        String query = "UPDATE buyers set cart_id = ? where buyer_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            try {
                preparedStatement.setInt(1,cart_id);
                preparedStatement.setInt(2,buyer_id);
                int rows =preparedStatement.executeUpdate();
                if(rows>0){
                    System.out.println("Cart_id Updated successfully");
                }else {
                    System.out.println("Unable to update cart id");
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateNumItems(int buyer_id,int count){
        if (conn==null){
            conn = dbdetails.connect();
        }
        String query = "UPDATE buyers set num_items_purch = num_items_purch + ? where buyer_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            try {
                preparedStatement.setInt(1,count);
                preparedStatement.setInt(2,buyer_id);
                int rows =preparedStatement.executeUpdate();
                if(rows>0){
                    System.out.println("Item count Updated successfully");
                }else {
                    System.out.println("Unable to update item count");
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
