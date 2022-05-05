package com.worwafi.users;

import com.worwafi.auctions.Auction;
import com.worwafi.others.AuctionObserver;

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

    @Override
    public void join(Auction auction) {
        Random rand = new Random();
        if(rand.nextInt(100) < 30) {
            auction.addBidder(this);
        }
    }
}
