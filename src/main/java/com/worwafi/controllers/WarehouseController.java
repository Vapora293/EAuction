package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.worwafi.others.AuctionedObject;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class WarehouseController extends PatternController implements Initializable {
    Stage auctionedStage = new Stage();

    @FXML
    private GridPane WarehouseGridPane;
    @FXML
    private JFXButton logOutBtn;
    @FXML
    private JFXTextArea timeTextArea;
    @FXML
    private JFXTextArea welcomeTextArea;
    @FXML
    private ListView<AuctionedObject> listObjects;
    @FXML
    private Button getAucObject;
    @FXML
    private Button addAucObject;
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
        setupBackButton();
        setupListView();
        auctionedStage.setOnCloseRequest(event -> {
            setupListView();
            System.out.println("yes");
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

    private void setupBackButton() {
        backButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/intro_screen.fxml"));
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
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
            System.out.println("Nema nijake veci u seba");
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
                        lineSplit[1], Double.valueOf(lineSplit[2]), Double.valueOf(lineSplit[3]), lineSplit[4],
                        lineSplit[5], lineSplit[6]);
                possesion.add(actual);
            }
            return possesion;
        } catch (FileNotFoundException e) {

        }
        return null;
    }
//    private void setupAreas() {
//        welcomeTextArea.setText(SingUserInfo.getInstance().getLoggedUser().getUsername());
//        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
//            LocalTime currentTime = LocalTime.now();
//            timeTextArea.setText(currentTime.getHour() + ":" + currentTime.getMinute());
//        }),
//                new KeyFrame(Duration.minutes(1))
//        );
//        clock.setCycleCount(Animation.INDEFINITE);
//        clock.play();
//    }
}
