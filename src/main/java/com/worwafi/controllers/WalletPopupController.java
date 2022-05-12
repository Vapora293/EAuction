package com.worwafi.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.worwafi.others.CustomPopup;
import com.worwafi.singleton.SingStage;
import com.worwafi.singleton.SingUserInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WalletPopupController implements Initializable {

    @FXML
    private JFXTextField firstTF;

    @FXML
    private JFXButton localButton;

    @FXML
    private JFXTextField secondTF;

    Stage popupStage;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed. sets up buttons for raising the account and if the names in the fields do not match with
     * the username, the warning is thrown.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        popupStage = new Stage();
        localButton.setOnAction(event -> {
            Stage stage = (Stage) localButton.getScene().getWindow();
            stage.getOnCloseRequest().handle(new WindowEvent(localButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
            if (!SingUserInfo.getInstance().getLoggedUser().getUsername().equals(secondTF.getText())) {
                popupStage.setTitle("Warning");
                CustomPopup alert = new CustomPopup("Are you sure it is you you want to raise account?", "yes");
                Scene popupScene = new Scene(alert.asParent(), 300, 100);
                popupStage.setScene(popupScene);
                popupStage.show();
            }
        });
        popupStage.setOnCloseRequest(event -> {
            SingUserInfo.getInstance().getLoggedUser().writeIntoCashAccount(Double.parseDouble(firstTF.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/wallet_screen.fxml"));
            try {
                Scene actual = new Scene(loader.load());
                SingStage.getInstance().setScene(actual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
