package Handlers;

import Pojos.Request.LogInRequest;

public class CreateAccountHandler implements RequestHandler{
    @Override
    public Object handle(Object request) {
        LogInRequest loginRequest = (LogInRequest) request;
        System.out.println("CreateAccount"+loginRequest.username+" "+loginRequest.password);
        return true;
    }
}
