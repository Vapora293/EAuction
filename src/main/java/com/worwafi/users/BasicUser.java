package com.worwafi.users;

import com.worwafi.others.AuctionedObject;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class BasicUser extends User implements Serializable {
    protected boolean limit;
    protected String password;
    protected String bio;
    protected transient ObservableList<AuctionedObject> possession;
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
    public void writeIntoCashAccount(double sum) {
        try {
            FileWriter writer = new FileWriter(moneyFile, true);
            writer.append("\n+" + sum);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setPossession(ObservableList<AuctionedObject> possession) {
        this.possession = possession;
    }
    public ObservableList<AuctionedObject> getPossession() {
        return possession;
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
