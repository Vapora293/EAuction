package com.worwafi.auctionedObject;

public enum ObjectStatus {
    SOLD, FORSALE, STORED;
    /**
     * Enum toString override
     * @return String param of enum
     */
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
