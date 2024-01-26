package pojos.Request;

import pojos.UserType;

import java.io.Serializable;

public class CreateAccountRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    public String username;
    public String password;

    public UserType userType;

    public String name;

    public CreateAccountRequest(String username, String password, UserType userType, String name) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }
}
