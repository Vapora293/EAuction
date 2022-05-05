package com.worwafi.auctions;

import com.worwafi.others.*;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.User;

import java.text.DecimalFormat;
import java.util.Random;

public abstract class Auction extends Starter implements HelpMethods, RaisingStrategy {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    protected AuctionStatusListener auctionStatusListener;
    protected String id;
    protected AuctionedObject win;
    protected GenericList<User> bidders;
    protected double actualPrice;
    protected User actualWinner;
    protected double currentRaise;
    protected boolean end;

    public Auction(String id, AuctionedObject win) {
        this.id = id;
        this.win = win;
        bidders = new GenericList<>();
        SingUserInfo.getInstance().getUsersAvailable().notifyAllObservers(this);
        actualPrice = win.getStartingPrice();
        actualWinner = win.getOwner();
        currentRaise = 0.10;
        end = false;
    }

    protected boolean callBidders() {
        for (int i = 1; i < SingAuction.getInstance().getAuction().getBidders().getList().size(); i++) {
            Random rand = new Random();
            int check = rand.nextInt(100);
            double price = SingAuction.getInstance().getAuction().getActualPrice() * (1 + Double.parseDouble(df.format(rand.nextDouble())));
            if (check < 10 && SingAuction.getInstance().getAuction().getBidders().getList().get(i).getCashAccount().getCredit() > price) {
                SingAuction.getInstance().getAuction().bid(SingAuction.getInstance().getAuction().getBidders().getList().get(i), price);
                auctionStatusListener.updateEnglishLayout();
                return true;
            }
        }
        return false;
    }
    public void addBidder(User user) {
        bidders.getList().add(user);
    }

    public abstract int handleCycle(int cycle);
//    private String getLocalId() {
//        File auctionFile = new File("D:\\skola\\txt\\auctions.txt");
//        String[] help;
//        String lastID;
//        char letter = 0;
//        char number = 0;
//        try {
//            Scanner myReader = new Scanner(auctionFile);
//            boolean start = true;
//            while(myReader.hasNextLine()) {
//                String line = myReader.nextLine();
//                if(start) {
//                    help = line.split("-");
//                    lastID = help[0];
//                    letter = lastID.charAt(0);
//                    number = lastID.charAt(1);
//                    start = false;
//                }
//                if (line.contains("!")) {
//                    start = true;
//                }
//            }
//            if(number == '0') {
//                number = '1';
//            }
//            if(number == '9') {
//                number = '0';
//                letter++;
//            }
//            number++;
//            return Character.toString(letter) + Character.toString(number);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public String bid(User bidder, double price) {
        actualWinner = bidder;
        return null;
    }

    public abstract void callAuction();

    public void setEnd() {
        win.setStatus(ObjectStatus.SOLD);
        end = true;
    }

    public GenericList<User> getBidders() {
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

    @Override
    public String getAllData() {
        return id + " " + win.getAllData() + "\n" + bidders.getAllData() + "\n" + actualWinner + " " + actualPrice;
    }
    @Override
    public String getName() {
        return id + " " + win.getName();
    }
    @Override
    public String toString() {
        return win.getName() + " by " + actualWinner.getUsername();
    }

    public AuctionedObject getWin() {
        return win;
    }
}
