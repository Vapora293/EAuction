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

public class CustomPopup {
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

    public void initialize() {
        Stage stage = new Stage();
        stage.setTitle(name);
        AnchorPane pane = new AnchorPane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.setPrefWidth(100);
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        textArea = new TextArea();
        textArea.setLayoutX(0);
        textArea.setLayoutY(0);
        textArea.setText(text);
        pane.getChildren().add(textArea);

        button = new Button();
        button.setText(buttonText);
        button.setLayoutX(80);
        button.setLayoutY(40);
        pane.getChildren().add(button);
        stage.show();
        button.setOnAction(event -> {
            stage.close();
        });
    }
}
