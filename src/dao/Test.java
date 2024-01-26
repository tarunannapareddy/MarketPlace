package dao;

import pojos.Buyer;
import pojos.Request.BuyerRequest;

public class Test {
    public static void main(String[] args) {
        BuyerRequest buyerRequest= new BuyerRequest(1);
        BuyerDAO buyerDAO=new BuyerDAO();
        Buyer buyerinfo = new Buyer();
        buyerinfo=buyerDAO.getBuyerInfo(buyerRequest);
        System.out.println(buyerinfo.getName()+" "+buyerinfo.getItemsPurchased());
    }
}
