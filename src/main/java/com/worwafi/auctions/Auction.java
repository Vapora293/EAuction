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
        bidders = getBidders();
        this.win = win;
    }

    private ArrayList<User> getBidders() {
        Random rand = new Random();
        ArrayList<User> localBidders = new ArrayList<>(rand.nextInt(50));
        localBidders.set(0, SingUserInfo.getInstance().getLoggedUser());
        BotNames names = new BotNames();
        for(int i = 1; i < localBidders.size(); i++) {
            BotUser actual = new BotUser(names.getName());
            actual.getCashAccount().setCredit(win.getExpSelPrice() * (1 + rand.nextDouble()));
            localBidders.set(i, actual);
        }
        return localBidders;
    }

    private String getLocalId() {
        File auctionFile = new File("D:\\skola\\txt\\auctions.txt");
        String[] help;
        String lastID = new String();
        try {
            Scanner myReader = new Scanner(auctionFile);
            while(myReader.hasNext("!")) {
                String auctionInfo = myReader.next("!");
                help = auctionInfo.split("-");
                lastID = help[0];
                char letter = lastID.charAt(0);
                char number = lastID.charAt(1);
                if(number == '0') {
                    number = '1';
                }
                if(number == '9') {
                    number = '0';
                    letter++;
                }
                number++;
                return String.valueOf(letter + number);
            }
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
