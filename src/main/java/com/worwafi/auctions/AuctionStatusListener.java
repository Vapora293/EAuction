package com.worwafi.auctions;

//TODO Interface 1
public interface AuctionStatusListener {
    //TODO taka trocha default interface metoda
    default String auctionStartQuote() {
        return "Auction is being started";
    }
    void updateEnglishLayout();
    void decreasePrice();
    void updateReverseLayout();
    void englishButtonListeners();
    void reverseButtonListeners();
}
