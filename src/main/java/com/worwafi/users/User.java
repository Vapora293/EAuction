package com.worwafi.users;

import com.worwafi.others.AuctionedObject;
import com.worwafi.others.Wallet;

import java.io.File;
import java.util.LinkedList;

public abstract class User extends UserTxt {
    protected Wallet cashAccount;
    protected String bio;
    protected LinkedList<AuctionedObject> possesion;
    protected File objectFile;
    protected File moneyFile;

    public User(String username, String password, String bio) {
        super(username, password);
        cashAccount = new Wallet();
        this.bio = bio;
        objectFile = new File("D:\\skola\\txt\\" + username + "Objects.txt");
        moneyFile = new File("D:\\skola\\txt\\" + username + "Wallet.txt");
    }

    public String getBio() {
        return bio;
    }

    public Wallet getCashAccount() {
        return cashAccount;
    }

    public File getObjectFile() {
        return objectFile;
    }

    public File getMoneyFile() {
        return moneyFile;
    }
    public void setCashAccount(Wallet actual) {
        cashAccount = actual;
    }

    abstract void bid();
    abstract void raise();
    abstract void withdrawFromAuction();
}
