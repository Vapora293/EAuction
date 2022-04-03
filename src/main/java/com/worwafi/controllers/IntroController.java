package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.worwafi.others.Wallet;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
    @FXML
    private JFXButton backButton;

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
        backButton.setDisable(true);
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
