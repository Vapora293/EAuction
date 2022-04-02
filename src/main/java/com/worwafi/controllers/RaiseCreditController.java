package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class RaiseCreditController implements Initializable {
    boolean check = false;
    double price;

    @FXML
    private JFXTextField firstTF;

    @FXML
    private JFXButton localButton;

    @FXML
    private JFXTextField secondTF;

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
        localButton.setOnAction(event -> {
            price = Double.parseDouble(firstTF.getText());
            if (!check) {
                localButton.setText("Zaplatiť sumu " + price);
                check = true;
            } else {

            }
        });
    }
}
