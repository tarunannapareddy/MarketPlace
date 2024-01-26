package mappers;

import dao.BuyerDAO;
import dao.SellerDAO;
import dao.UserDAO;
import dbConnector.DBConnector;
import handlers.CreateAccountHandler;
import handlers.LogInRequestHandler;
import handlers.RequestHandler;
import pojos.Operation;

import java.sql.Connection;

public class RequestHandlerMapper {

    private static UserDAO userDAO = new UserDAO();
    private static SellerDAO sellerDAO = new SellerDAO();
    private static BuyerDAO buyerDAO = new BuyerDAO();
    public static LogInRequestHandler logInRequestHandler= new LogInRequestHandler(userDAO);
    public static CreateAccountHandler createAccountHandler = new CreateAccountHandler(userDAO, buyerDAO, sellerDAO);

    public static RequestHandler getRequestHandler(Operation operation){
        switch (operation){
            case LOGIN: return logInRequestHandler;
            case CREATE_ACCOUNT: return createAccountHandler;
        }
        return null;
    }
}
