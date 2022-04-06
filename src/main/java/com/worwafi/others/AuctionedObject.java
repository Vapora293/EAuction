package com.worwafi.others;

import com.worwafi.users.User;

import java.io.File;
import java.math.BigDecimal;
import java.util.Locale;

public class AuctionedObject {
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

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
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
}
