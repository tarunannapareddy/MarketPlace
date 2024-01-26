package handlers;

import dao.AccountDAO;
import dao.UsersDAO;
import pojos.Account;
import pojos.Request.LogInRequest;

public class CreateAccountHandler implements RequestHandler{
    public AccountDAO accountDAO;
    @Override
    public Object handle(Object request) {
        String password = (String) request;
        UsersDAO usersDAO = new UsersDAO();
        Integer userid=usersDAO.createUser(password);
        return userid;
    }
}
