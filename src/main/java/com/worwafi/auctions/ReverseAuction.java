package com.worwafi.auctions;

import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.users.User;

public class ReverseAuction extends Auction{
    ReverseAuction(String id, AuctionedObject win) {
        super(id, win);
    }

    //TODO priklad na polymorfizmus
    /**
     * Handles the cycle. If there is any bidder who bids in the call, auction ends.
     * If there is no person to bet, the price decreases. On the opposite, the auction ends
     *
     * @param cycle number of cycles of the timer
     * @return 0 or 1 depending on the process of betting
     */
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
    /**
     * Process of bidding in the auction
     *
     * @param bidder bidder to be winner at that time
     * @param price  price to be betted
     * @return used for Auction exception
     */
    @Override
    public String bid(User bidder, double price) {
        super.bid(bidder, price);
        return null;
    }
    //TODO priklad na polymorfizmus
    /**
     * Updates the textAreas in the view
     */
    @Override
    public void callAuction() {
        auctionStatusListener.updateEnglishLayout();
        auctionStatusListener.reverseButtonListeners();
    }
    /**
     * Gets all data from the auction
     * @return all data
     */
    @Override
    public String getAllData() {
        return "rv . " + id + " . " + win.getOwner().toString() + " . " + win.getName();
    }
    public String getNecessaryData() {
        return "rv . " + id + " . " + win.getOwner() + " . " + win.getName();
    }
    @Override
    public String toString() {
        return "Reverse auction: by " + win.getOwner() + " of " + win.getName();
    }
    @Override
    public String getName() {
        return super.getName() + " Reverse Auction";
    }
}
