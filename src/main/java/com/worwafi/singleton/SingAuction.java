package com.worwafi.singleton;

import com.worwafi.auctions.Auction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SingAuction {
    private static SingAuction single_instance = null;

    private Auction actual;
    private File auctionData;
    private FileWriter auctionWriter;

    private SingAuction() {

    }

    public static SingAuction getInstance() {
        if (single_instance == null)
            single_instance = new SingAuction();
        return single_instance;
    }
    public void setAuction(Auction actual) {
        this.actual = actual;
        auctionData = new File("D:\\skola\\txt\\auctions.txt");
        try {
            auctionWriter = new FileWriter(auctionData, true);
        }
         catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void appendToFile(String s) {
        try {
            auctionWriter.append(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Auction getAuction() {
        return actual;
    }
}
