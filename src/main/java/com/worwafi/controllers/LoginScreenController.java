package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginScreenController implements Initializable {

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
        LinkedList<BasicUser> users = null;
        try {
            users = txtFileConfig();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        buttonConfig(users);
    }

    private LinkedList<BasicUser> txtFileConfig() throws IOException, ClassNotFoundException {
        File userTxt = new File("D:\\skola\\txt\\users.txt");
        if(userTxt.exists()) {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(userTxt));
            LinkedList<BasicUser> userList = (LinkedList<BasicUser>) inputStream.readObject();
            inputStream.close();
            return userList;
            }
        return new LinkedList<>();
//            public void serialization(LinkedList<User> userList) throws IOException {
//                FileOutputStream fileOut = new FileOutputStream("registeredUsers.out");
//                ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
//
//                outputStream.writeObject(userList);
//                outputStream.close();
//            }
//        }
//        try {
//
//            Scanner myReader = new Scanner(userTxt);
//            LinkedList<BasicUser> users = new LinkedList<>();
//            while (myReader.hasNextLine()) {
//                String line = myReader.nextLine();
//                String lineSplit[] = line.split(" . ");
//                BasicUser actual = new BasicUser(lineSplit[0], lineSplit[1], lineSplit[2]);
//                users.add(actual);
//            }
//            return users;
//        } catch (FileNotFoundException e) {
//            return null;
//        }
    }

    private void buttonConfig(LinkedList<BasicUser> users) {
        AtomicBoolean registrationClicked = new AtomicBoolean(false);
        buttonLogin.setOnAction(event -> {
            String usernameInput = fldUsername.getText();
            String passwordInput = fldPassword.getText();
            try {
                boolean works = compare(usernameInput, passwordInput, users);
                if(!works)
                    movesDialog.setText("Nespravne udaje, skuste znova");
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
                    try {
                        File userTxt = new File("D:\\skola\\txt\\users.txt");
                        FileWriter fw = new FileWriter(userTxt, true);
                        fw.append("\n" + newBasicUser.getUsername() + " . " + newBasicUser.getPassword() + " . " + newBasicUser.getBio() + " . " + "no");
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    movesDialog.setText("Pouzivatel " + newBasicUser.getUsername() + " bol uspesne zaregistrovany");
                    users.add(newBasicUser);
                    fldPassword1.setVisible(false);
                    fldBio.setVisible(false);
                    fldUsername.setText(null);
                    fldUsername.setPromptText("Prihlasovacie meno");
                    fldPassword.setText(null);
                    fldPassword.setPromptText("Heslo");
                    registrationClicked.set(false);
                }
                else {
                    movesDialog.setText("Hesla nie su zhodne, zadajte znova");
                }
            }
        });
    }

    private boolean compare(String usernameInput, String passwordInput, LinkedList<BasicUser> users) throws IOException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(usernameInput) && users.get(i).getPassword().equals(passwordInput)) {
                SingUserInfo.getInstance().setLoggedUser(users.get(i));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/intro_screen.fxml"));
                SingStage.getInstance().setScene(new Scene(loader.load()));
                return true;
            }
        }
        return false;
    }
}
