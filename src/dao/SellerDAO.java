package dao;

import pojos.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SellerDAO {
    private String url = "jdbc:postgresql://localhost/users";
    DButils dbdetails= new DButils(url);
    private Connection conn = null;
    public void createSeller(Seller seller){
        if (conn==null){
            conn = dbdetails.connect();
        }
        String  query= "insert into sellers (SELLER_ID,SELLER_NAME) values (?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, seller.getSellerId());
            preparedStatement.setString(2,seller.getSellerName());
            try {
                int rows =preparedStatement.executeUpdate();
                if(rows>0){
                    System.out.println("Seller Created successfully");
                }else {
                    System.out.println("Unable to create seller");
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Seller GetSellerInfo(Integer seller_id){
        if (conn==null){
            conn = dbdetails.connect();
        }
        String  query= "SELECT * from sellers where seller_id="+seller_id;

        Seller seller=null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, seller.getSellerId());
            preparedStatement.setString(2,seller.getSellerName());
            try {
                ResultSet rs =preparedStatement.executeQuery();
                while (rs.next()) {
                    seller=new Seller();
                    seller.setSellerId(rs.getInt(1));
                    seller.setSellerName(rs.getString(2));
                    seller.setPositiveReviewCount(rs.getInt(3));
                    seller.setNegativeReviewCount(rs.getInt(4));
                    seller.setTotalSold((rs.getInt(5)));
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return seller;
    }

    public void updateReviewCounts(Integer seller_id,boolean review){
        if (conn==null){
            conn = dbdetails.connect();
        }
        String query = null;
        if (review){
            query = "UPDATE sellers set POS_REV_COUNT = POS_REV_COUNT+1 where seller_id = "+seller_id;
        }else {
            query = "UPDATE sellers set NEG_REV_COUNT = NEG_REV_COUNT+1 where seller_id = "+seller_id;
        }

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            try {
                int rows =preparedStatement.executeUpdate();
                if(rows>0){
                    System.out.println("Review count Updated successfully");
                }else {
                    System.out.println("Unable to update review count");
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateItemsSold(Integer seller_id,Integer itemssold){
        if (conn==null){
            conn = dbdetails.connect();
        }

        String query = "UPDATE sellers set ITEMS_SOLD = ITEMS_SOLD+? where seller_id = ?";


        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            try {
                preparedStatement.setInt(1,itemssold);
                preparedStatement.setInt(2,seller_id);
                int rows =preparedStatement.executeUpdate();
                if(rows>0){
                    System.out.println("Item count Updated successfully");
                }else {
                    System.out.println("Unable to update Item count");
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
