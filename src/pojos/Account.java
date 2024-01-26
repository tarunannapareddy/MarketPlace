package pojos;

public class Account {
//    String userName;
    String password;
    Integer id;
    public Account() {
//        this.userName = userName;
        this.password = password;
        this.id = id;
    }
    public Account(Integer id,String password) {
//        this.userName = userName;
        this.password = password;
        this.id = id;
    }

    public Integer getUserid() {
        return id;
    }

    public void setUserName(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
