package com.worwafi.others;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomPopup implements Initializable {
    private Button button;
    private TextArea textArea;
    private String name;
    private String text;
    private String buttonText;

    public CustomPopup(String name, String text, String buttonText) {
        this.name = name;
        this.text = text;
        this.buttonText = buttonText;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Stage stage = new Stage();
        stage.setTitle(name);
        stage.show();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 100, 60);
        stage.setScene(scene);
        button.setText(buttonText);
        button.setLayoutX(80);
        button.setLayoutY(40);
        pane.getChildren().add(button);

        textArea.setLayoutX(0);
        textArea.setLayoutY(0);
        textArea.setText(text);
        pane.getChildren().add(textArea);
        stage.show();
        button.setOnAction(event -> {
            stage.getOnCloseRequest().handle(new WindowEvent(textArea.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        });
    }
}
