package com.worwafi.others;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomPopup {
    private Button button;
    private javafx.scene.control.Label textArea;
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
        pane.getStylesheets().add(this.getClass().getResource("/fxml/basic.css").toExternalForm());
        pane.getStyleClass().add("regularGridPane");
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.setPrefWidth(100);
        pane.setStyle("-fx-background-color: linear-gradient(#AF40FF, #5B42F3 100%,#00DDEB);");

        textArea = new Label(text);
        textArea.getStyleClass().add("mainTextArea");
        pane.getChildren().add(textArea);
//        textArea.setLayoutX(0);
//        textArea.setLayoutY(0);
//        textArea.skinProperty().addListener(new ChangeListener<Skin<?>>() {
//            @Override
//            public void changed(
//                    ObservableValue<? extends Skin<?>> ov, Skin<?> t, Skin<?> t1) {
//                if (t1 != null && t1.getNode() instanceof Region) {
//                    Region r = (Region) t1.getNode();
//                    r.setBackground(Background.EMPTY);
//
//                    r.getChildrenUnmodifiable().stream().
//                            filter(n -> n instanceof Region).
//                            map(n -> (Region) n).
//                            forEach(n -> n.setBackground(Background.EMPTY));
//
//                    r.getChildrenUnmodifiable().stream().
//                            filter(n -> n instanceof Control).
//                            map(n -> (Control) n).
//                            forEach(c -> c.skinProperty().addListener(this)); // *
//                }
//            }
//        });
//        textArea.setStyle("-fx-background-color: transparent;");
//        textArea.setStyle("-fx-start-margin: 5px;");
//        textArea.setStyle("-fx-background-color: #ffffff;");
//        textArea.setStyle("-fx-border-radius: 100px;");
//        textArea.setStyle("-fx-text-fill: linear-gradient(#AF40FF, #5B42F3 100%,#00DDEB);");
//        textArea.setStyle("border: 0;");
//        textArea.setStyle("display: flex;");
//        textArea.setStyle("font-family: Phantomsans, sans-serif;");
//        textArea.setStyle("font-size: 20px;");
//        textArea.setStyle("justify-content: center;");
//        textArea.setStyle("line-height: 1em;");
//        textArea.setStyle("max-width: 100%;");
//        textArea.setStyle("min-width: 140px;");
//        textArea.setStyle("padding: 19px 24px;");
//        pane.getChildren().add(textArea);

        button = new Button();
        button.getStyleClass().add("regularButton");
        button.setText(buttonText);
        button.setLayoutX(80);
        button.setLayoutY(40);
//        button.setStyle("-fx-background-color: #ffffff;");
//        button.setStyle("border: 0;");
//        button.setStyle("border-radius: 8px;");
//        button.setStyle("box-shadow: rgba(151, 65, 252, 0.2) 0 15px 30px -5px;");
//        button.setStyle("box-sizing: border-box;");
//        button.setStyle("-fx-text-fill: linear-gradient(#AF40FF, #5B42F3 100%,#00DDEB);");
//        button.setStyle("display: flex;");
//        button.setStyle("font-family: Phantomsans, sans-serif;");
//        button.setStyle("font-size: 20px;");
//        button.setStyle("justify-content: center;");
//        button.setStyle("line-height: 1em;");
//        button.setStyle("max-width: 100%;");
//        button.setStyle("min-width: 140px;");
//        button.setStyle("padding: 19px 24px;");
//        button.setStyle("text-decoration: none;");
//        button.setStyle("white-space: nowrap;");
//        button.setStyle(" cursor: pointer;");
        pane.getChildren().add(button);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        button.setOnAction(event -> {
            stage.close();
        });
    }
}
