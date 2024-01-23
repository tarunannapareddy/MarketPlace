package handlers;

import pojos.Request.LogInRequest;

public class LogInRequestHandler implements RequestHandler{
    @Override
    public Object handle(Object request) {
        LogInRequest loginRequest = (LogInRequest) request;
        System.out.println("logInRequestHandle"+loginRequest.username+" "+loginRequest.password);
        return true;
    }
}
