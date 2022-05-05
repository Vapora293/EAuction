package com.worwafi.others;

public enum Deseralization {
    USERS, WAREHOUSE, WALLET, AUCTIONS;
    @Override
    public String toString() {
        switch (this) {
            case USERS: return "users";
            case WAREHOUSE: return "warehouse";
            case WALLET: return "wallet";
            case AUCTIONS: return "auctions";
            default: throw new IllegalStateException("deserialization failed");
        }
    }
}
