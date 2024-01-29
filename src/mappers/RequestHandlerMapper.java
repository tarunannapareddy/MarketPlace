package mappers;

import dao.*;
import dbConnector.DBConnector;
import handlers.*;
import pojos.Operation;
import pojos.Request.RemoveItemRequest;
import pojos.Request.SearchItemRequest;

import java.sql.Connection;

public class RequestHandlerMapper {

    private static UserDAO userDAO = new UserDAO();
    private static SellerDAO sellerDAO = new SellerDAO();
    private static BuyerDAO buyerDAO = new BuyerDAO();

    private static ItemDAO itemDAO = new ItemDAO();

    private static CartDAO cartDAO = new CartDAO();

    private static FeedBackDAO feedBackDAO = new FeedBackDAO();
    public static LogInRequestHandler logInRequestHandler= new LogInRequestHandler(userDAO);
    public static CreateAccountHandler createAccountHandler = new CreateAccountHandler(userDAO, buyerDAO, sellerDAO);

    public static AddItemHandler addItemHandler = new AddItemHandler(itemDAO);

    public static GetItemsHandler getItemsHandler = new GetItemsHandler(itemDAO);
    public static RemoveItemHandler removeItemHandler = new RemoveItemHandler(itemDAO);
    public static UpdateItemHandler updateItemHandler = new UpdateItemHandler(itemDAO);

    public static SellerRatingHandler sellerRatingHandler = new SellerRatingHandler(sellerDAO);
    public static RateItemHandler rateItemHandler = new RateItemHandler(itemDAO, sellerDAO, feedBackDAO);
    public static SearchItemHandler searchItemHandler = new SearchItemHandler(itemDAO);

    public static UpdateCartHandler updateCartHandler = new UpdateCartHandler(cartDAO,itemDAO);

    public static RequestHandler getRequestHandler(Operation operation){
        switch (operation){
            case LOGIN: return logInRequestHandler;
            case CREATE_ACCOUNT: return createAccountHandler;
            case SELLER_RATING: return sellerRatingHandler;
            case SELLER_ADD_ITEM: return addItemHandler;
            case SELLER_REMOVE_ITEM: return removeItemHandler;
            case SELLER_GET_ITEMS: return getItemsHandler;
            case SELLER_UPDATE_ITEM : return updateItemHandler;
            case RATE_ITEM: return rateItemHandler;
            case SEARCH_ITEM: return searchItemHandler;
            case UPDATE_CART: return  updateCartHandler;
        }
        return null;
    }
}
