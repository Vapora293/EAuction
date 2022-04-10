package com.worwafi.auctions;

import com.worwafi.others.*;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BotUser;
import com.worwafi.users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Auction {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    protected AuctionStatusListener auctionStatusListener;
    protected String id;
    protected AuctionedObject win;
    protected AuctionTimer timeInfo;
    protected ArrayList<User> bidders;
    protected double actualPrice;
    protected User actualWinner;
    protected double currentRaise;
    protected boolean end;

    public Auction(AuctionedObject win) {
        id = getLocalId();
        this.win = win;
        bidders = makeBidders();
        actualPrice = SingActualObject.getInstance().getObject().getStartingPrice();
        actualWinner = SingUserInfo.getInstance().getLoggedUser();
        currentRaise = 0.10;
        end = false;
    }

    protected boolean callBidders() {
        for (int i = 1; i < SingAuction.getInstance().getAuction().getBidders().size(); i++) {
            Random rand = new Random();
            int check = rand.nextInt(100);
            double price = SingAuction.getInstance().getAuction().getActualPrice() * (1 + Double.parseDouble(df.format(rand.nextDouble())));
            if (check < 10 && SingAuction.getInstance().getAuction().getBidders().get(i).getCashAccount().getCredit() > price) {
                SingAuction.getInstance().getAuction().bid(SingAuction.getInstance().getAuction().getBidders().get(i), price);
                auctionStatusListener.updateEnglishLayout();
                return true;
            }
        }
        return false;
    }

    private ArrayList<User> makeBidders() {
        Random rand = new Random();
        ArrayList<User> localBidders = new ArrayList<>();
        localBidders.add(SingUserInfo.getInstance().getLoggedUser());
        BotNames names = new BotNames();
        for(int i = 1; i < rand.nextInt(50); i++) {
            BotUser actual = new BotUser(names.getName());
            actual.getCashAccount().setCredit(Math.round(win.getExpSelPrice() * (1 + rand.nextDouble())));
            localBidders.add(actual);
        }
        return localBidders;
    }

    public abstract int handleCycle(int cycle);
    private String getLocalId() {
        File auctionFile = new File("D:\\skola\\txt\\auctions.txt");
        String[] help;
        String lastID;
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
        actualWinner = bidder;
        return null;
    }

    public abstract void callAuction();

    public void setEnd() {
        win.setStatus(ObjectStatus.SOLD);
        end = true;
    }

    public ArrayList<User> getBidders() {
        return bidders;
    }

    public User getActualWinner() {
        return actualWinner;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setAuctionStatusListener(AuctionStatusListener auctionStatusListener) {
        this.auctionStatusListener = auctionStatusListener;
    }
}
