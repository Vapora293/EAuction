package com.worwafi.auctions;

public interface AuctionStatusListener {
    default String auctionStartQuote() {
        return "Auction is being started";
    }
    void updateEnglishLayout();
    void decreasePrice();
    void updateReverseLayout();
    void englishButtonListeners();
    void reverseButtonListeners();
}
