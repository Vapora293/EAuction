package com.worwafi.auctions;

import com.worwafi.auctionedObject.AuctionedObject;

public class AuctionFactory {
    //TODO Factory method design pattern

    /**
     * Factory method pattern implementation, used to create Auctions
     * @param kindOfAuction desired kind of auction
     * @param id id of the auction to be serialized
     * @param win objects to be auctioned
     * @return new desired auction
     */
    public Auction createAuction(String kindOfAuction, String id, AuctionedObject win) {
        if (kindOfAuction == null || kindOfAuction.isEmpty())
            return null;
        switch (kindOfAuction) {
            case "e":
            case "English":
            case "english":
            case "English Auction":
            case "English auction":
            case "english auction":
                return new EnglishAuction(id, win);
            case "r":
            case "Reverse":
            case "reverse":
            case "Reverse Auction":
            case "Reverse auction":
            case "reverse auction":
                return new ReverseAuction(id, win);
            default:
                throw new IllegalArgumentException("Unknown auction type" + kindOfAuction);
        }
    }
}
