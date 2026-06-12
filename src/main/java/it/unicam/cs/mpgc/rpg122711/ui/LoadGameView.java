package it.unicam.cs.mpgc.rpg122711.ui;

import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveData;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/*
 * View di selezione salvataggi.
 *
 * Responsabilità:
 * - mostrare slot di salvataggio
 * - delegare caricamento a GameFlow
 */
public class LoadGameView {

    private final VBox root = new VBox(15);
    private final SaveService saveService;

    public LoadGameView(GameFlow gameFlow) {

        this.saveService = new SaveService();

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Carica Partita");

        root.getChildren().addAll(
                title,
                createSlot(gameFlow, 1),
                createSlot(gameFlow, 2),
                createSlot(gameFlow, 3),
                createBackButton(gameFlow)
        );
    }

    private VBox createSlot(GameFlow gameFlow, int slot) {

        SaveData data = saveService.load(slot);

        VBox box = new VBox(5);
        box.setStyle("-fx-padding: 10; -fx-border-color: gray;");

        Label info = new Label(formatSlotInfo(slot, data));

        Button loadBtn = new Button("Carica");

        loadBtn.setOnAction(e -> {
            if (data == null) return;

            gameFlow.setCurrentSlot(slot);
            gameFlow.loadFromSave(data);
            gameFlow.showGameStart();
        });

        box.getChildren().addAll(info, loadBtn);
        return box;
    }

    private String formatSlotInfo(int slot, SaveData data) {

        if (data == null || data.player == null) {
            return "Slot " + slot + " - Vuoto";
        }

        return """
                Slot %d
                Player: %s
                Classe: %s
                Livello: %d
                Anno: %d
                """.formatted(
                slot,
                data.player.name,
                data.player.characterClass,
                data.player.level,
                data.world.year
        );
    }

    private Button createBackButton(GameFlow gameFlow) {

        Button back = new Button("Torna indietro");
        back.setOnAction(e -> gameFlow.showMainMenu());
        return back;
    }

    public VBox getRoot() {
        return root;
    }
}