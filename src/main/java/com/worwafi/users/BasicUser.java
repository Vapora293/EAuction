package com.worwafi.users;

import com.worwafi.others.AuctionedObject;

import java.io.File;
import java.util.LinkedList;

public class BasicUser extends User {
    protected boolean limit;

    public BasicUser(String username, String password, String bio) {
        super(username, password, bio);
        limit = false;
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

}
