package com.worwafi.auctions;

import com.worwafi.others.AuctionedObject;
import com.worwafi.others.ObjectStatus;
import com.worwafi.users.User;

public class ReverseAuction extends Auction{
    public ReverseAuction(AuctionedObject win) {
        super(win);
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
        setEnd();
        return null;
    }
    //TODO priklad na polymorfizmus
    @Override
    public void callAuction() {
        auctionStatusListener.updateEnglishLayout();
        auctionStatusListener.reverseButtonListeners();
    }

    @Override
    public String toString() {
        return "Reverse Auction";
    }
}
