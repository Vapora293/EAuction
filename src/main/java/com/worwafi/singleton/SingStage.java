package com.worwafi.singleton;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SingStage {
    private static SingStage single_instance = null;

    private Stage actual;

    private SingStage(Stage stage) {
        actual = stage;
    }
    public static SingStage getInstance() {
        return single_instance;
    }
    public static void setInstance(Stage stage) {
        single_instance = new SingStage(stage);
    }
    public void setScene(Scene scene) {
        actual.setScene(scene);
    }
    public Stage getStage() {
        return actual;
    }
}
