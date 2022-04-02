package com.worwafi.users;

import com.worwafi.others.AuctionedObject;

import java.io.File;
import java.util.LinkedList;

public class BasicUser extends User {
    protected boolean limit;
    protected String password;
    protected String bio;
    protected LinkedList<AuctionedObject> possesion;
    protected File objectFile;
    protected File moneyFile;

    public BasicUser(String username, String password, String bio) {
        super(username);
        limit = false;
        this.password = password;
        this.bio = bio;
        objectFile = new File("D:\\skola\\txt\\" + username + "Objects.txt");
        moneyFile = new File("D:\\skola\\txt\\" + username + "Wallet.txt");
    }

    void bid() {

    }

    void raise() {
    }

    void withdrawFromAuction() {
    }
    void whisper() {

    }
    void upgradeSubscription() {

    }
    void setPossesion(LinkedList<AuctionedObject> possesion) {
        this.possesion = possesion;
    }
    LinkedList<AuctionedObject> getPossesion() {
        return possesion;
    }
    boolean getLimit() {
        return limit;
    }
    void setLimit(boolean limit) {
        this.limit = limit;
    }
    public File getObjectFile() {
        return objectFile;
    }

    public File getMoneyFile() {
        return moneyFile;
    }
    public String getBio() {
        return bio;
    }
    public String getPassword() {
        return password;
    }

}
