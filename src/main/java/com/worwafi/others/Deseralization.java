package com.worwafi.others;

public enum Deseralization {
    USERS, WAREHOUSE, WALLET, AUCTION;
    @Override
    public String toString() {
        switch (this) {
            case USERS: return "users";
            case WAREHOUSE: return "warehouse";
            case WALLET: return "wallet";
            case AUCTION: return "auction";
            default: throw new IllegalStateException("deserialization failed");
        }
    }
}
