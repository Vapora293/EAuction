package com.worwafi.singleton;

import com.worwafi.others.AuctionedObject;

public class SingActualObject {
    private static SingActualObject single_instance = null;

    private AuctionedObject actual;
    private boolean neww;

    private SingActualObject() {

    }
    public static SingActualObject getInstance() {
        if(single_instance == null)
            single_instance = new SingActualObject();
        return single_instance;
    }
    public void setObject(AuctionedObject actual, boolean neww) {
        this.actual = actual;
        this.neww = neww;
    }
    public AuctionedObject getObject() {
        return actual;
    }
    public boolean getNeww() {
        return neww;
    }
}
