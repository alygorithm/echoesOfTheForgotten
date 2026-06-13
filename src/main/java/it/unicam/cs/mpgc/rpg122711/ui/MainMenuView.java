package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Vista JavaFX dedicata alla schermata del menu principale del gioco.
 * Configura la disposizione del titolo stilizzato e dei pulsanti interattivi
 * per avviare una nuova partita, caricare i progressi o uscire dall'applicazione.
 */
public class MainMenuView {

    private final VBox root;

    public MainMenuView(GameFlow gameFlow) {
        root = new VBox(60);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setId("mainMenu");

        VBox titleBox = new VBox(0);
        titleBox.setAlignment(Pos.CENTER);

        Label title1 = styledLabel("ECHOES", "main-title");
        Label title2 = styledLabel("of the", "sub-title");
        Label title3 = styledLabel("FORGOTTEN", "main-title");

        titleBox.getChildren().addAll(title1, title2, title3);

        VBox menuBox = new VBox(15);
        menuBox.setAlignment(Pos.CENTER);

        Button newGame = styledButton("Nuova Partita");
        Button loadGame = styledButton("Carica Partita");
        Button exit = styledButton("Esci");

        newGame.setOnAction(e -> gameFlow.showPrologue());
        loadGame.setOnAction(e -> gameFlow.showLoadGame());
        exit.setOnAction(e -> System.exit(0));

        menuBox.getChildren().addAll(newGame, loadGame, exit);

        root.getChildren().addAll(titleBox, menuBox);
    }

    private Label styledLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private Button styledButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-btn");
        button.setPrefWidth(240);
        button.setPrefHeight(45);
        return button;
    }

    public VBox getRoot() {
        return root;
    }
}