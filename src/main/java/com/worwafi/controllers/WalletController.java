package com.worwafi.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.worwafi.others.Wallet;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class WalletController extends PatternController implements Initializable {
    Stage auctionedStage = new Stage();

    @FXML
    private JFXButton backButton;

    @FXML
    private Button getAucObject;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private Button raiseMoney;

    @FXML
    private JFXTextArea timeTextArea;

    @FXML
    private JFXTextArea walletConsole;

    @FXML
    private JFXTextArea welcomeTextArea;

    private void setupWallet() {
        try {
            File userTxt = new File("D:\\skola\\txt\\" + SingUserInfo.getInstance().getLoggedUser().getUsername() + "Wallet.txt");
            Scanner myReader = new Scanner(userTxt);
            Wallet actual = new Wallet();
            double check = 0;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if(isPlus(line)) {
                    walletConsole.appendText("Na účet bolo pripísaných " + line + "\n");
                    check += Double.parseDouble(line);
                }
                if(isMinus(line)) {
                    walletConsole.appendText("Na účet bolo odpísaných " + line + "\n");
                    check += Double.parseDouble(line);
                }
            }
            actual.setCredit(check);
            SingUserInfo.getInstance().getLoggedUser().setCashAccount(actual);
        } catch (FileNotFoundException e) {

        }
    }
    boolean isPlus(String line) {
        if(line.contains("+"))
            return true;
        return false;
    }
    boolean isMinus(String line) {
        if(line.contains("-"))
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
        raiseMoney.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/wallet_popup.fxml"));
            try {
                Scene actual = new Scene(loader.load());
                auctionedStage.setScene(actual);
                auctionedStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        auctionedStage.setOnCloseRequest(event -> {
            setupWallet();
            System.out.println(SingUserInfo.getInstance().getLoggedUser().getCashAccount().getCredit());
        });
    }

    private void updateBalance() {
    }
}
