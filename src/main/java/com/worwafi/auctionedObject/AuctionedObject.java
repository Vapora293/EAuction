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

    /**
     *
     * @param owner
     * @param name
     * @param bio description of the auctioned object
     * @param startingPrice
     * @param expSelPrice
     * @param path to the picture
     * @param objectCategory either Jewellery, Antique, Sculpture or Painting
     * @param status either sold, stored or for sale
     */
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

    /**
     *
     * @return default method for listviews
     */
    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @return all data of the object
     */
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

    /**
     * equivalent of .equals, but just for the parameters of the object, they do not have to be the same instance
     * @param second object to compare
     * @return boolean if they contain the same data
     */
    @Override
    public boolean compare(Starter second) {
        if(!this.owner.getUsername().equals(((AuctionedObject)second).owner.getUsername()) || !this.name.equals(((AuctionedObject)second).name) ||
                !this.bio.equals(((AuctionedObject)second).bio) || (this.startingPrice != ((AuctionedObject)second).startingPrice) || (this.expSelPrice != ((AuctionedObject)second).expSelPrice) ||
                !this.picture.toString().equals(((AuctionedObject)second).picture.toString()) || !(this.category == ((AuctionedObject)second).category))
            return false;
        return true;
    }
    //TODO implementacia Memento design pattern

    /**
     * Implementation of Memento design pattern
     * @return new Memento
     */
    public Memento saveToMemento() {
        return new Memento(this);
    }

    /**
     * restores data from the Memento
     * @param memento Memento to restore from
     */
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

        /**
         * Memento saves the object at some point in history
         * @param localVersion
         */
        public Memento(AuctionedObject localVersion) {
            this.localVersion = localVersion;
        }

        public AuctionedObject getSavedAuctionedObject() {
            return localVersion;
        }
    }
}
