package com.worwafi.auctionedObject;

import com.worwafi.others.Starter;
import com.worwafi.users.User;

import java.io.File;
import java.util.Locale;

public class AuctionedObject extends Starter {
    private User owner;
    private String name;
    private String bio;
    private double startingPrice;
    private double expSelPrice;
    private File picture;
    private ObjectCategory category;
    private ObjectStatus status;

    public AuctionedObject(User owner, String name, String bio, double startingPrice, double expSelPrice, String path,
                           String objectCategory, String status) {
        this.owner = owner;
        this.name = name;
        this.bio = bio;
        this.startingPrice = startingPrice;
        this.expSelPrice = expSelPrice;
        this.picture = new File(path);
        this.category = ObjectCategory.valueOf(objectCategory.toUpperCase(Locale.ROOT));
        this.status = ObjectStatus.valueOf(status.toUpperCase(Locale.ROOT));
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAllData() {
        return owner.getName() + " " + name + " " + bio + " " + startingPrice + " " + expSelPrice + " " +
                picture.toString() + " " + category.toString() + " " + status.toString();
    }

    public String getBio() {
        return bio;
    }

    public double getExpSelPrice() {
        return expSelPrice;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public File getPicture() {
        return picture;
    }

    public ObjectCategory getCategory() {
        return category;
    }

    public ObjectStatus getStatus() {
        return status;
    }

    public void setStatus(ObjectStatus status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }
    public boolean compare(AuctionedObject second) {
        if(!this.owner.getUsername().equals(second.owner.getUsername()) || !this.name.equals(second.name) ||
        !this.bio.equals(second.bio) || (this.startingPrice != second.startingPrice) || (this.expSelPrice != second.expSelPrice) ||
        !this.picture.toString().equals(second.picture.toString()) || !(this.category == second.category))
            return false;
        return true;
    }
    //TODO implementacia Memento design pattern
    public Memento saveToMemento() {
        return new Memento(this);
    }

    public void restoreFromMemento(Memento memento) {
        owner = memento.getSavedAuctionedObject().owner;
        name = memento.getSavedAuctionedObject().name;
        bio = memento.getSavedAuctionedObject().bio;
        startingPrice = memento.getSavedAuctionedObject().startingPrice;
        expSelPrice = memento.getSavedAuctionedObject().expSelPrice;
        picture = memento.getSavedAuctionedObject().picture;
        category = memento.getSavedAuctionedObject().category;
        status = memento.getSavedAuctionedObject().status;
    }

    public static class Memento {
        private final AuctionedObject localVersion;

        public Memento(AuctionedObject localVersion) {
            this.localVersion = localVersion;
        }

        public AuctionedObject getSavedAuctionedObject() {
            return localVersion;
        }
    }
}
