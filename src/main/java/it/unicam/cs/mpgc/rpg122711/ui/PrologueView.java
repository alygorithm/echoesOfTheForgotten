package it.unicam.cs.mpgc.rpg122711.ui;

import it.unicam.cs.mpgc.rpg122711.content.PrologueContent;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.List;

/*
 * View narrativa del prologo.
 *
 * Responsabilità:
 * - mostrare sequenza testuale introduttiva
 * - gestire avanzamento interattivo
 */
public class PrologueView {

    private final GameFlow gameFlow;
    private final VBox root = new VBox(20);

    private final Label text = new Label();

    private final List<String> lines = PrologueContent.lines();
    private int index = 0;

    private boolean animating = false;

    public PrologueView(GameFlow gameFlow) {

        this.gameFlow = gameFlow;

        root.setAlignment(Pos.CENTER);
        root.setId("prologueView");
        root.setStyle("-fx-background-color: #1b1d24;");

        text.setWrapText(true);
        text.setMaxWidth(650);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setAlignment(Pos.CENTER);
        text.setStyle("""
                -fx-font-size: 18px;
                -fx-text-fill: #e6e5e0;
                -fx-alignment: center;
        """);

        root.getChildren().add(text);

        showLine(0);

        root.setOnMouseClicked(e -> nextLine());
    }

    /*
     * Mostra una riga del prologo con fade-in.
     */
    private void showLine(int i) {

        animating = true;

        text.setText(lines.get(i));
        text.setOpacity(0);

        FadeTransition fade = new FadeTransition(Duration.millis(500), text);
        fade.setFromValue(0);
        fade.setToValue(1);

        fade.setOnFinished(e -> animating = false);
        fade.play();
    }

    /*
     * Avanza la narrazione al click.
     */
    private void nextLine() {

        if (animating) return;

        index++;

        if (index >= lines.size()) {
            gameFlow.showCharacterCreation();
            return;
        }

        showLine(index);
    }

    public VBox getRoot() {
        return root;
    }
}