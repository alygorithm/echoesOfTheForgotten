package it.unicam.cs.mpgc.rpg122711.ui;

import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/*
 * View finale del gioco.
 * Responsabilità: mostra schermata di conclusione e ritorno al menu.
 */
public class GameCompletedView {

    private final VBox root;

    public GameCompletedView(GameFlow gameFlow) {
        this.root = new VBox(20);
        buildUI(gameFlow);
    }

    private void buildUI(GameFlow gameFlow) {

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Label title = new Label("FINE");
        title.setId("title");

        Label text = new Label("""
                Il tuo viaggio è giunto al termine, l'ultimo passo è stato consumato sul
                sentiero del silenzio.

                Ma la fine non è oblio: le memorie strappate al nulla e raccolte lungo il cammino
                non svaniranno con te.

                Continueranno a fluttuare nell'oscurità, come sussurri immortali destinati
                a riecheggiare ben oltre l'orlo dell'ultima, definitiva pagina della storia.
        """);

        text.setWrapText(true);

        Button menuButton = new Button("Torna al menù principale");
        menuButton.setOnAction(e -> gameFlow.showMainMenu());

        applyButtonStyle(menuButton);

        root.getChildren().addAll(title, text, menuButton);
    }

    /*
     * SRP interno: styling UI pulsanti coerente tra le view.
     * (meglio sarebbe una utility condivisa, ma qui resta locale)
     */
    private void applyButtonStyle(Button button) {

        String baseStyle = """
            -fx-background-color: #3a3f4b;
            -fx-text-fill: #e6e5e0;
            -fx-padding: 10 22;
            -fx-background-radius: 6;
        """;

        String hoverStyle = """
            -fx-background-color: #4a5160;
            -fx-text-fill: #ffffff;
            -fx-padding: 10 22;
            -fx-background-radius: 6;
        """;

        button.setStyle(baseStyle);

        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));
    }

    public Parent getRoot() {
        return root;
    }
}