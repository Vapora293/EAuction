package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.worwafi.others.ObjectStatus;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends PatternController implements Initializable {
    @FXML
    private JFXTextArea welcomeTextArea;
    @FXML
    private JFXTextArea timeTextArea;
    @FXML
    private JFXButton backButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAreas();
        setupNavBarButtons();
        if(SingActualObject.getInstance()!= null && SingActualObject.getInstance().getObject().getStatus() == ObjectStatus.FORSALE) {

        }
    }
}
