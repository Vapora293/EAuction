package com.worwafi.others;

import com.worwafi.auctions.Auction;

public interface AuctionObserver {
    void join(Auction auction);
}
