package com.worwafi.others;

import com.worwafi.auctions.Auction;

public interface AuctionObserver {
    /**
     * makes the method for inviting observers to join the auction
     * @param auction
     */
    void join(Auction auction);
}
