package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.content.PrologueContent;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.List;

/**
 * Vista JavaFX responsabile della sequenza di testo introduttiva.
 * Permette la gestione della visualizzazione sequenziale delle linee di testo narrative
 * attraverso transizioni di dissolvenza controllare dai vari click dell'utente.
 */
public class PrologueView {

    private final GameFlow gameFlow;
    private final VBox root = new VBox(0);

    private final Label text = new Label();
    private final Label hint = new Label("* Clicca ovunque per continuare *");

    private final List<String> lines = PrologueContent.lines();
    private int index = 0;

    private boolean animating = false;

    public PrologueView(GameFlow gameFlow) {
        this.gameFlow = gameFlow;

        root.setAlignment(Pos.CENTER);
        root.setId("prologueView");
        root.setPadding(new Insets(50));

        VBox containerBox = new VBox(25);
        containerBox.setAlignment(Pos.CENTER);
        containerBox.setMaxWidth(700);
        containerBox.setMinHeight(250);
        containerBox.getStyleClass().add("prologue-box");

        text.setWrapText(true);
        text.setMaxWidth(600);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setAlignment(Pos.CENTER);
        text.setStyle("-fx-font-size: 19px; -fx-text-fill: #e5dac1; -fx-line-spacing: 8px;");

        hint.getStyleClass().add("prologue-hint");
        hint.setOpacity(0);

        containerBox.getChildren().addAll(text, hint);

        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        root.getChildren().addAll(topSpacer, containerBox, bottomSpacer);

        showLine(0);

        root.setOnMouseClicked(e -> nextLine());
    }

    private void showLine(int i) {
        animating = true;
        hint.setOpacity(0);

        text.setText(lines.get(i));
        text.setOpacity(0);

        FadeTransition fadeText = new FadeTransition(Duration.millis(800), text);
        fadeText.setFromValue(0);
        fadeText.setToValue(1);

        fadeText.setOnFinished(e -> {
            animating = false;
            FadeTransition fadeHint = new FadeTransition(Duration.millis(400), hint);
            fadeHint.setFromValue(0);
            fadeHint.setToValue(1);
            fadeHint.play();
        });

        fadeText.play();
    }

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