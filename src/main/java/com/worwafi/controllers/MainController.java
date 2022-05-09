package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.auctions.Auction;
import com.worwafi.auctions.EnglishAuction;
import com.worwafi.auctions.ReverseAuction;
import com.worwafi.others.*;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BasicUser;
import com.worwafi.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController extends ObjectPatternController implements Initializable {
    Random rand = new Random();
    Serialize serialize = new Serialize();
    @FXML
    private JFXTextArea actualBalance;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXTextField bioTextArea;

    @FXML
    private JFXTextField categoryTextArea;

    @FXML
    private JFXTextField expctPriceTextArea;

    @FXML
    private JFXTextField filePathTextArea;

    @FXML
    private JFXButton getAucObject;

    @FXML
    private ImageView imageViewer;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private JFXTextField nameTextArea;

    @FXML
    private JFXTextField ownerTxtArea;

    @FXML
    private JFXTextField startingPriceTextArea;

    @FXML
    private JFXTextField statusTextArea;

    @FXML
    private JFXTextField timeTextArea;

    @FXML
    private JFXTextField welcomeTextArea;

    @FXML
    private ComboBox<Text> auctionComboBox;
    @FXML
    private ListView<User> listView;
    @FXML
    private JFXButton saveChanges;
    @FXML
    private JFXButton revertChanges;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAreas();
        setupNavBarButtons();
        setupObject();
        if (SingAuction.getInstance().isNewAuction()) {
            setEditable(false);
            getAucObject.setText("");
        } else {
            listView.setVisible(false);
            setEditable(true);
            getAucObject.setOnAction(event -> {
                try {
                    GenericList<AuctionedObject> serialized = (GenericList<AuctionedObject>) serialize.readObject("warehouse");
                    if(serialized.find(SingActualObject.getInstance().getObject()) == null) {
                        AuctionedObject toDelete = SingActualObject.getInstance().getFirst().getSavedAuctionedObject();
                        serialized.getList().remove(serialized.find(toDelete));
                        serialized.getList().add(SingActualObject.getInstance().getObject());
                        serialize.writeObject(serialized);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (SingActualObject.getInstance().getObject().getStatus() == ObjectStatus.FORSALE) {

                    Auction auction = null;
                    if (auctionComboBox.getSelectionModel().getSelectedItem().getText().toString().equals("English Auction")) {
                        auction = new EnglishAuction(getId(), SingActualObject.getInstance().getObject());
                    }
                    if (auctionComboBox.getSelectionModel().getSelectedItem().getText().toString().equals("Reverse Auction")) {
                        auction = new ReverseAuction(getId(), SingActualObject.getInstance().getObject());
                    }
                    SingAuction.getInstance().setAuction(auction, true);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auction_screen.fxml"));
                    try {
                        Scene actual = new Scene(loader.load());
                        SingStage.getInstance().setScene(actual);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            saveChanges.setOnAction(event -> {
                SingActualObject.getInstance().addToHistory(SingActualObject.getInstance().getObject());
                SingActualObject.getInstance().setObject(new AuctionedObject(SingUserInfo.getInstance().getLoggedUser(),
                        nameTextArea.getText(), bioTextArea.getText(), Double.parseDouble(startingPriceTextArea.getText().replaceAll(" €", "")),
                        Double.parseDouble(expctPriceTextArea.getText().replaceAll(" €", "")), filePathTextArea.getText(),
                        categoryTextArea.getText(), statusTextArea.getText().replaceAll("\\W", "")), true);
                SingActualObject.getInstance().addToHistory(SingActualObject.getInstance().getObject());
                setupObject();
            });
            revertChanges.setOnAction(event -> {
                SingActualObject.getInstance().restoreFromHistory(SingActualObject.getInstance().getObject());
                setupObject();
            });
        }
    }
    @Override
    protected void setupObject() {
        super.setupObject();
        ObservableList<Text> auctionList = FXCollections.observableArrayList();
        auctionList.add(new Text("English Auction"));
        auctionList.add(new Text("Reverse Auction"));
        auctionComboBox.setItems(auctionList);

    }

    private String getId() {
        return String.valueOf((char) (rand.nextInt(25) + 65) + rand.nextInt(10));
    }
            private void checkCorrect () {
                if (!SingActualObject.getInstance().getObject().getName().equals(nameTextArea.getText()) ||
                        !SingActualObject.getInstance().getObject().getBio().equals(bioTextArea.getText()) ||
                        SingActualObject.getInstance().getObject().getStartingPrice() != Double.parseDouble(startingPriceTextArea.getText().replaceAll("\\D+", "")) ||
                        SingActualObject.getInstance().getObject().getExpSelPrice() != Double.parseDouble(expctPriceTextArea.getText().replaceAll("\\D+", "")) ||
                        SingActualObject.getInstance().getObject().getCategory() != ObjectCategory.valueOf(categoryTextArea.getText().toUpperCase(Locale.ROOT)) ||
                        SingActualObject.getInstance().getObject().getStatus() != ObjectStatus.valueOf(statusTextArea.getText().toUpperCase(Locale.ROOT))) {
                    correctObject();
                }
            }

            private void correctObject () {

            }
        }
