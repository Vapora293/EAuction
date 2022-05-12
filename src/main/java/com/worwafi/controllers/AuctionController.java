package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.auctions.AuctionStatusListener;
import com.worwafi.auctions.EnglishAuction;
import com.worwafi.others.GenericList;
import com.worwafi.singleton.SingActualObject;
import com.worwafi.singleton.SingAuction;
import com.worwafi.singleton.SingUserInfo;
import com.worwafi.users.User;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListView;
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
    private ListView<User> biddersBox;

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
     * completely processed. Setups buttons in the upper and lower part, defines functions for different
     * auction types to implement. Starts the auction.
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

    /**
     * Changes the areas for reverse auction implementation
     */
    @Override
    public void reverseButtonListeners() {
        recom2.setVisible(false);
        setBid.setVisible(false);
        getBid.setVisible(false);
        recom1.setText("Accept the bid");
        recom1.setOnAction(event -> {
            SingAuction.getInstance().getAuction().bid(SingUserInfo.getInstance().getLoggedUser(), Double.parseDouble(actualBid.getText()));
            SingAuction.getInstance().getAuction().setEnd();
            updateReverseLayout();
            pauseTransition.pause();
        });
    }

    /**
     * Changes the areas for english auction implementation
     */
    @Override
    public void englishButtonListeners() {
        buttonSetting(recom1);
        buttonSetting(recom2);
        setBid.setOnAction(event -> {
            double actual = Double.parseDouble(getBid.getText());
            if (Double.compare(SingAuction.getInstance().getAuction().getActualPrice() + actual,
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

    /**
     * buttons for setting, define the same function but with different values
     *
     * @param actualButton button to be set
     */
    private void buttonSetting(JFXButton actualButton) {
        actualButton.setOnAction(event -> {
            String[] text = actualButton.getText().split(" ");
            double actual = Double.parseDouble(text[1]);
            if (Double.compare(SingAuction.getInstance().getAuction().getActualPrice() + actual,
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

    /**
     * Start of the auction
     */
    private void startAuction() {
        SingAuction.getInstance().getAuction().setAuctionStatusListener(this);
        //TODO thread
        Timer timer = new Timer();
        AuctionTask task = new AuctionTask();
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    //TODO vnorena trieda

    class AuctionTask extends TimerTask {
        private int local;

        public AuctionTask() {
            local = 5;
        }

        /**
         * The action to be performed by this timer task. Timer decreases the value after one second
         */
        @Override
        public void run() {
            callingTextArea.setText(String.valueOf(local));
            local--;
            if (local < 0) {
                this.cancel();
                callingTextArea.setText(auctionStartQuote());
                lowNavBar.setDisable(false);
                calling();
            }
        }
    }

    /**
     * Function which performs the auction. Timer that eventually stops based on the input from the auctions and
     * sets the winner into the areas and disables the buttons.
     */
    private void calling() {
        pauseTransition = new PauseTransition(Duration.seconds(1));
        pauseTransition.play();
        pauseTransition.setOnFinished(event -> {
            //TODO RTTI
            if (SingAuction.getInstance().getAuction() instanceof EnglishAuction) {
                callingTextArea.setText("Calling for the " + (pauseTransition.getCycleCount()) + ". time");
            }
            int check = SingAuction.getInstance().getAuction().handleCycle(pauseTransition.getCycleCount());
            if (check == -1) {
                callingTextArea.setText("Winner of " + SingActualObject.getInstance().getObject().getName() + " is "
                        + SingAuction.getInstance().getAuction().getActualWinner().getUsername() + "!");
                informBid.setText("");
                pauseTransition.pause();
                lowNavBar.setDisable(true);
            } else {
                pauseTransition.setCycleCount(check);
                pauseTransition.playFromStart();
            }
        });
    }

    /**
     * setups areas of the view along with picture
     */
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

    /**
     * Setups bidders into the listview
     */
    private void setupBidders() {
        biddersBox.setItems(SingAuction.getInstance().getAuction().getBidders().getList());
        biddersBox.setEditable(false);
    }

    /**
     * Updates areas for English auction
     */
    @Override
    public void updateEnglishLayout() {
        if (start)
            informBid.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername() + " has raised to "
                    + SingAuction.getInstance().getAuction().getActualPrice());
        start = true;
        actualBid.setText(df.format(SingAuction.getInstance().getAuction().getActualPrice()));
        actualBidder.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername());
        recom1.setText("Bid +" + (SingAuction.getInstance().getAuction().getActualPrice() / 10));
        recom2.setText("Bid +" + (SingAuction.getInstance().getAuction().getActualPrice() / 5));
    }

    /**
     * Updates the cycle during Reverse auction
     */
    @Override
    public void decreasePrice() {
        informBid.setText("Price has been decreased to " + SingAuction.getInstance().getAuction().getActualPrice());
        actualBid.setText(df.format(SingAuction.getInstance().getAuction().getActualPrice()));
    }

    /**
     * Updates areas for Reverse auction
     */
    @Override
    public void updateReverseLayout() {
        if (start)
            informBid.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername() +
                    " has accepted the price of " + SingAuction.getInstance().getAuction().getActualPrice());
        start = true;
        callingTextArea.setText("Winner of " + SingAuction.getInstance().getAuction().getWin().getName() + " is "
                + SingAuction.getInstance().getAuction().getActualWinner().getUsername() + "!");
        actualBid.setText(df.format(SingAuction.getInstance().getAuction().getActualPrice()));
        actualBidder.setText(SingAuction.getInstance().getAuction().getActualWinner().getUsername());
    }
}
