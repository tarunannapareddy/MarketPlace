package Mappers;

import Handlers.CreateAccountHandler;
import Handlers.LogInRequestHandler;
import Handlers.RequestHandler;
import Pojos.Operation;

public class RequestHandlerMapper {
    public static LogInRequestHandler logInRequestHandler= new LogInRequestHandler();
    public static CreateAccountHandler createAccountHandler = new CreateAccountHandler();

    public static RequestHandler getRequestHandler(Operation operation){
        switch (operation){
            case LOGIN: return logInRequestHandler;
            case CREATE_ACCOUNT: return createAccountHandler;
        }
        return null;
    }
}
