package com.worwafi.users;

import com.worwafi.others.AuctionedObject;
import com.worwafi.others.GenericList;
import com.worwafi.others.HelpMethods;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BasicUser extends User {
    protected boolean limit;
    protected String password;
    protected String bio;
    protected GenericList<AuctionedObject> possession;
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
    public void setPossession(GenericList<AuctionedObject> possession) {
        this.possession = possession;
    }
    public GenericList<AuctionedObject> getPossession() {
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

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getAllData() {
        return username + " " + bio + "\n" + possession.getAllData() + "\n " + objectFile.toString() + " " + moneyFile.toString();
    }
}
