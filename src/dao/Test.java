package dao;

import pojos.Buyer;
import pojos.Request.BuyerRequest;

public class Test {
    public static void main(String[] args) {
        int buyerid=1;
        String pw = "pass1";
        BuyerDAO buyerDAO=new BuyerDAO();
        UsersDAO usersDAO=new UsersDAO();
       // Buyer buyerinfo = buyerDAO.getBuyerInfo(buyerid);
        //System.out.println(buyerinfo.getName()+" "+buyerinfo.getItemsPurchased());
        int user =usersDAO.createUsers(pw);
        System.out.println(user);
    }
}
