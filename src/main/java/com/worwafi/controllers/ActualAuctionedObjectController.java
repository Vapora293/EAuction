package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.others.*;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.BasicUser;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

//TODO dorobit nech berie z comboBoxu namiesto pola

public class ActualAuctionedObjectController extends ObjectPatternController implements Initializable {
    Serialize serialize = new Serialize();
    final FileChooser fileChooser = new FileChooser();

    @FXML
    private JFXTextField bioTextArea;
    @FXML
    private JFXTextField categoryTextArea;
    @FXML
    private JFXTextField expctPriceTextArea;
    @FXML
    private JFXTextField nameTextArea;
    @FXML
    private JFXTextField ownerTxtArea;
    @FXML
    private JFXTextField startingPriceTextArea;
    @FXML
    private JFXTextField statusTextArea;
    @FXML
    private JFXButton addButton;
    @FXML
    private GridPane GridPaneAuctionedObject;
    @FXML
    private JFXTextField filePathTextArea;
    @FXML
    private ImageView imageViewer;
    @FXML
    private ComboBox<ObjectCategory> categoryComboBox;
    @FXML
    private JFXButton searchForFile;

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
        searchForFile.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(new Stage());
            if(file != null) {
                try {
                    imageViewer.setImage(new Image(new FileInputStream(file)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            filePathTextArea.setText(file.getAbsolutePath().replace("\\","\\" + "\\" ));
        });
        addButton.setOnAction(event -> {
            if(SingActualObject.getInstance().getNeww()) {
                try {
                    GenericList<AuctionedObject> auctionedObjects = (GenericList<AuctionedObject>) serialize.readObject("warehouse");
                    auctionedObjects.getList().add(new AuctionedObject(SingUserInfo.getInstance().getLoggedUser(), nameTextArea.getText(),
                            bioTextArea.getText(), Double.parseDouble(startingPriceTextArea.getText()), Double.parseDouble(expctPriceTextArea.getText()), filePathTextArea.getText(),
                            categoryComboBox.getValue().toString(),
                            statusTextArea.getText().toString()));
                    serialize.writeObject(auctionedObjects);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    throw new IncorrectEntryException("Incorrect price - " + startingPriceTextArea.getText(), filePathTextArea.textProperty());
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
        addButton.setText("Add object to the warehouse");
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
