package com.worwafi.singleton;

import com.worwafi.auctions.Auction;
import com.worwafi.others.GenericList;
import com.worwafi.others.Serialize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SingAuction {
    private static SingAuction single_instance = null;

    private GenericList<Auction> allAuctions;
    private Auction actual;
    private boolean newAuction;

    private SingAuction() {
        Serialize serialize = new Serialize();
        try {
            allAuctions = new GenericList<>();
            allAuctions = (GenericList<Auction>) serialize.readObject("auctions");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static SingAuction getInstance() {
        if (single_instance == null)
            single_instance = new SingAuction();
        return single_instance;
    }

    public void setAuction(Auction actual, boolean newAuction) {
        this.actual = actual;
        this.newAuction = newAuction;
    }
    public boolean isNewAuction() {
        return newAuction;
    }

    public Auction getAuction() {
        return actual;
    }
}
