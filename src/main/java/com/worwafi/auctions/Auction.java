package com.worwafi.auctions;

import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.auctionedObject.ObjectStatus;
import com.worwafi.others.*;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BasicUser;
import com.worwafi.users.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public abstract class Auction extends Starter implements HelpMethods {
    Serialize serialize = new Serialize();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    protected AuctionStatusListener auctionStatusListener;
    protected String id;
    protected AuctionedObject win;
    protected GenericList<User> bidders;
    protected double actualPrice;
    protected User actualWinner;
    protected BasicUser formerOwner;
    protected double currentRaise;
    protected boolean end;

    Auction(String id, AuctionedObject win) {
        this.id = id;
        this.win = win;
        bidders = new GenericList<>();
        SingUserInfo.getInstance().getUsersAvailable().notifyAllObservers(this);
        actualPrice = win.getStartingPrice();
        actualWinner = win.getOwner();
        formerOwner = (BasicUser) win.getOwner();
        currentRaise = 0.10;
        end = false;
    }

    protected boolean callBidders() {
        for (int i = 0; i < SingAuction.getInstance().getAuction().getBidders().getList().size(); i++) {
            //TODO RTTI
            if (!(SingAuction.getInstance().getAuction().getBidders().getList().get(i) instanceof BasicUser)) {
                Random rand = new Random();
                int check = rand.nextInt(100);
                double price = SingAuction.getInstance().getAuction().getActualPrice() * (1 + Double.parseDouble(df.format(rand.nextDouble())));
                if (check < 10 && SingAuction.getInstance().getAuction().getBidders().getList().get(i).getCashAccount().getCredit() > price) {
                    SingAuction.getInstance().getAuction().bid(SingAuction.getInstance().getAuction().getBidders().getList().get(i), price);
                    auctionStatusListener.updateEnglishLayout();
                    return true;
                }
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
        actualPrice = price;
        return null;
    }

    public abstract void callAuction();

    public boolean compare(Auction auction) {
        if(!auction.win.compare(this.win) || !auction.formerOwner.compare(formerOwner))
            return false;
        return true;
    }

    public void setEnd() {
        win.setStatus(ObjectStatus.SOLD);
        if (actualWinner instanceof BasicUser) {
            try {
                ((BasicUser) actualWinner).setPossession(serialize.readObject("warehouse", actualWinner.getUsername()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ((BasicUser) actualWinner).getPossession().getList().add(win);
            try {
                serialize.writeObject(((BasicUser) actualWinner).getPossession(), actualWinner.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ((BasicUser) actualWinner).writeIntoCashAccount(Double.parseDouble("-" + actualPrice));
        }
            try {
                GenericList<Auction> auctions = (GenericList<Auction>) serialize.readObject("auctions");
                for(Auction actual : auctions.getList()) {
                    if(actual.compare(this)) {
                        auctions.getList().remove(actual);
                        break;
                    }
                }
                serialize.writeObject(auctions);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        try {
            GenericList<AuctionedObject> formerOwnerObjects = serialize.readObject("warehouse", formerOwner.getUsername());
            for(AuctionedObject actual : formerOwnerObjects.getList()) {
                if (actual.compare(win)) {
                    formerOwnerObjects.getList().remove(actual);
                    break;
                }
            }
            serialize.writeObject(formerOwnerObjects, formerOwner.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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
