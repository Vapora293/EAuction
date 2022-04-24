package com.worwafi;

import com.worwafi.singleton.SingStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


//TODO thread - paralelizacia prace, setrenie vykonu, pomocou casovaca odsun
//TODO body - custom exception, nielen vypis
//TODO hra s obrazkami
//TODO checkCorrect v MainController
//TODO newObjectSetup v ActualObjectController upravit
//TODO dropdownButton na enums
//TODO serializacia spravit na jednu triedu, co najviac rozvite aby to vedelo serializovat
//TODO checknut polymorfizmus pre rozne spravania, hlavne prejav (napr rozne usery)
//TODO path from content root
//TODO novy eventHandler - napr pri zavreti - pozriet update databazy
//TODO aj koncepty napr na aukciu - neuplna perzistencia
//TODO prihlasenie sa na este nezacatu aukciu
//TODO navrhove vzory - observer, visitor

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

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
