package com.worwafi.auctions;

import com.worwafi.others.AuctionException;
import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.users.User;


public class EnglishAuction extends Auction {
    EnglishAuction(String id, AuctionedObject win) {
        super(id, win);
    }
    //TODO priklad na polymorfizmus
    @Override
    public int handleCycle(int cycle) {
        if(cycle > 3) {
            setEnd();
            return -1;
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
        return null;
    }
    //TODO priklad na polymorfizmus
    @Override
    public void callAuction() {
        auctionStatusListener.updateEnglishLayout();
        auctionStatusListener.englishButtonListeners();
    }
    @Override
    public String getAllData() {
        return "en . " + id + " . " + win.getOwner().toString() + " . " + win.getName();
    }
    public String getNecessaryData() {
        return "en . " + id + " . " + win.getOwner() + " . " + win.getName();
    }
    @Override
    public String getName() {
        return super.getName() + " English Auction";
    }
    @Override
    public String toString() {
        return "English auction: by " + win.getOwner() + " of " + win.getName();
    }

    private void writeIntoLog(User bidder, double price) {

    }

}
