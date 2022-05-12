package com.worwafi.users;

import com.worwafi.auctions.Auction;
import com.worwafi.others.AuctionObserver;
import com.worwafi.others.Starter;

import java.util.Random;

public class BotUser extends User implements AuctionObserver {
    public BotUser(String username) {
        super(username);
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getAllData() {
        return username;
    }

    /**
     * implementation of the observer interface
     *
     * @param auction auction to join to
     */
    @Override
    public void join(Auction auction) {
        Random rand = new Random();
        if (rand.nextInt(100) < 30) {
            auction.addBidder(this);
        }
    }

    @Override
    public boolean compare(Starter actual) {
        return false;
    }
}
