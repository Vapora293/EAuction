package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomPopupController {
    JFXButton button;

    public CustomPopupController(JFXButton button) {
        this.button = button;
        initialize();
    }

    /**
     * Custom popup made without fxml prerequisite. After setting up the view, it contains one button used for closing
     * the window with close request
     */
    private void initialize() {
        button.setOnAction(event -> {
            Stage stage = (Stage) button.getScene().getWindow();
            stage.getOnCloseRequest().handle(new WindowEvent(button.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        });
    }
}
