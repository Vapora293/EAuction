package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.auctions.AuctionStatusListener;
import com.worwafi.auctions.EnglishAuction;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.User;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionController extends PatternController implements Initializable, AuctionStatusListener {
    private final DecimalFormat df = new DecimalFormat("0.00");
    private boolean start = false;
    PauseTransition pauseTransition;
    @FXML
    private JFXTextField actualBalance;

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
    private JFXTextArea callingTextArea;

    @FXML
    private JFXButton recom1;

    @FXML
    private JFXButton recom2;

    @FXML
    private JFXButton setBid;

    @FXML
    private JFXTextField timeTextArea;

    @FXML
    private JFXTextField welcomeTextArea;
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
        startAuction();
        SingAuction.getInstance().getAuction().callAuction();
    }

    @Override
    public void reverseButtonListeners() {
        recom2.setVisible(false);
        setBid.setVisible(false);
        getBid.setVisible(false);
        recom1.setText("Accept the bid");
        recom1.setOnAction(event -> {
            updateReverseLayout();
            SingAuction.getInstance().getAuction().setEnd();
            pauseTransition.pause();
        });
    }

    @Override
    public void englishButtonListeners() {
        buttonSetting(recom1);
        buttonSetting(recom2);
        setBid.setOnAction(event -> {
            double actual = Double.parseDouble(getBid.getText());
            if(Double.compare(SingAuction.getInstance().getAuction().getActualPrice() + actual,
                    SingUserInfo.getInstance().getLoggedUser().getCashAccount().getCredit()) > 0) {
                getBid.setText("Not enough money to bet");
                return;
            }
            callingTextArea.setText("Calling for the " + "1. time");
            SingAuction.getInstance().getAuction().bid(SingUserInfo.getInstance().getLoggedUser(),
                    Double.parseDouble(String.valueOf(Math.round(SingAuction.getInstance().getAuction().getActualPrice() + actual))));
            updateEnglishLayout();
            pauseTransition.pause();
            if (SingAuction.getInstance().getAuction() instanceof EnglishAuction) {
                calling();
            }
        });
    }

    private void buttonSetting(JFXButton actualButton) {
        actualButton.setOnAction(event -> {
            String[] text = actualButton.getText().split(" ");
            double actual = Double.parseDouble(text[1]);
            if(Double.compare(SingAuction.getInstance().getAuction().getActualPrice() + actual,
                    SingUserInfo.getInstance().getLoggedUser().getCashAccount().getCredit()) > 0) {
                getBid.setText("Not enough money to bet");
                return;
            }
            callingTextArea.setText("Calling for the " + "1. time");
            SingAuction.getInstance().getAuction().bid(SingUserInfo.getInstance().getLoggedUser(),
                    Double.parseDouble(String.valueOf(Math.round(SingAuction.getInstance().getAuction().getActualPrice() + actual))));
            updateEnglishLayout();
            pauseTransition.pause();
            if (SingAuction.getInstance().getAuction() instanceof EnglishAuction) {
                calling();
            }
        });
    }


    private void startAuction() {
        SingAuction.getInstance().getAuction().setAuctionStatusListener(this);
        //TODO zmenit toto na thread
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int local = 5;

            @Override
            public void run() {
                callingTextArea.setText(String.valueOf(local));
                local--;
                if (local < 0) {
                    timer.cancel();
                    callingTextArea.setText(auctionStartQuote());
                    lowNavBar.setDisable(false);
                    calling();
                }
            }
        }, 0, 1000);
    }

    private void calling() {
        pauseTransition = new PauseTransition(Duration.seconds(2));
        pauseTransition.play();
        pauseTransition.setOnFinished(event -> {
            if (SingAuction.getInstance().getAuction() instanceof EnglishAuction) {
                callingTextArea.setText("Calling for the " + (pauseTransition.getCycleCount()) + ". time");
            }
            int check = SingAuction.getInstance().getAuction().handleCycle(pauseTransition.getCycleCount());
            if (check == -1) {
                callingTextArea.setText("Winner of " + SingActualObject.getInstance().getObject().getName() + " is "
                        + SingAuction.getInstance().getAuction().getActualWinner().getUsername() + "!");
                pauseTransition.pause();
                lowNavBar.setDisable(true);
            } else {
                pauseTransition.setCycleCount(check);
                pauseTransition.playFromStart();
            }
        });

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            int local = 3;
//            @Override
//            public void run() {
//                calling.setText("Calling for the " + (4-local) + ". time");
//                local--;
//                if (callBidders()) {
//                    timer.cancel();
//                    calling();
//                }
//                if (local < 0) {
//                    timer.cancel();
//                    SingAuction.getInstance().getAuction().setEnd();
//                    calling.setText("Winner of " + SingActualObject.getInstance().getObject().getName() + " is " + SingAuction.getInstance().getAuction().getActualWinner().getUsername() + "!");
//                    SingAuction.getInstance().getAuction().setEnd();
//                    SingActualObject.getInstance().getObject().setStatus(ObjectStatus.SOLD);
//                }
//            }
//        }, 0, 3000);
    }

//    private boolean callBidders() {
//        for (int i = 1; i < SingAuction.getInstance().getAuction().getBidders().size(); i++) {
//            Random rand = new Random();
//            int check = rand.nextInt(100);
//            double price = SingAuction.getInstance().getAuction().getActualPrice() * (1 + Double.parseDouble(df.format(rand.nextDouble())));
//            if (check < 10 && SingAuction.getInstance().getAuction().getBidders().get(i).getCashAccount().getCredit() > price) {
//                SingAuction.getInstance().getAuction().bid(SingAuction.getInstance().getAuction().getBidders().get(i), price);
//                updateLayout();
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void setupAreas() {
        super.setupAreas();
        try {
            auctionedImage.setImage(new Image(new FileInputStream(SingActualObject.getInstance().getObject().getPicture())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        actualBalance.setText(String.valueOf(SingUserInfo.getInstance().getLoggedUser().getCashAccount().getCredit()));
    }

    private void setupBidders() {
        for (User actual : SingAuction.getInstance().getAuction().getBidders().getList()) {
            biddersBox.getChildren().add(new Text(actual.getUsername()));
        }
        biddersBox.getStyleClass().add("regularBox");
    }


    @Override
    public void updateEnglishLayout() {
        if(start)
            informBid.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername() + " has raised to "
                    + SingAuction.getInstance().getAuction().getActualPrice());
        start = true;
        actualBid.setText(df.format(SingAuction.getInstance().getAuction().getActualPrice()));
        actualBidder.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername());
        recom1.setText("Bid +" + (SingAuction.getInstance().getAuction().getActualPrice() / 10));
        recom2.setText("Bid +" + (SingAuction.getInstance().getAuction().getActualPrice() / 5));
    }

    @Override
    public void decreasePrice() {
        informBid.setText("Price has been decreased to " + SingAuction.getInstance().getAuction().getActualPrice());
        actualBid.setText(df.format(SingAuction.getInstance().getAuction().getActualPrice()));
    }

    @Override
    public void updateReverseLayout() {
        if(start)
            informBid.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername() +
                    " has accepted the price of " + SingAuction.getInstance().getAuction().getActualPrice());
        start = true;
        callingTextArea.setText("Winner of " + SingActualObject.getInstance().getObject().getName() + " is "
                + SingAuction.getInstance().getAuction().getActualWinner().getUsername() + "!");
        actualBid.setText(df.format(SingAuction.getInstance().getAuction().getActualPrice()));
        actualBidder.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername());
    }
}
