package com.worwafi.singleton;

import com.worwafi.others.AuctionedObject;

import java.util.ArrayList;

public class SingActualObject {
    private static SingActualObject single_instance = null;
    private static ArrayList<AuctionedObject.Memento> history;

    private AuctionedObject actual;
    private boolean neww;

    private SingActualObject() {
        history = new ArrayList<>();
    }

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
    public void restoreFromHistory(AuctionedObject object) {
        object.restoreFromMemento(history.get(0));
    }

    public AuctionedObject.Memento getFirst() {
        return history.get(0);
    }

    public AuctionedObject getObject() {
        return actual;
    }

    public boolean getNeww() {
        return neww;
    }
}
