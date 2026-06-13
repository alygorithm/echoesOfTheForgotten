package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Vista JavaFX per la schermata di conclusione del gioco.
 * Mostra il testo narrativo finale e fornisce un pulsante per tornare
 * al menù principale, chuidendo così il ciclo del flusso di gioco.
 */
public class GameCompletedView {

    private final VBox root;

    public GameCompletedView(GameFlow gameFlow) {
        this.root = new VBox(30);
        buildUI(gameFlow);
    }

    private void buildUI(GameFlow gameFlow) {
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setId("prologueView");

        Label title = new Label("FINE");
        title.getStyleClass().add("main-title");

        VBox textContainer = new VBox();
        textContainer.getStyleClass().add("prologue-box");
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setMaxWidth(600);

        Label text = new Label("""
                Il tuo viaggio è giunto al termine, l'ultimo passo è stato consumato sul
                sentiero del silenzio.
                
                Ma la fine non è oblio: le memorie strappate al nulla e raccolte lungo il cammino
                non svaniranno con te.
                
                Continueranno a fluttuare nell'oscurità, come sussurri immortali destinati
                a riecheggiare ben oltre l'orlo dell'ultima, definitiva pagina della storia.
                """);

        text.getStyleClass().add("mission-text");
        text.setWrapText(true);
        text.setMaxWidth(520);

        textContainer.getChildren().add(text);

        Button menuButton = new Button("Torna al menù principale");
        menuButton.getStyleClass().add("menu-btn");
        menuButton.setPrefHeight(45);
        menuButton.setOnAction(e -> gameFlow.showMainMenu());

        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        root.getChildren().addAll(topSpacer, title, textContainer, menuButton, bottomSpacer);
    }

    public Parent getRoot() {
        return root;
    }
}