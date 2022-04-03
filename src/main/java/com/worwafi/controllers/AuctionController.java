package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.worwafi.auctions.Auction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionController extends PatternController implements Initializable {
    @FXML
    private JFXTextArea actualBalance;

    @FXML
    private JFXButton backButton;

    @FXML
    private VBox biddersBox;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private JFXTextArea timeTextArea;

    @FXML
    private JFXTextArea walletConsole;

    @FXML
    private JFXTextArea welcomeTextArea;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAreas();
        setupNavBarButtons();
        setupBidders();
    }

    private void setupBidders() {

    }

}
