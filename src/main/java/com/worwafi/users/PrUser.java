package com.worwafi.users;

import com.worwafi.others.AuctionedObject;

import java.util.LinkedList;

public class PrUser extends BasicUser {
    public PrUser(BasicUser user) {
        super(user.username, user.password, user.bio);
        this.setPossesion(user.getPossesion());
        this.setLimit(false);
    }
    void paySubscription() {

    }
}
