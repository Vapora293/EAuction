package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.others.AuctionedObject;
import com.worwafi.others.Serialize;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingUserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class WarehouseController extends PatternController implements Initializable {
    Stage auctionedStage = new Stage();
    Serialize serialize = new Serialize();

    @FXML
    private GridPane WarehouseGridPane;
    @FXML
    private JFXButton logOutBtn;
    @FXML
    private JFXTextField timeTextArea;
    @FXML
    private JFXTextField welcomeTextArea;
    @FXML
    private ListView<AuctionedObject> listObjects;
    @FXML
    private JFXButton getAucObject;
    @FXML
    private JFXButton addAucObject;
    @FXML
    private JFXButton backButton;

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
        setupAreas();
        setupNavBarButtons();
        setupListView();
        auctionedStage.setOnCloseRequest(event -> {
            setupListView();
        });
        addAucObject.setOnAction(event -> {
            SingActualObject.getInstance().setObject(null, true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auctioned_popup.fxml"));
            try {
                Scene actual = new Scene(loader.load());
                auctionedStage.setScene(actual);
                auctionedStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        getAucObject.setOnAction(event -> {
            int index = listObjects.getSelectionModel().getSelectedIndex();
            SingActualObject.getInstance().setObject(listObjects.getItems().get(index), false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auctioned_popup.fxml"));
            try {
                Scene actual = new Scene(loader.load());
                auctionedStage.setScene(actual);
                auctionedStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupListView() {
        try {
            listObjects.getItems().clear();
            listObjects.setItems(setupAuctionedObjects());
        } catch (Exception e) {
            listObjects.setItems(FXCollections.observableArrayList());
        }
    }

    private ObservableList<AuctionedObject> setupAuctionedObjects() {
        try {
            File auctionedObjectFile = new File(String.valueOf(SingUserInfo.getInstance().getLoggedUser().getObjectFile()));
            Scanner myReader = new Scanner(auctionedObjectFile);
            ObservableList<AuctionedObject> possesion = FXCollections.observableArrayList();
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String lineSplit[] = line.split(" . ");
                AuctionedObject actual = new AuctionedObject(SingUserInfo.getInstance().getLoggedUser(), lineSplit[0],
                        lineSplit[1], Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3]), lineSplit[4],
                        lineSplit[5], lineSplit[6]);
                possesion.add(actual);
            }
            SingUserInfo.getInstance().getLoggedUser().setPossession(possesion);
            return possesion;
        } catch (FileNotFoundException e) {

        }
        return null;
    }
}
