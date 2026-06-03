package it.unicam.cs.mpgc.rpg122711;

import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameFlow gameFlow = new GameFlow(primaryStage);
        gameFlow.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}