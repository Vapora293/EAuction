package com.worwafi.users;

public class UserTxt {
    protected String username;
    protected String password;

    public UserTxt(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
