package com.worwafi.auctionedObject;

public enum ObjectStatus {
    SOLD, FORSALE, STORED;
    @Override
    public String toString() {
        switch (this) {
            case SOLD: return "sold";
            case FORSALE: return "for sale";
            case STORED: return "stored";
            default: throw new IllegalStateException("Object status unknown");
        }
    }
}
