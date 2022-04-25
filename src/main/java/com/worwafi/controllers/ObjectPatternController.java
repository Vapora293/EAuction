package com.worwafi.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingUserInfo;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ObjectPatternController extends PatternController {
    @FXML
    JFXTextField bioTextArea;
    @FXML
    JFXTextField categoryTextArea;
    @FXML
    JFXTextField expctPriceTextArea;
    @FXML
    JFXTextField nameTextArea;
    @FXML
    JFXTextField ownerTxtArea;
    @FXML
    JFXTextField startingPriceTextArea;
    @FXML
    JFXTextField statusTextArea;
    @FXML
    JFXTextField filePathTextArea;
    @FXML
    ImageView imageViewer;

    protected void setupObject() {
        ownerTxtArea.setText(SingUserInfo.getInstance().getLoggedUser().getUsername());
        nameTextArea.setText(SingActualObject.getInstance().getObject().getName());
        startingPriceTextArea.setText(SingActualObject.getInstance().getObject().getStartingPrice() + " €");
        expctPriceTextArea.setText(SingActualObject.getInstance().getObject().getExpSelPrice() + " €");
        bioTextArea.setText(SingActualObject.getInstance().getObject().getBio());
        categoryTextArea.setText(SingActualObject.getInstance().getObject().getCategory().toString());
        statusTextArea.setText(SingActualObject.getInstance().getObject().getStatus().toString());
        filePathTextArea.setText(SingActualObject.getInstance().getObject().getPicture().getAbsolutePath());
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
