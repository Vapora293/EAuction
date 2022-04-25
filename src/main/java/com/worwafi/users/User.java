package com.worwafi.users;

import com.worwafi.others.AuctionedObject;
import com.worwafi.others.HelpMethods;
import com.worwafi.others.Starter;
import com.worwafi.others.Wallet;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.LinkedList;

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

    public Wallet getCashAccount() {
        return cashAccount;
    }
    public void setCashAccount(Wallet actual) {
        cashAccount = actual;
    }

    abstract void bid();
    abstract void raise();
    abstract void withdrawFromAuction();
}
