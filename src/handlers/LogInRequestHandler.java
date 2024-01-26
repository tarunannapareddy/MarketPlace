package handlers;

import dao.UserDAO;
import pojos.User;
import pojos.Request.LogInRequest;

public class LogInRequestHandler implements RequestHandler{
    public UserDAO userDAO;

    public LogInRequestHandler(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Object handle(Object request) {
        LogInRequest loginRequest = (LogInRequest) request;
        User user = userDAO.getUser(loginRequest.username, loginRequest.password);
        if(user == null){
            return -1;
        }
        return user.getId();
    }
}
