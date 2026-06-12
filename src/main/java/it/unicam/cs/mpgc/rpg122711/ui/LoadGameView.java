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

    private final VBox root = new VBox(20);
    private final SaveService saveService;

    public LoadGameView(GameFlow gameFlow) {

        this.saveService = new SaveService();

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.getStyleClass().add("load-view");

        Label title = new Label("Carica Partita");
        title.getStyleClass().add("load-title");

        root.getChildren().add(title);

        for (int i = 1; i <= 3; i++) {
            root.getChildren().add(createSlot(gameFlow, i));
        }

        root.getChildren().add(createBackButton(gameFlow));
    }

    private VBox createSlot(GameFlow gameFlow, int slot) {

        SaveData data = saveService.load(slot);

        VBox box = new VBox(8);
        box.getStyleClass().add("save-slot");
        box.setPadding(new Insets(12));

        Label title = new Label("Slot " + slot);
        title.getStyleClass().add("slot-title");

        Label info = new Label(formatSlotInfo(data));
        info.getStyleClass().add("slot-info");

        Button loadBtn = new Button(data == null ? "Slot vuoto" : "Carica");
        loadBtn.setMaxWidth(Double.MAX_VALUE);

        loadBtn.setDisable(data == null);

        loadBtn.setOnAction(e -> {

            if (data == null) return;

            gameFlow.setCurrentSlot(slot);
            gameFlow.loadFromSave(data);
            gameFlow.showGameStart();
        });

        box.getChildren().addAll(title, info, loadBtn);
        return box;
    }

    private String formatSlotInfo(SaveData data) {

        if (data == null || data.player == null) {
            return "Nessun salvataggio presente";
        }

        return """
                Player: %s
                Classe: %s
                Livello: %d
                Anno: %d
                """.formatted(
                data.player.name,
                data.player.characterClass,
                data.player.level,
                data.world.year
        );
    }

    private Button createBackButton(GameFlow gameFlow) {

        Button back = new Button("Torna indietro");
        back.getStyleClass().add("back-btn");

        back.setOnAction(e -> gameFlow.showMainMenu());

        return back;
    }

    public VBox getRoot() {
        return root;
    }
}