package com.worwafi;

import com.worwafi.singleton.SingStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the first stage. Moves it to Singleton as a scene changer
     *
     * @param primaryStage stage to cast to
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        SingStage.setInstance(primaryStage);
        SingStage.getInstance().getStage().setTitle("3xBet");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_screen.fxml"));
        Scene actual = new Scene(loader.load());
        SingStage.getInstance().getStage().setScene(actual);
        primaryStage = SingStage.getInstance().getStage();
        primaryStage.show();
    }
}
