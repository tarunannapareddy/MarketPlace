package handlers;

import Exceptions.InvalidDataException;
import dao.UserDAO;
import pojos.Session;
import pojos.User;
import pojos.Request.LogInRequest;

public class LogInRequestHandler implements RequestHandler{
    public UserDAO userDAO;

    public LogInRequestHandler(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Object handle(Object request, Session session) throws InvalidDataException {
        LogInRequest loginRequest = (LogInRequest) request;
        User user = userDAO.getUser(loginRequest.username, loginRequest.password);
        if(user == null){
            throw  new InvalidDataException("invalid username or Password");
        }
        session.setSessionId(user.getId());
        return user.getId();
    }
}
