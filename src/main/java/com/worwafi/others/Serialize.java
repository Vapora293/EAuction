package com.worwafi.others;

import com.worwafi.auctions.Auction;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BasicUser;
import com.worwafi.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

//TODO prerobit serializaciu
public class Serialize {
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    public final void writeObject(Object o) throws IOException, ClassNotFoundException {
        if (o.getClass() == LinkedList.class) {
            try {
                File userTxt = new File("D:\\skola\\txt\\uusers.txt");
                FileWriter fw = new FileWriter(userTxt);
                BasicUser actual;
                for(int i = 0; i < ((LinkedList<?>) o).size(); i++) {
                    actual = (BasicUser) ((LinkedList<?>) o).get(i);
                    if(i != 0) {
                        fw.append("\n");
                    }
                    fw.append(actual.getUsername() + " . " + actual.getPassword() + " . " + actual.getBio() + " . " + "no");
                }
                fw.close();
            } catch (IOException e) {
                        e.printStackTrace();
                    }
//            fileOutputStream = new FileOutputStream(new File("D:\\skola\\txt\\users.txt"));
//            objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            writeInto(o);
        }
        else {
            System.out.println("yes");
            try {
                File userTxt = new File("D:\\skola\\txt\\" + SingUserInfo.getInstance().getLoggedUser().getUsername() + "Objects.txt");
                FileWriter fw = new FileWriter(userTxt);
                boolean notfirst = false;
                for(AuctionedObject actual :((ObservableList<AuctionedObject>) o)) {
                    if(notfirst) {
                        fw.append("\n");
                    }
                    fw.append(actual.getName() + " . " + actual.getBio() + " . " + actual.getStartingPrice() + " . " +
                            actual.getExpSelPrice() + " . " + actual.getPicture().getPath() + " . " +
                            actual.getCategory().toString() + " . " + actual.getStatus().toString());
                    notfirst = true;
                }
                fw.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
//            fileOutputStream = new FileOutputStream(new File(String.valueOf(SingUserInfo.getInstance().getLoggedUser().getObjectFile())));
//            objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            writeInto(o);
        }
        if (o.getClass() == String.class) {

        }
        if (o.getClass() == Auction.class) {

        }
    }

    private void writeInto(Object o) throws IOException {
        objectOutputStream.writeObject(o);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public final Object readObject(String thing) throws IOException, ClassNotFoundException {
        switch (Deseralization.valueOf(thing.toUpperCase(Locale.ROOT))) {
            case USERS:
                File userTxt = new File("D:\\skola\\txt\\uusers.txt");
                Scanner myReader = new Scanner(userTxt);
                LinkedList<BasicUser> users = new LinkedList<>();
                while (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    String lineSplit[] = line.split(" . ");
                    BasicUser actual = new BasicUser(lineSplit[0], lineSplit[1], lineSplit[2]);
                    users.add(actual);
                }
                return users;
//                fileInputStream = new FileInputStream("D:\\skola\\txt\\users.txt");
//                objectInputStream = new ObjectInputStream(fileInputStream);
//                LinkedList<BasicUser> linkedList = (LinkedList<BasicUser>) objectInputStream.readObject();
//                return linkedList;
            case WAREHOUSE:
                try {
                    File auctionedObjectFile = new File(String.valueOf(SingUserInfo.getInstance().getLoggedUser().getObjectFile()));
                    Scanner myReaderware = new Scanner(auctionedObjectFile);
                    ObservableList<AuctionedObject> possesion = FXCollections.observableArrayList();
                    while (myReaderware.hasNextLine()) {
                        String line = myReaderware.nextLine();
                        if(line.equals(""))
                            continue;
                        String lineSplit[] = line.split(" . ");
                        AuctionedObject actual = new AuctionedObject(SingUserInfo.getInstance().getLoggedUser(), lineSplit[0],
                                lineSplit[1], Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3]), lineSplit[4],
                                lineSplit[5], lineSplit[6]);
                        possesion.add(actual);
                    }
                    SingUserInfo.getInstance().getLoggedUser().setPossession(possesion);
                    return possesion;
                } catch (FileNotFoundException e) {
                    return FXCollections.observableArrayList();
                }
//                fileInputStream = new FileInputStream(new File(String.valueOf(SingUserInfo.getInstance().getLoggedUser().getObjectFile())));
//                objectInputStream = new ObjectInputStream(fileInputStream);
//                ObservableList<AuctionedObject> observableList = (ObservableList<AuctionedObject>) objectInputStream.readObject();
//                return observableList;
        }
        return null;
    }
}
