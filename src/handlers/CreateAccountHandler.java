package handlers;

import dao.AccountDAO;
import pojos.Account;
import pojos.Request.LogInRequest;

public class CreateAccountHandler implements RequestHandler{
    public AccountDAO accountDAO;
    @Override
    public Object handle(Object request) {
        LogInRequest loginRequest = (LogInRequest) request;
        Account account = accountDAO.getAccountInfo(loginRequest);
        return account.getId();
    }
}
