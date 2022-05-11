package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Random;

public class PatternController {
    @FXML
    private JFXTextField welcomeTextArea;
    @FXML
    private JFXTextField timeTextArea;
    @FXML
    private JFXButton logOutBtn;
    @FXML
    private JFXButton backButton;

    protected void setupAreas() {
        welcomeTextArea.setText("Logged as: " + SingUserInfo.getInstance().getLoggedUser().getUsername());
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            if(currentTime.getMinute() < 10)
                timeTextArea.setText(currentTime.getHour() + ":0" + currentTime.getMinute());
            else
                timeTextArea.setText(currentTime.getHour() + ":" + currentTime.getMinute());
        }),
                new KeyFrame(Duration.seconds(10))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    protected void setupNavBarButtons() {
        logOutBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_screen.fxml"));
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
                SingUserInfo.getInstance().getUsersAvailable().getList().remove(SingUserInfo.getInstance().getLoggedUser());
                SingUserInfo.getInstance().setLoggedUser(null);
                SingAuction.getInstance().clear();
                SingActualObject.getInstance().clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
}
