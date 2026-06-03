package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MainMenuView {
    private final VBox root;

    public MainMenuView(GameFlow gameFlow) {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setId("mainMenu");

        Label echoes = new Label("ECHOES");
        echoes.getStyleClass().add("main-title");
        Label ofThe = new Label("of the");
        ofThe.getStyleClass().add("sub-title");
        Label forgotten = new Label("FORGOTTEN");
        forgotten.getStyleClass().add("main-title");


        Button newGame = new Button("Nuova Partita");
        newGame.getStyleClass().add("menu-btn");
        Button loadGame = new Button("Carica Partita");
        loadGame.getStyleClass().add("menu-btn");
        Button exit = new Button("Esci");
        exit.getStyleClass().add("menu-btn");

        newGame.setPrefWidth(220);
        loadGame.setPrefWidth(220);
        exit.setPrefWidth(220);

        newGame.setOnAction( e -> gameFlow.showPrologue() );
        loadGame.setOnAction( e -> gameFlow.showLoadGame() );
        exit.setOnAction( e -> System.exit(0));

        root.getChildren().addAll(
                echoes,
                ofThe,
                forgotten,
                newGame,
                loadGame,
                exit
        );
    }

    public VBox getRoot() {
        return root;
    }

}
