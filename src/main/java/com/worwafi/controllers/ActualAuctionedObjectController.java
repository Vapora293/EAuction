package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.auctionedObject.ObjectCategory;
import com.worwafi.auctionedObject.ObjectStatus;
import com.worwafi.auctions.Auction;
import com.worwafi.others.*;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

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
     * completely processed. If there is new object, textfields can be modified and then object saved. If it already
     * exists, no modifications can be provided in this view
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

    /**
     * setup of all buttons. Search - setups the image. Add - if everything kosher, it will be added to the warehouse
     * of current user and the window closes
     */
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
                    if(!startingPriceTextArea.getText().replaceAll("€", "").matches("[0-9]+"))
                        throw new IncorrectEntryException(startingPriceTextArea.getText(), filePathTextArea.textProperty());
                    if(!expctPriceTextArea.getText().replaceAll("€", "").matches("[0-9]+"))
                        throw new IncorrectEntryException(expctPriceTextArea.getText(), filePathTextArea.textProperty());
                    auctionedObjects.getList().add(new AuctionedObject(SingUserInfo.getInstance().getLoggedUser(), nameTextArea.getText(),
                            bioTextArea.getText(), Double.parseDouble(startingPriceTextArea.getText()), Double.parseDouble(expctPriceTextArea.getText()), filePathTextArea.getText(),
                            categoryComboBox.getValue().toString(),
                            statusTextArea.getText().toString()));
                    serialize.writeObject(auctionedObjects);
                    Stage stage = (Stage) ownerTxtArea.getScene().getWindow();
                    stage.getOnCloseRequest().handle(new WindowEvent(ownerTxtArea.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                    stage.close();
                } catch (IOException | IncorrectEntryException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                Stage stage = (Stage) ownerTxtArea.getScene().getWindow();
                stage.getOnCloseRequest().handle(new WindowEvent(ownerTxtArea.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();
                SingAuction.getInstance().setNewAuction(false);
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
