package com.worwafi.others;

import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.auctions.Auction;
import com.worwafi.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//TODO genericka trieda
public class GenericList<T extends Starter> {
    private ObservableList<T> list;

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

    public Starter find(Starter local) {
        for (Starter actual : list) {
            if (((AuctionedObject) actual).compare((AuctionedObject) local))
                return actual;
        }
        return null;
    }

    //TODO Observer design pattern
    public void notifyAllObservers(Auction auction) {
        for (Starter o : list) {
            if(o != null)
                ((User) o).join(auction);
        }
    }
}
