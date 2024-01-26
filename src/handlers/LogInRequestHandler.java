package handlers;

import dao.UsersDAO;
import pojos.Account;
import pojos.Request.LogInRequest;

public class LogInRequestHandler implements RequestHandler{
    @Override
    public Object handle(Object request) {
        LogInRequest loginRequest = (LogInRequest) request;
        UsersDAO user = new UsersDAO();
        Account account = user.getUserDetails(loginRequest.userid);
        if(account == null){
            System.out.println("User doesn't exist");
            return false;
        }else if(!account.getPassword().equals(loginRequest.password)){
            System.out.println("Invalid password");
            return false;
        }else{
            return true;
        }
    }
}
