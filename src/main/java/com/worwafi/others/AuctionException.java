package com.worwafi.others;

public class AuctionException {
    /**
     * Own exception throws a warning
     * @return warning
     */
    public String notEnoughMoney() {
        return "The bid is lower than expected";
    }
}
