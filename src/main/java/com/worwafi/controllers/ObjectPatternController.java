package com.worwafi.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingUserInfo;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ObjectPatternController extends PatternController {
    @FXML
    JFXTextArea bioTextArea;
    @FXML
    JFXTextArea categoryTextArea;
    @FXML
    JFXTextArea expctPriceTextArea;
    @FXML
    JFXTextArea nameTextArea;
    @FXML
    JFXTextArea ownerTxtArea;
    @FXML
    JFXTextArea startingPriceTextArea;
    @FXML
    JFXTextArea statusTextArea;
    @FXML
    JFXTextArea filePathTextArea;
    @FXML
    ImageView imageViewer;

    protected void setupObject() {
        ownerTxtArea.setText(SingUserInfo.getInstance().getLoggedUser().getUsername());
        nameTextArea.setText(SingActualObject.getInstance().getObject().getName());
        startingPriceTextArea.setText(String.valueOf(SingActualObject.getInstance().getObject().getStartingPrice()) + " €");
        expctPriceTextArea.setText(String.valueOf(SingActualObject.getInstance().getObject().getExpSelPrice()) + " €");
        bioTextArea.setText(SingActualObject.getInstance().getObject().getBio());
        categoryTextArea.setText(SingActualObject.getInstance().getObject().getCategory().toString());
        statusTextArea.setText(SingActualObject.getInstance().getObject().getStatus().toString());
        try {
            imageViewer.setImage(new Image(new FileInputStream(SingActualObject.getInstance().getObject().getPicture())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected void setEditable(boolean actual) {
        ownerTxtArea.setEditable(actual);
        nameTextArea.setEditable(actual);
        startingPriceTextArea.setEditable(actual);
        expctPriceTextArea.setEditable(actual);
        bioTextArea.setEditable(actual);
        categoryTextArea.setEditable(actual);
        statusTextArea.setEditable(actual);
    }
}
