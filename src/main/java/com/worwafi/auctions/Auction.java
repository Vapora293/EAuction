package com.worwafi.auctions;

import com.worwafi.others.AuctionTimer;
import com.worwafi.others.AuctionedObject;
import com.worwafi.users.User;

import java.util.ArrayList;

public abstract class Auction {
    AuctionedObject win;
    AuctionTimer timeInfo;
    ArrayList<User> bidders;
    double actualPrice;
    User actualWinner;
    double currentRaise;

    public void bid(User bidder, double price) {
        if(price < actualPrice*currentRaise) {

        }
        actualPrice = price;
        actualWinner = bidder;
    }

}
