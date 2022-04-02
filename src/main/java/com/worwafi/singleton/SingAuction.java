package com.worwafi.singleton;

import com.worwafi.auctions.Auction;

import java.io.File;

public class SingAuction {
    private static SingAuction single_instance = null;

    private Auction actual;
    private File auctionData;

    private SingAuction() {

    }

    public static SingAuction getInstance() {
        if (single_instance == null)
            single_instance = new SingAuction();
        return single_instance;
    }
    public void setAuction(Auction actual) {
        this.actual = actual;
        auctionData = new File("D:\\skola\\txt\\auctions.txt");
    }
    public Auction getAuction() {
        return actual;
    }
}
