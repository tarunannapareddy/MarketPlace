package pojos.Request;

import java.io.Serializable;


public class LogInRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    public Integer userid;
    public String password;

    public LogInRequest(Integer userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
