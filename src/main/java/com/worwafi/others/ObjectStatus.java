package com.worwafi.others;

public enum ObjectStatus {
    SOLD, FORSALE, STORED;
    @Override
    public String toString() {
        switch (this) {
            case SOLD: return "sold";
            case FORSALE: return "for sale";
            case STORED: return "stored only";
            default: throw new IllegalStateException("Object status unknown");
        }
    }
}
