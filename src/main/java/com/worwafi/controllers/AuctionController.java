package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

//TODO sumy na biddovanie zaokruhlovanie
//TODO fixnut ten timer na buttony
public class AuctionController extends PatternController implements Initializable {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    @FXML
    private JFXTextArea actualBalance;

    @FXML
    private JFXTextArea actualBid;

    @FXML
    private JFXTextArea actualBidder;

    @FXML
    private ImageView auctionedImage;

    @FXML
    private JFXButton backButton;

    @FXML
    private VBox biddersBox;

    @FXML
    private JFXTextField getBid;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private JFXTextArea calling;

    @FXML
    private JFXButton recom1;

    @FXML
    private JFXButton recom2;

    @FXML
    private JFXButton setBid;

    @FXML
    private JFXTextArea timeTextArea;

    @FXML
    private JFXTextArea welcomeTextArea;
    @FXML
    private ButtonBar lowNavBar;
    @FXML
    private JFXTextArea informBid;

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
        setupBidders();
        setupObject();
        startAuction();
        updateWinner();
        updateBidButtons();
        buttonListeners();
    }

    private void buttonListeners() {
        recom1.setOnAction(event -> {
            double actual = Double.parseDouble(recom1.getText().replaceAll("\\D+", "")) / 10;
            SingAuction.getInstance().getAuction().bid(SingUserInfo.getInstance().getLoggedUser(),
                    Double.parseDouble(df.format(SingAuction.getInstance().getAuction().getActualPrice() + actual)));
            informChange();
            updateWinner();
            updateBidButtons();
            stopTimer();
        });
    }

    private void stopTimer() {

    }

    private void updateBidButtons() {
        recom1.setText("Bid +" + (SingAuction.getInstance().getAuction().getActualPrice() / 10));
        recom2.setText("Bid +" + (SingAuction.getInstance().getAuction().getActualPrice() / 5));
    }

    private void startAuction() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int local = 5;
            @Override
            public void run() {
                calling.setText(String.valueOf(local));
                local--;
                if (local < 0) {
                    timer.cancel();
                    calling.setText("Go!");
                    lowNavBar.setDisable(false);
                    callHelp();
                }
            }
        }, 0, 1000);
    }

    private void callHelp() {
        calling();
    }

    private void calling() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int local = 3;
            @Override
            public void run() {
                calling.setText("Calling for the " + (4-local) + ". time");
                local--;
                if (callBidders()) {
                    timer.cancel();
                    calling();
                }
                if (local < 0) {
                    timer.cancel();
                    SingAuction.getInstance().getAuction().setEnd();
                    calling.setText("Winner of " + SingActualObject.getInstance().getObject().getName() + " is " + SingAuction.getInstance().getAuction().getActualWinner().getUsername() + "!");
                    SingAuction.getInstance().getAuction().setEnd();
                }
            }
        }, 0, 3000);
    }

    private boolean callBidders() {
        for (int i = 1; i < SingAuction.getInstance().getAuction().getBidders().size(); i++) {
            Random rand = new Random();
            int check = rand.nextInt(100);
            double price = SingAuction.getInstance().getAuction().getActualPrice() * (1 + Double.parseDouble(df.format(rand.nextDouble())));
            if (check < 10 && SingAuction.getInstance().getAuction().getBidders().get(i).getCashAccount().getCredit() > price) {
                SingAuction.getInstance().getAuction().bid(SingAuction.getInstance().getAuction().getBidders().get(i), price);
                informChange();
                updateWinner();
                updateBidButtons();
                return true;
            }
        }
        return false;
    }
    private void setupObject() {
        try {
            auctionedImage.setImage(new Image(new FileInputStream(SingActualObject.getInstance().getObject().getPicture())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupBidders() {
        for (User actual : SingAuction.getInstance().getAuction().getBidders()) {
            biddersBox.getChildren().add(new Text(actual.getUsername()));
        }

    }

    private void informChange() {
        informBid.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername() + " has raised to " + SingAuction.getInstance().getAuction().getActualPrice());
    }

    private void updateWinner() {
        actualBid.setText(df.format(SingAuction.getInstance().getAuction().getActualPrice()));
        actualBidder.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername());
    }

}
