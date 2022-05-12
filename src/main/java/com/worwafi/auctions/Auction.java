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

    /**
     * Generates new auction
     *
     * @param id  id to be written into txt
     * @param win object to be auctioned
     */
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

    /**
     * This calls all the bidders in the auction. Bots bid with 10% probability and random bet
     *
     * @return returns true or false, if the bots bidded or not
     */
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

    /**
     * Adds bidder to the auction, used for Observer pattern
     *
     * @param user user to be added
     */
    public void addBidder(User user) {
        bidders.getList().add(user);
    }

    public abstract int handleCycle(int cycle);

    /**
     * Process of bidding in the auction
     *
     * @param bidder bidder to be winner at that time
     * @param price  price to be betted
     * @return used for Auction exception
     */
    public String bid(User bidder, double price) {
        actualWinner = bidder;
        actualPrice = price;
        return null;
    }

    public abstract void callAuction();

    /**
     * Compares values of two auctions
     *
     * @param auction auctioned to be compared with
     * @return boolean if they have the same data
     */
    @Override
    public boolean compare(Starter auction) {
        if (!((Auction) auction).win.compare(this.win) || !((Auction) auction).formerOwner.compare(formerOwner))
            return false;
        return true;
    }

    /**
     * When the auction ends, the objects gets to the warehouse of the winner and gets
     * removed from the warehouse of the former owner. Winner pays for it. Auction
     * gets removed from the serialized list of auctions
     */
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
            //TODO pouzivanie generickych metod
            Auction toDelete = (Auction) auctions.find(this);
            auctions.getList().remove(toDelete);
            serialize.writeObject(auctions);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            GenericList<AuctionedObject> formerOwnerObjects = serialize.readObject("warehouse", formerOwner.getUsername());
            AuctionedObject toDelete = (AuctionedObject) formerOwnerObjects.find(win);
            formerOwnerObjects.getList().remove(toDelete);
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

    public AuctionedObject getWin() {
        return win;
    }

    /**
     * @return default method for listviews
     */
    @Override
    public String toString() {
        return win.getName() + " by " + actualWinner.getUsername();
    }

}
