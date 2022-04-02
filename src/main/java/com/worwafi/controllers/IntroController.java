package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.worwafi.others.AuctionedObject;
import com.worwafi.others.Wallet;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BasicUser;
import com.worwafi.users.UserTxt;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class IntroController extends PatternController implements Initializable {

    @FXML
    private GridPane GridPaneIntro;
    @FXML
    private JFXButton logOutBtn;
    @FXML
    private JFXTextArea timeTextArea;
    @FXML
    private JFXTextArea welcomeTextArea;
    @FXML
    private VBox list;
    @FXML
    private JFXButton warehouseButton;
    @FXML
    private JFXButton auctionButton;
    @FXML
    private JFXButton walletButton;

    private void setupWallet() {
        try {
            File userTxt = new File("D:\\skola\\txt\\" + SingUserInfo.getInstance().getLoggedUser().getUsername() + "Wallet.txt");
            Scanner myReader = new Scanner(userTxt);
            Wallet actual = new Wallet();
            int check = 0;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (isPlus(line))
                    actual.raiseCredit(Math.abs(Double.parseDouble(line)));
                if (isMinus(line))
                    actual.lowerCredit(Math.abs(Double.parseDouble(line)));
            }
            SingUserInfo.getInstance().getLoggedUser().setCashAccount(actual);
        } catch (FileNotFoundException e) {

        }
    }

    boolean isPlus(String line) {
        if (line.contains("+"))
            return true;
        return false;
    }

    boolean isMinus(String line) {
        if (line.contains("-"))
            return true;
        return false;
    }
//    private void setupAreas() {
//        welcomeTextArea.setText("Welcome " + SingUserInfo.getInstance().getLoggedUser().getUsername() + "!");
//        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
//            LocalTime currentTime = LocalTime.now();
//            timeTextArea.setText(currentTime.getHour() + ":" + currentTime.getMinute());
//        }),
//                new KeyFrame(Duration.minutes(1))
//        );
//        clock.setCycleCount(Animation.INDEFINITE);
//        clock.play();
//    }

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
        setupWallet();
        warehouseButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/warehouseScreen.fxml"));
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        auctionButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_screen.fxml"));
            try {
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        walletButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/wallet_screen.fxml"));
            try {
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
