package com.worwafi.auctions;

import com.worwafi.others.AuctionTimer;
import com.worwafi.others.AuctionedObject;
import com.worwafi.users.User;

import java.util.ArrayList;

public class EnglishAuction extends Auction {
    public EnglishAuction(AuctionedObject win) {
        super(win);
    }

    @Override
    public String bid(User bidder, double price) {
        super.bid(bidder, price);
        writeIntoLog(bidder, price);
        return null;
    }

    private void writeIntoLog(User bidder, double price) {

    }

}
