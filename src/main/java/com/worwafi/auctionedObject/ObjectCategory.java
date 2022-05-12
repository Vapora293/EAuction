package com.worwafi.auctionedObject;

public enum ObjectCategory {
    JEWELLERY, ANTIQUE, PAINTING, SCULPTURE;

    /**
     * Enum toString override
     * @return String param of enum
     */
    @Override
    public String toString() {
        switch (this) {
            case JEWELLERY: return "jewellery";
            case ANTIQUE: return "antique";
            case PAINTING: return "painting";
            case SCULPTURE: return "sculpture";
            default: throw new IllegalStateException("Object category unknown");
        }
    }
}
