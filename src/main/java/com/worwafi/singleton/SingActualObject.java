package com.worwafi.singleton;

import com.worwafi.auctionedObject.AuctionedObject;

import java.util.ArrayList;

public class SingActualObject {
    private static SingActualObject single_instance = null;
    private static ArrayList<AuctionedObject.Memento> history;

    private AuctionedObject actual;
    private boolean neww;

    private SingActualObject() {
        history = new ArrayList<>();
    }

    /**
     * @return returns the instance of this Singleton
     */
    public static SingActualObject getInstance() {
        if (single_instance == null)
            single_instance = new SingActualObject();
        return single_instance;
    }

    public void setObject(AuctionedObject actual, boolean neww) {
        this.actual = actual;
        this.neww = neww;
    }

    public void addToHistory(AuctionedObject object) {
        history.add(object.saveToMemento());
    }

    /**
     * restores first instance of the object
     *
     * @param object object to restore information into
     */
    public void restoreFromHistory(AuctionedObject object) {
        object.restoreFromMemento(history.get(0));
    }

    public AuctionedObject.Memento getFirst() {
        return history.get(0);
    }

    public AuctionedObject getObject() {
        return actual;
    }

    public void clear() {
        single_instance = null;
    }

    public boolean getNeww() {
        return neww;
    }
}
