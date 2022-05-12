package com.worwafi.users;

import com.worwafi.auctions.Auction;
import com.worwafi.others.AuctionObserver;
import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.others.GenericList;
import com.worwafi.others.Serialize;
import com.worwafi.others.Starter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BasicUser extends User implements AuctionObserver {
    protected boolean limit;
    protected String password;
    protected String bio;
    protected GenericList<AuctionedObject> possession;
    protected File objectFile;
    protected File moneyFile;

    /**
     * Sets the new registered user
     * @param username name
     * @param password pw
     * @param bio description
     */
    public BasicUser(String username, String password, String bio) {
        super(username);
        limit = false;
        this.password = password;
        this.bio = bio;
        objectFile = new File("D:\\skola\\txt\\" + username + "Objects.txt");
        moneyFile = new File("D:\\skola\\txt\\" + username + "Wallet.txt");
    }

    /**
     * Writes into log of the desired user
     * @param sum price to write
     */
    public void writeIntoCashAccount(double sum) {
        try {
            FileWriter writer = new FileWriter(moneyFile, true);
            if (sum < 0)
                writer.append("\n" + sum);
            else
                writer.append("\n+" + sum);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets the warehouse of desired user
     * @param possession warehouse
     */
    public void setPossession(GenericList<AuctionedObject> possession) {
        this.possession = possession;
    }

    public GenericList<AuctionedObject> getPossession() {
        return possession;
    }

    public File getObjectFile() {
        return objectFile;
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

    /**
     * Gets all the desired data for serialization
     * @return data for serialization
     */
    @Override
    public String getAllData() {
        return username + " " + bio + "\n" + possession.getAllData() + "\n " + objectFile.toString() + " " + moneyFile.toString();
    }

    /**
     * implements abstract method of the observer interface
     * @param auction auction to join into
     */
    @Override
    public void join(Auction auction) {
        auction.addBidder(this);
    }

    /**
     * method equivalent of .equals but just for the parameters, not the instances
     * @param user user to compare with
     * @return whether they contain the same data
     */
    @Override
    public boolean compare(Starter user) {
        if (!((BasicUser) user).getUsername().equals(username) || !((BasicUser) user).getPassword().equals(password) || !((BasicUser) user).getBio().equals(bio))
            return false;
        return true;
    }
}
