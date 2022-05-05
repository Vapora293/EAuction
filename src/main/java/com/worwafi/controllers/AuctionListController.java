package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.auctions.Auction;
import com.worwafi.others.AuctionedObject;
import com.worwafi.others.GenericList;
import com.worwafi.others.Serialize;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionListController extends PatternController implements Initializable {
    Serialize serialize = new Serialize();
    @FXML
    private GridPane WarehouseGridPane;

    @FXML
    private JFXButton addAucObject;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton getAucObject;

    @FXML
    private ListView<Auction> listObjects;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private JFXTextField timeTextArea;

    @FXML
    private JFXTextField welcomeTextArea;
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
        getAucObject.setOnAction(event -> {
            int index = listObjects.getSelectionModel().getSelectedIndex();
            SingAuction.getInstance().setAuction(listObjects.getItems().get(index), false);
            SingActualObject.getInstance().setObject(SingAuction.getInstance().getAuction().getWin(), false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_screen.fxml"));
            try {
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addAucObject.setOnAction(event -> {
            int index = listObjects.getSelectionModel().getSelectedIndex();
            SingAuction.getInstance().setAuction(listObjects.getItems().get(index), true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_screen.fxml"));
            try {
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupListView() {
        listObjects.getItems().clear();
        try {
            GenericList<Auction> items = (GenericList<Auction>) serialize.readObject("auctions");
            listObjects.setItems(items.getList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
