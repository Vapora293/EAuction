package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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

public class PatternController {
    @FXML
    private JFXTextArea welcomeTextArea;
    @FXML
    private JFXTextArea timeTextArea;
    @FXML
    private JFXButton logOutBtn;

    protected void setupAreas() {
        welcomeTextArea.setText(SingUserInfo.getInstance().getLoggedUser().getUsername());
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
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
                SingUserInfo.getInstance().setLoggedUser(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
