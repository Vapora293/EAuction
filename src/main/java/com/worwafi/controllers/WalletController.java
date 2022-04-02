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
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class WalletController extends PatternController implements Initializable {
    @FXML
    private JFXTextArea walletConsole;
    @FXML
    private Button addAucObject;
    @FXML
    private JFXButton backButton;
    @FXML
    private Button getAucObject;
    @FXML
    private JFXButton logOutBtn;
    @FXML
    private JFXTextArea timeTextArea;
    @FXML
    private JFXTextArea welcomeTextArea;
    @FXML
    private JFXTextArea actualBalance;

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
                    check += Double.parseDouble(line);;
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
    private void setupBackButton() {
        backButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/intro_screen.fxml"));
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        setupBackButton();
    }
}
