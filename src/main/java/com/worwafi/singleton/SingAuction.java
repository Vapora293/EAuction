package com.worwafi.singleton;

import com.worwafi.auctions.Auction;

public class SingAuction {
    private static SingAuction single_instance = null;

    private Auction actual;

    private SingAuction() {

    }

    public static SingAuction getInstance() {
        if (single_instance == null)
            single_instance = new SingAuction();
        return single_instance;
    }
    public void setAuction(Auction actual) {
        this.actual = actual;
    }
    public Auction getAuction() {
        return actual;
    }
}
