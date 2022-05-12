package com.worwafi.others;

import com.jfoenix.controls.JFXButton;
import com.worwafi.controllers.CustomPopupController;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

//TODO manualne vytvorene okno
public class CustomPopup {
    private JFXButton button;
    private javafx.scene.control.Label label;
    private AnchorPane pane;
    private String text;
    private String buttonText;
    private CustomPopupController controller;

    public CustomPopup(String text, String buttonText) {
        this.text = text;
        this.buttonText = buttonText;
        setupView();
        controller = new CustomPopupController(button);
    }

    public Parent asParent() {
        return pane;
    }

    /**
     * Sets up view of a custom view
     */
    public void setupView() {
        pane = new AnchorPane();
        pane.setPrefWidth(300);
        pane.setPrefHeight(100);
        pane.getStylesheets().add(this.getClass().getResource("/fxml/basic.css").toExternalForm());
        pane.getStyleClass().add("regularGridPane");
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.setStyle("-fx-background-color: linear-gradient(#AF40FF, #5B42F3 100%,#00DDEB);");

        label = new Label(text);
        label.getStyleClass().add("mainTextArea");
        label.setPadding(new Insets(10,0,10,20));
        pane.getChildren().add(label);

        button = new JFXButton();
        button.getStyleClass().add("regularButton");
        button.setText(buttonText);
        button.setLayoutX(135);
        button.setLayoutY(50);
        pane.getChildren().add(button);
    }
}
