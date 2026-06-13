package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveData;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Vista JavaFX dedicata alla schermata di caricamento dei vari salvataggi.
 * Genera e permette di visualizzare in modo dinamico le schede descrittive per ciascuno slot
 * di gioco, andando a mostrare i metadati del personaggio e della missione corrente (se presenti).
 */
public class LoadGameView {

    private final VBox root = new VBox(40);
    private final SaveService saveService = new SaveService();

    public LoadGameView(GameFlow gameFlow) {
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setId("loadGameView");

        Label title = new Label("CARICA PARTITA");
        title.getStyleClass().add("load-title");

        HBox slotsContainer = new HBox(20);
        slotsContainer.setAlignment(Pos.CENTER);
        slotsContainer.getChildren().addAll(
                createSlot(gameFlow, 1),
                createSlot(gameFlow, 2),
                createSlot(gameFlow, 3)
        );

        root.getChildren().addAll(
                title,
                slotsContainer,
                createBackButton(gameFlow)
        );
    }

    private VBox createSlot(GameFlow gameFlow, int slot) {
        SaveData data = saveService.load(slot);

        VBox box = new VBox(15);
        box.setPrefWidth(240);
        box.setPrefHeight(320);
        box.setAlignment(Pos.TOP_CENTER);

        if (data == null) {
            box.getStyleClass().add("slot-card-empty");
        } else {
            box.getStyleClass().add("slot-card");
        }

        Label info = new Label(formatSlotInfo(slot, data, gameFlow));
        info.getStyleClass().add("slot-info-text");
        info.setWrapText(true);
        info.setMaxWidth(210);

        Button loadBtn = new Button(data == null ? "Vuoto" : "Carica");
        loadBtn.getStyleClass().add("menu-btn");
        loadBtn.setPrefWidth(160);
        loadBtn.setPrefHeight(40);

        if (data == null) {
            loadBtn.setDisable(true);
        }

        loadBtn.setOnAction(e -> {
            if (data == null) return;
            gameFlow.setCurrentSlot(slot);
            gameFlow.loadFromSave(data);
            gameFlow.startGameFromLoad();
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        box.getChildren().addAll(info, spacer, loadBtn);
        return box;
    }

    private String formatSlotInfo(int slot, SaveData data, GameFlow gameFlow) {
        if (data == null || data.player == null) {
            return "SLOT %d\n\nNessun dato\ndisponibile".formatted(slot);
        }

        String missionDetails = "Sconosciuta";
        if (data.progress != null) {
            it.unicam.cs.mpgc.rpg122711.domain.Player tempPlayer = new it.unicam.cs.mpgc.rpg122711.domain.Player(data.player.name, data.player.characterClass);
            missionDetails = gameFlow.getMissionName(data.progress.currentMissionIndex, tempPlayer);
        }

        return """
                SLOT %d
                
                Nome: %s
                Classe: %s
                Livello: %d
                Anno: %d
                
                Missione:
                %s
                """.formatted(
                slot,
                data.player.name,
                data.player.characterClass.getLabel(),
                data.player.level,
                data.world.year,
                missionDetails
        );
    }

    private Button createBackButton(GameFlow gameFlow) {
        Button back = new Button("Torna Indietro");
        back.getStyleClass().add("menu-btn");
        back.setPrefWidth(200);
        back.setPrefHeight(45);
        back.setOnAction(e -> gameFlow.showMainMenu());
        return back;
    }

    public VBox getRoot() {
        return root;
    }
}