package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.worwafi.others.AuctionedObject;
import com.worwafi.others.ObjectCategory;
import com.worwafi.others.ObjectStatus;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

//TODO dorobit nech berie z comboBoxu namiesto pola

public class ActualAuctionedObjectController extends ObjectPatternController implements Initializable {
    private static ActualAuctionedObjectController single_instance = null;
    public ActualAuctionedObjectController() {
        single_instance = this;
    }

    public static ActualAuctionedObjectController getSingle_instance() {
        return single_instance;
    }

    @FXML
    private JFXTextArea bioTextArea;
    @FXML
    private JFXTextArea categoryTextArea;
    @FXML
    private JFXTextArea expctPriceTextArea;
    @FXML
    private JFXTextArea nameTextArea;
    @FXML
    private JFXTextArea ownerTxtArea;
    @FXML
    private JFXTextArea startingPriceTextArea;
    @FXML
    private JFXTextArea statusTextArea;
    @FXML
    private JFXButton addButton;
    @FXML
    private GridPane GridPaneAuctionedObject;
    @FXML
    private JFXTextArea filePathTextArea;
    @FXML
    private ImageView imageViewer;
    @FXML
    private ComboBox<ObjectCategory> categoryComboBox;

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
        if(!SingActualObject.getInstance().getNeww())
            setupObject();
        else
            newObjectSetup();
        buttonSetup();
    }

    private void buttonSetup() {
        addButton.setOnAction(event -> {
            if(SingActualObject.getInstance().getNeww()) {
                try {
                    File userTxt = new File("D:\\skola\\txt\\" + SingUserInfo.getInstance().getLoggedUser().getUsername() + "Objects.txt");
                    FileWriter fw = new FileWriter(userTxt, true);
                    fw.append("\n" + nameTextArea.getText() + " . " + bioTextArea.getText() + " . " + startingPriceTextArea.getText() + " . " + expctPriceTextArea.getText() + " . " + filePathTextArea.getText() + " . " + categoryComboBox.getValue().toString() + " . " + statusTextArea.getText());
                    fw.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                Stage stage = (Stage) ownerTxtArea.getScene().getWindow();
                stage.getOnCloseRequest().handle(new WindowEvent(ownerTxtArea.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();
            }
            else {
                Stage stage = (Stage) ownerTxtArea.getScene().getWindow();
                stage.getOnCloseRequest().handle(new WindowEvent(ownerTxtArea.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_screen.fxml"));
                try {
                    SingActualObject.getInstance().getObject().setStatus(ObjectStatus.FORSALE);
                    Scene actual = new Scene(loader.load());
                    SingStage.getInstance().setScene(actual);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            });

    }

    private void newObjectSetup() {
        addButton.setText("Pridaj objekt do skladu");
        filePathTextArea.setVisible(true);
        filePathTextArea.setEditable(true);
//        for(Node actual : GridPaneAuctionedObject.getChildren()) {
//            if(actual.getClass().getName().equals("JFXTextArea"))
//                (JFXTextArea) actual.setEditable
//        }
        ownerTxtArea.setText(SingUserInfo.getInstance().getLoggedUser().getUsername());
        nameTextArea.setEditable(true);
        startingPriceTextArea.setEditable(true);
        expctPriceTextArea.setEditable(true);
        bioTextArea.setEditable(true);
        categoryTextArea.setVisible(false);
        statusTextArea.setEditable(true);
        categoryComboBox.setItems( FXCollections.observableArrayList( ObjectCategory.values()));
        categoryComboBox.setPromptText(categoryComboBox.getItems().get(0).toString());
        statusTextArea.setText(ObjectStatus.STORED.toString());
        statusTextArea.setEditable(false);
    }
}
