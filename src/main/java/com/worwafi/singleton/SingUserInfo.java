package com.worwafi.singleton;

import com.worwafi.users.BasicUser;

public class SingUserInfo {
    private static SingUserInfo single_instance = null;

    private BasicUser loggedUser;

    private SingUserInfo() {
    }
    public static SingUserInfo getInstance() {
        if(single_instance == null)
            single_instance = new SingUserInfo();
        return single_instance;
    }

    public void setLoggedUser(BasicUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    public BasicUser getLoggedUser() {
        return loggedUser;
    }
}
