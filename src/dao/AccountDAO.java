package dao;

import pojos.Account;
import pojos.Request.LogInRequest;

public class AccountDAO {

    public Account getAccountInfo(LogInRequest logInRequest){
        return new Account(logInRequest.username, logInRequest.password, 5);
    }
}
