package com.worwafi.others;

import com.worwafi.auctions.Auction;
import com.worwafi.auctions.EnglishAuction;
import com.worwafi.auctions.ReverseAuction;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BasicUser;
import com.worwafi.users.User;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

//TODO serializacia + RTTI
public class Serialize {

    public final boolean writeObject(GenericList<? extends Starter> o) throws IOException, ClassNotFoundException {
        if (o.getList().isEmpty())
            return false;
        if (o.getList().get(0) instanceof User) {
            try {
                File userTxt = new File("D:\\skola\\txt\\uusers.txt");
                FileWriter fw = new FileWriter(userTxt);
                BasicUser actual;
                for (int i = 0; i < o.getList().size(); i++) {
                    actual = (BasicUser) o.getList().get(i);
                    if (i != 0) {
                        fw.append("\n");
                    }
                    fw.append(actual.getUsername() + " . " + actual.getPassword() + " . " + actual.getBio() + " . " + "no");
                }
                fw.close();
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        if (o.getList().get(0) instanceof AuctionedObject) {
            try {
                File userTxt = new File("D:\\skola\\txt\\" + SingUserInfo.getInstance().getLoggedUser().getUsername() + "Objects.txt");
                FileWriter fw = new FileWriter(userTxt);
                for (int i = 0; i < o.getList().size(); i++) {
                    AuctionedObject actual = (AuctionedObject) o.getList().get(i);
                    if (i != 0) {
                        fw.append("\n");
                    }
                    fw.append(actual.getName() + " . " + actual.getBio() + " . " + actual.getStartingPrice() + " . " +
                            actual.getExpSelPrice() + " . " + actual.getPicture().getPath() + " . " +
                            actual.getCategory().toString() + " . " + actual.getStatus().toString());
                }
                fw.close();
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        if (o.getList().get(0) instanceof Auction) {
            try {
                File userTxt = new File("D:\\skola\\txt\\auctions.txt");
                FileWriter fw = new FileWriter(userTxt);
                for (int i = 0; i < o.getList().size(); i++) {
                    Auction actual = (Auction) o.getList().get(i);
                    if (i != 0) {
                        fw.append("\n");
                    }
                    fw.append(actual.getAllData());
                }
                fw.close();
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final GenericList<? extends Starter> readObject(String thing) throws IOException, ClassNotFoundException {
        switch (Deseralization.valueOf(thing.toUpperCase(Locale.ROOT))) {
            case USERS:
                File userTxt = new File("D:\\skola\\txt\\uusers.txt");
                Scanner myReader = new Scanner(userTxt);
                GenericList<BasicUser> users = new GenericList<>();
                while (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    String lineSplit[] = line.split(" . ");
                    BasicUser actual = new BasicUser(lineSplit[0], lineSplit[1], lineSplit[2]);
                    users.getList().add(actual);
                }
                return users;
            case WAREHOUSE:
                try {
                    File auctionedObjectFile = new File(String.valueOf(SingUserInfo.getInstance().getLoggedUser().getObjectFile()));
                    Scanner myReaderware = new Scanner(auctionedObjectFile);
                    GenericList<AuctionedObject> possesion = new GenericList<>();
                    while (myReaderware.hasNextLine()) {
                        String line = myReaderware.nextLine();
                        if (line.equals(""))
                            continue;
                        String lineSplit[] = line.split(" . ");
                        AuctionedObject actual = new AuctionedObject(SingUserInfo.getInstance().getLoggedUser(), lineSplit[0],
                                lineSplit[1], Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3]), lineSplit[4],
                                lineSplit[5], lineSplit[6]);
                        possesion.getList().add(actual);
                    }
                    SingUserInfo.getInstance().getLoggedUser().setPossession(possesion);
                    return possesion;
                } catch (FileNotFoundException e) {
                    return new GenericList<AuctionedObject>();
                }
            case AUCTIONS:
                try {
                    File auctionsFile = new File("D:\\skola\\txt\\auctions.txt");
                    GenericList<Auction> auctions = new GenericList<>();
                    Scanner myReaderAuctions = new Scanner(auctionsFile);
                    while (myReaderAuctions.hasNextLine()) {
                        String line = myReaderAuctions.nextLine();
                        String lineSplit[] = line.split(" . ");
                        if (lineSplit[0].equals("en")) {
                            GenericList<AuctionedObject> objectsOfTheUser = readObject("warehouse", lineSplit[2]);
                            for (AuctionedObject object : objectsOfTheUser.getList()) {
                                if (object.getName().equals(lineSplit[3])) {
                                    auctions.getList().add(new EnglishAuction(lineSplit[1], object));
                                    break;
                                }
                            }
                        }
                        if (lineSplit[0].equals("rv")) {
                            GenericList<AuctionedObject> objectsOfTheUser = readObject("warehouse", lineSplit[2]);
                            for (AuctionedObject object : objectsOfTheUser.getList()) {
                                if (object.getName().equals(lineSplit[3])) {
                                    auctions.getList().add(new ReverseAuction(lineSplit[1], object));
                                    break;
                                }
                            }
                        }
                    }
                    return auctions;
                } catch (FileNotFoundException e) {
                    return new GenericList<AuctionedObject>();
                }
            default:
                return null;
        }
    }

    private GenericList<AuctionedObject> readObject(String thing, String user) {
        switch (Deseralization.valueOf(thing.toUpperCase(Locale.ROOT))) {
            case WAREHOUSE:
                try {
                    File auctionedObjectFile = new File("D:\\skola\\txt\\" + user + "Objects.txt");
                    Scanner myReaderware = new Scanner(auctionedObjectFile);
                    GenericList<AuctionedObject> possesion = new GenericList<>();
                    while (myReaderware.hasNextLine()) {
                        String line = myReaderware.nextLine();
                        if (line.equals(""))
                            continue;
                        String lineSplit[] = line.split(" . ");
                        AuctionedObject actual = new AuctionedObject(SingUserInfo.getInstance().getLoggedUser(), lineSplit[0],
                                lineSplit[1], Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3]), lineSplit[4],
                                lineSplit[5], lineSplit[6]);
                        possesion.getList().add(actual);
                    }
                    SingUserInfo.getInstance().getLoggedUser().setPossession(possesion);
                    return possesion;
                } catch (FileNotFoundException e) {
                    return new GenericList<AuctionedObject>();
                }
            default:
                return null;
        }
    }
}
