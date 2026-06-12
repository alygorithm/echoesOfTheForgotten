package it.unicam.cs.mpgc.rpg122711.ui;

import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/*
 * Menu principale del gioco.
 *
 * Responsabilità:
 * - entry point UI
 * - navigazione verso le principali scene
 */
public class MainMenuView {

    private final VBox root;

    public MainMenuView(GameFlow gameFlow) {

        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setId("mainMenu");

        Label title1 = styledLabel("ECHOES", "main-title");
        Label title2 = styledLabel("of the", "sub-title");
        Label title3 = styledLabel("FORGOTTEN", "main-title");

        Button newGame = styledButton("Nuova Partita");
        Button loadGame = styledButton("Carica Partita");
        Button exit = styledButton("Esci");

        newGame.setOnAction(e -> gameFlow.showPrologue());
        loadGame.setOnAction(e -> gameFlow.showLoadGame());
        exit.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(
                title1,
                title2,
                title3,
                newGame,
                loadGame,
                exit
        );
    }

    private Label styledLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private Button styledButton(String text) {

        Button button = new Button(text);
        button.getStyleClass().add("menu-btn");
        button.setPrefWidth(220);

        return button;
    }

    public VBox getRoot() {
        return root;
    }
}