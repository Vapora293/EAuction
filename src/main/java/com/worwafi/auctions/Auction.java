package com.worwafi.auctions;

import com.worwafi.others.AuctionException;
import com.worwafi.others.AuctionTimer;
import com.worwafi.others.AuctionedObject;
import com.worwafi.others.BotNames;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BotUser;
import com.worwafi.users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Auction {
    String id;
    AuctionedObject win;
    AuctionTimer timeInfo;
    ArrayList<User> bidders;
    double actualPrice;
    User actualWinner;
    double currentRaise;

    public Auction(AuctionedObject win) {
        id = getLocalId();
        this.win = win;
        bidders = getBidders();
    }

    private ArrayList<User> getBidders() {
        Random rand = new Random();
        ArrayList<User> localBidders = new ArrayList<User>();
        localBidders.add(SingUserInfo.getInstance().getLoggedUser());
        BotNames names = new BotNames();
        for(int i = 1; i < rand.nextInt(50); i++) {
            BotUser actual = new BotUser(names.getName());
            actual.getCashAccount().setCredit(win.getExpSelPrice() * (1 + rand.nextDouble()));
            localBidders.add(actual);
        }
        return localBidders;
    }

    private String getLocalId() {
        File auctionFile = new File("D:\\skola\\txt\\auctions.txt");
        String[] help;
        String lastID = new String();
        char letter = 0;
        char number = 0;
        try {
            Scanner myReader = new Scanner(auctionFile);
            boolean start = true;
            while(myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if(start) {
                    help = line.split("-");
                    lastID = help[0];
                    letter = lastID.charAt(0);
                    number = lastID.charAt(1);
                    start = false;
                }
                if (line.contains("!")) {
                    start = true;
                }
            }
            if(number == '0') {
                number = '1';
            }
            if(number == '9') {
                number = '0';
                letter++;
            }
            number++;
            return Character.toString(letter) + Character.toString(number);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String bid(User bidder, double price) {
        if(price < actualPrice*currentRaise) {
            return new AuctionException().notEnoughMoney();
        }
        actualPrice = price;
        actualWinner = bidder;
        return null;
    }


}