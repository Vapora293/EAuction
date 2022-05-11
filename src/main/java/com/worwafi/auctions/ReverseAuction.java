package com.worwafi.auctions;

import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.users.User;

public class ReverseAuction extends Auction{
    ReverseAuction(String id, AuctionedObject win) {
        super(id, win);
    }

    //TODO priklad na polymorfizmus
    @Override
    public int handleCycle(int cycle) {
        if(callBidders()) {
            auctionStatusListener.updateReverseLayout();
            setEnd();
            return -1;
        }
        actualPrice -= actualPrice*currentRaise;
        auctionStatusListener.decreasePrice();
        return 0;
    }

    @Override
    public String bid(User bidder, double price) {
        super.bid(bidder, price);
        return null;
    }
    //TODO priklad na polymorfizmus
    @Override
    public void callAuction() {
        auctionStatusListener.updateEnglishLayout();
        auctionStatusListener.reverseButtonListeners();
    }
    @Override
    public String getAllData() {
        return "rv . " + id + " . " + win.getOwner().toString() + " . " + win.getName();
    }
    public String getNecessaryData() {
        return "rv . " + id + " . " + win.getOwner() + " . " + win.getName();
    }
    public String toString() {
        return "Reverse auction: by " + win.getOwner() + " of " + win.getName();
    }
    @Override
    public String getName() {
        return super.getName() + " Reverse Auction";
    }
}
