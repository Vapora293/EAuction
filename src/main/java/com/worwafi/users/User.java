package com.worwafi.users;

import com.worwafi.others.Starter;
import com.worwafi.others.Wallet;

public abstract class User extends Starter {
    protected String username;
    protected Wallet cashAccount;

    public User(String username) {
        cashAccount = new Wallet();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return getUsername();
    }

    public Wallet getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(Wallet actual) {
        cashAccount = actual;
    }

}
