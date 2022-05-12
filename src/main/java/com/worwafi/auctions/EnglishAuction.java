package com.worwafi.auctions;

import com.worwafi.others.AuctionException;
import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.users.User;


public class EnglishAuction extends Auction {

    EnglishAuction(String id, AuctionedObject win) {
        super(id, win);
    }

    //TODO priklad na polymorfizmus

    /**
     * Handles the cycle. If there is any bidder who bids in the call, timer gets reset and does all the cycle
     * from start. If there is no person to bet, cycle increases. There are 3 cycles only, then the auction ends
     *
     * @param cycle number of cycles of the timer
     * @return the cycle value
     */
    @Override
    public int handleCycle(int cycle) {
        if (cycle > 3) {
            setEnd();
            return -1;
        }
        if (callBidders()) {
            return 1;
        }
        return cycle + 1;
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
        if (price < actualPrice * currentRaise) {
            return new AuctionException().notEnoughMoney();
        }
        actualPrice = price;
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
        auctionStatusListener.englishButtonListeners();
    }

    /**
     * Gets all data from the auction
     * @return all data
     */
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

    /**
     * @return default method for listviews
     */
    @Override
    public String toString() {
        return "English auction: by " + win.getOwner() + " of " + win.getName();
    }
}
