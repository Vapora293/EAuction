package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.worwafi.auctions.EnglishAuction;
import com.worwafi.others.AuctionedObject;
import com.worwafi.others.ObjectCategory;
import com.worwafi.others.ObjectStatus;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController extends ObjectPatternController implements Initializable {
    @FXML
    private JFXTextArea actualBalance;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXTextArea bioTextArea;

    @FXML
    private JFXTextArea categoryTextArea;

    @FXML
    private JFXTextArea expctPriceTextArea;

    @FXML
    private JFXTextArea filePathTextArea;

    @FXML
    private Button getAucObject;

    @FXML
    private ImageView imageViewer;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private JFXTextArea nameTextArea;

    @FXML
    private JFXTextArea ownerTxtArea;

    @FXML
    private JFXTextArea startingPriceTextArea;

    @FXML
    private JFXTextArea statusTextArea;

    @FXML
    private JFXTextArea timeTextArea;

    @FXML
    private JFXTextArea welcomeTextArea;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAreas();
        setupNavBarButtons();
        setupObject();
        setEditable(true);
        getAucObject.setOnAction(event -> {
            if (SingActualObject.getInstance() != null && SingActualObject.getInstance().getObject().getStatus() == ObjectStatus.FORSALE) {
                checkCorrect();
                EnglishAuction auction = new EnglishAuction(SingActualObject.getInstance().getObject());
                SingAuction.getInstance().setAuction(auction);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auction_screen.fxml"));
                try {
                    Scene actual = new Scene(loader.load());
                    SingStage.getInstance().setScene(actual);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkCorrect() {
        //for (Object actual: SingActualObject.getInstance().getObject().getClass().getDeclaredFields()) {
        //}
        if (!SingActualObject.getInstance().getObject().getName().equals(nameTextArea.getText()) ||
                !SingActualObject.getInstance().getObject().getBio().equals(bioTextArea.getText()) ||
                SingActualObject.getInstance().getObject().getStartingPrice() != Double.parseDouble(startingPriceTextArea.getText().replaceAll("\\D+", "")) ||
                SingActualObject.getInstance().getObject().getExpSelPrice() != Double.parseDouble(expctPriceTextArea.getText().replaceAll("\\D+", "")) ||
                SingActualObject.getInstance().getObject().getCategory() != ObjectCategory.valueOf(categoryTextArea.getText().toUpperCase(Locale.ROOT)) ||
                SingActualObject.getInstance().getObject().getStatus() != ObjectStatus.valueOf(statusTextArea.getText().toUpperCase(Locale.ROOT))) {
            correctObject();
        }
    }

    private void correctObject() {

    }
}
