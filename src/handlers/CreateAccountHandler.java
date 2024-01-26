package handlers;

import dao.BuyerDAO;
import dao.SellerDAO;
import dao.UserDAO;
import pojos.Buyer;
import pojos.Request.CreateAccountRequest;
import pojos.Request.LogInRequest;
import pojos.Seller;
import pojos.UserType;

public class CreateAccountHandler implements RequestHandler{
    public UserDAO userDAO;
    public BuyerDAO buyerDAO;

    public SellerDAO sellerDAO;
    public CreateAccountHandler(UserDAO userDAO, BuyerDAO buyerDAO, SellerDAO sellerDAO) {
        this.userDAO = userDAO;
        this.buyerDAO = buyerDAO;
        this.sellerDAO = sellerDAO;
    }

    @Override
    public Object handle(Object request) {
        CreateAccountRequest createAccountRequest = (CreateAccountRequest) request;
        int id = userDAO.createUser(createAccountRequest.username,createAccountRequest.password);
        if(id !=-1){
            if(UserType.BUYER.equals(createAccountRequest.userType)){
                 buyerDAO.createBuyer(new Buyer(id, createAccountRequest.getName()));
            }if(UserType.SELLER.equals(createAccountRequest.userType)){
                 sellerDAO.createSeller(new Seller(id, createAccountRequest.getName()));
            }
        }
        return id;
    }
}
