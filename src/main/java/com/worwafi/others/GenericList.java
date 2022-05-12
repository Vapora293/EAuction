package com.worwafi.others;

import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.auctions.Auction;
import com.worwafi.users.BotUser;
import com.worwafi.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//TODO genericka trieda
public class GenericList<T extends Starter> {
    private ObservableList<T> list;

    /**
     * creates new observable list in the generic method
     */
    public GenericList() {
        list = FXCollections.observableArrayList();
    }

    public ObservableList<T> getList() {
        return list;
    }

    public String[] allINames() {
        String[] names = new String[list.size()];
        int i = 0;
        for (Starter o : list) {
            names[i] = o.getName();
            i++;
        }
        return names;
    }

    public String[] getAllData() {
        String[] data = new String[list.size()];
        int i = 0;
        for (Starter o : list) {
            data[i] = o.getAllData();
            i++;
        }
        return data;
    }

    /**
     * function for finding objects with same parameters in the observable List
     * @param local object to find
     * @return object found; null
     */
    public Starter find(Starter local) {
        for (Starter actual : list) {
            if (actual.compare(local))
                return actual;
        }
        return null;
    }

    //TODO Observer design pattern

    /**
     * Method for notifying bots available to join the auction
     * @param auction auction to join to
     */
    public void notifyAllObservers(Auction auction) {
        for (Starter o : list) {
            if(o != null)
                ((BotUser) o).join(auction);
        }
    }
}
