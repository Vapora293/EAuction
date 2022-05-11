package com.worwafi.singleton;

import com.worwafi.others.BotNames;
import com.worwafi.others.GenericList;
import com.worwafi.users.BasicUser;
import com.worwafi.users.BotUser;
import com.worwafi.users.User;

import java.util.Random;

public class SingUserInfo {
    private static SingUserInfo single_instance = null;

    private BasicUser loggedUser;
    private GenericList<User> usersAvailable;

    private SingUserInfo() {
        usersAvailable = new GenericList<>();
        usersAvailable = makeBidders();
    }
    public static SingUserInfo getInstance() {
        if(single_instance == null)
            single_instance = new SingUserInfo();
        return single_instance;
    }

    public void setLoggedUser(BasicUser loggedUser) {
        this.loggedUser = loggedUser;
        usersAvailable.getList().add(loggedUser);
    }
    private GenericList<User> makeBidders() {
        Random rand = new Random();
        GenericList<User> localBidders = new GenericList<>();
        BotNames names = new BotNames();
        for(int i = 1; i < rand.nextInt(50)+50; i++) {
            BotUser actual = new BotUser(names.getName());
            actual.getCashAccount().setCredit(rand.nextInt(10000));
            localBidders.getList().add(actual);
        }
        return localBidders;
    }
    public void clear() {
        single_instance = null;
    }

    public BasicUser getLoggedUser() {
        return loggedUser;
    }

    public GenericList<User> getUsersAvailable() {
        return usersAvailable;
    }
}
