package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.auctionedObject.AuctionedObject;
import com.worwafi.others.GenericList;
import com.worwafi.others.Serialize;
import com.worwafi.singleton.SingActualObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
     * completely processed. After setting up pattern, two buttons - one for loading, second
     * for adding new object are being initialized.
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

    /**
     * sets up Auctioned Objects listview
     */
    private void setupListView() {
        listObjects.getItems().clear();
        try {
            GenericList<AuctionedObject> items = (GenericList<AuctionedObject>) serialize.readObject("warehouse");
            listObjects.setItems(items.getList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
