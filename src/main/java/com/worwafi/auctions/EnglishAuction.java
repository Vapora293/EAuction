package com.worwafi.auctions;

import com.worwafi.others.AuctionException;
import com.worwafi.others.AuctionTimer;
import com.worwafi.others.AuctionedObject;
import com.worwafi.others.ObjectStatus;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.users.User;

import java.util.ArrayList;

public class EnglishAuction extends Auction {
    public EnglishAuction(AuctionedObject win) {
        super(win);
    }

    @Override
    public int handleCycle(int cycle) {
        if(cycle > 3) {
            setEnd();
            return -1; //pribeh konci
        }
        if(callBidders()) {
            return 1;
        }
        return cycle+1;
    }
    @Override
    public String bid(User bidder, double price) {
        if(price < actualPrice*currentRaise) {
            return new AuctionException().notEnoughMoney();
        }
        actualPrice = price;
        super.bid(bidder, price);
        writeIntoLog(bidder, price);
        return null;
    }

    @Override
    public void callAuction() {
        auctionStatusListener.updateEnglishLayout();
        auctionStatusListener.englishButtonListeners();
    }

    private void writeIntoLog(User bidder, double price) {

    }
    @Override
    public String toString() {
        return "English auction";
    }

}
