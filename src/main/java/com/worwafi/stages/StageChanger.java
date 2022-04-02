package com.worwafi.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageChanger {
    protected Stage primaryStage;
    protected Scene currentScene;
    protected FXMLLoader loader;
    public StageChanger(Stage stage, FXMLLoader loader) throws Exception {
        primaryStage = stage;
        this.loader = loader;
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }
    public void changeScene(FXMLLoader loader) throws Exception {
        this.loader = loader;
        primaryStage.setScene(new Scene(loader.load()));
    }

}
