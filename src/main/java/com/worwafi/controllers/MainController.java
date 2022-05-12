package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.auctionedObject.ObjectCategory;
import com.worwafi.auctionedObject.ObjectStatus;
import com.worwafi.auctions.Auction;
import com.worwafi.auctions.AuctionFactory;
import com.worwafi.auctions.EnglishAuction;
import com.worwafi.others.*;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController extends ObjectPatternController implements Initializable {
    Random rand = new Random();
    Serialize serialize = new Serialize();
    AuctionFactory auctionFactory = new AuctionFactory();
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
    private ListView<User> biddersBox;
    @FXML
    private JFXButton saveChanges;
    @FXML
    private JFXButton revertChanges;
    @FXML
    private JFXButton saveAuction;
    @FXML
    private JFXTextField auctionType;

    /**
     * Sets up the pattern buttons and loads the auction information into the fields. Buttons are set here
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAreas();
        setupNavBarButtons();
        setupObject();
        if (SingAuction.getInstance().isNewAuction()) {
            setEditable(false);
            getAucObject.setVisible(true);
            biddersBox.setItems(SingAuction.getInstance().getAuction().getBidders().getList());
            biddersBox.setEditable(false);
            auctionType.setVisible(true);
            saveAuction.setVisible(false);
            saveChanges.setVisible(false);
            revertChanges.setVisible(false);
            getAucObject.setText("Start auction");
            //TODO RTTI
            if(SingAuction.getInstance().getAuction() instanceof EnglishAuction)
                auctionType.setText("English Auction");
            else
                auctionType.setText("Reverse Auction");
            getAucObject.setOnAction(event -> {
                SingActualObject.getInstance().setObject(SingAuction.getInstance().getAuction().getWin(), true);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auction_screen.fxml"));
                try {
                    Scene actual = new Scene(loader.load());
                    SingStage.getInstance().setScene(actual);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            biddersBox.setVisible(false);
            setEditable(true);
            getAucObject.setOnAction(event -> {
                try {
                    checkCorrect();
                    GenericList<AuctionedObject> serialized = (GenericList<AuctionedObject>) serialize.readObject("warehouse");
                    if (serialized.find(SingActualObject.getInstance().getObject()) == null) {
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
                        auction = auctionFactory.createAuction("English", getId(), SingActualObject.getInstance().getObject());
                    }
                    if (auctionComboBox.getSelectionModel().getSelectedItem().getText().toString().equals("Reverse Auction")) {
                        auction = auctionFactory.createAuction("Reverse", getId(), SingActualObject.getInstance().getObject());
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
                checkCorrect();
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
            auctionComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setupAuctionComboBox();
                }
            });
            saveAuction.setOnAction(event -> {
                try {
                    GenericList<Auction> auctions = (GenericList<Auction>) serialize.readObject("auctions");
                    Auction auction = null;
                    if (auctionComboBox.getSelectionModel().getSelectedItem().getText().toString().equals("English Auction")) {
                        auction = auctionFactory.createAuction("English", getId(), SingActualObject.getInstance().getObject());
                    }
                    if (auctionComboBox.getSelectionModel().getSelectedItem().getText().toString().equals("Reverse Auction")) {
                        auction = auctionFactory.createAuction("Reverse", getId(), SingActualObject.getInstance().getObject());
                    }
                    auctions.getList().add(auction);
                    serialize.writeObject(auctions);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/intro_screen.fxml"));
                    Scene actual = new Scene(loader.load());
                    SingStage.getInstance().setScene(actual);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Sets up object info and combobox info of auctions
     */
    @Override
    protected void setupObject() {
        super.setupObject();
        setupAuctionComboBox();

    }

    private void setupAuctionComboBox() {
        ObservableList<Text> auctionList = FXCollections.observableArrayList();
        auctionList.add(new Text("English Auction"));
        auctionList.add(new Text("Reverse Auction"));
        auctionComboBox.setItems(auctionList);
    }

    /**
     * random id
     * @return id
     */
    private String getId() {
        return String.valueOf((char) (rand.nextInt(25) + 65) + rand.nextInt(10));
    }

    /**
     * checks correctness of money values
     */
    private void checkCorrect() {
        try {
        if(!startingPriceTextArea.getText().replaceAll("€", "").matches("(.*)regular(.*)"))
            throw new IncorrectEntryException(startingPriceTextArea.getText(), filePathTextArea.textProperty());
        if(!expctPriceTextArea.getText().replaceAll("€", "").matches("(.*)regular(.*)"))
                throw new IncorrectEntryException(expctPriceTextArea.getText(), filePathTextArea.textProperty());
        } catch (IncorrectEntryException e) {
            e.printStackTrace();
        }
    }

    /**
     * After clicking on combobox, it loads the texts again
     * @param mouseEvent
     */
    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        setupAuctionComboBox();
    }
}
