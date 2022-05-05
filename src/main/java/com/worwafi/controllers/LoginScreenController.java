package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.others.GenericList;
import com.worwafi.others.Serialize;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BasicUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginScreenController implements Initializable {
    Serialize serialize = new Serialize();

    @FXML
    private JFXButton buttonLogin;
    @FXML
    private JFXButton buttonRegister;
    @FXML
    private JFXTextField fldBio;
    @FXML
    private JFXPasswordField fldPassword;
    @FXML
    private JFXPasswordField fldPassword1;
    @FXML
    private JFXTextField fldUsername;
    @FXML
    private GridPane loginGridPane;
    @FXML
    private JFXTextArea movesDialog;
    @FXML
    private JFXTextArea welcomeDialog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GenericList<BasicUser> users = null;
        try {
            users = (GenericList<BasicUser>) serialize.readObject("users");
            serialize.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        buttonConfig(users);
    }

    private void buttonConfig(GenericList<BasicUser> users) {
        AtomicBoolean registrationClicked = new AtomicBoolean(false);
        buttonLogin.setOnAction(event -> {
            String usernameInput = fldUsername.getText();
            String passwordInput = fldPassword.getText();
            try {
                boolean works = compare(usernameInput, passwordInput, users);
                if(!works)
                    movesDialog.setText("Unknown credentials, try again");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttonRegister.setOnAction(event -> {
            if (!registrationClicked.get()) {
                fldBio.setVisible(true);
                fldPassword1.setVisible(true);
                registrationClicked.set(true);
            }
            else {
                String usernameInput = fldUsername.getText();
                String passwordInput = fldPassword.getText();
                String bioInput = fldBio.getText();
                BasicUser newBasicUser;
                if (passwordInput.equals(fldPassword1.getText())) {
                    newBasicUser = new BasicUser(usernameInput, passwordInput, bioInput);
                    users.getList().add(newBasicUser);
                    try {
                        serialize.writeObject(users);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    movesDialog.setText("User " + newBasicUser.getUsername() + " has been successfully registered");
                    fldPassword1.setVisible(false);
                    fldBio.setVisible(false);
                    fldUsername.setText(null);
                    fldUsername.setPromptText("Username");
                    fldPassword.setText(null);
                    fldPassword.setPromptText("Password");
                    registrationClicked.set(false);
                }
                else {
                    movesDialog.setText("Password doesn't match, try again");
                }
            }
        });
    }

    private boolean compare(String usernameInput, String passwordInput, GenericList<BasicUser> users) throws IOException {
        for (int i = 0; i < users.getList().size(); i++) {
            if (users.getList().get(i).getUsername().equals(usernameInput) && users.getList().get(i).getPassword().equals(passwordInput)) {
                SingUserInfo.getInstance().setLoggedUser(users.getList().get(i));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/intro_screen.fxml"));
                SingStage.getInstance().setScene(new Scene(loader.load()));
                SingAuction.getInstance();
                return true;
            }
        }
        return false;
    }
}
