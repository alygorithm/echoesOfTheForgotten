package it.unicam.cs.mpgc.rpg122711.ui;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.*;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveData;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveService;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceDialog;

import java.util.List;

public class GameView {

    private final GameFlow gameFlow;
    private final Player player;
    private final WorldService worldService;
    private final SaveService saveService;

    private final BorderPane root = new BorderPane();

    private Mission currentMission;

    private final TextArea storyArea = new TextArea();
    private final TextArea statsArea = new TextArea();
    private final VBox choicesBox = new VBox(10);

    public GameView(GameFlow gameFlow) {

        this.gameFlow = gameFlow;
        this.player = gameFlow.getPlayer();
        this.worldService = gameFlow.getWorldService();
        this.saveService = new SaveService();

        buildUI();
        loadMission();
        refreshUI();
    }

    // ---------------- UI ----------------

    private void buildUI() {

        Button saveBtn = new Button("Salva");
        Button loadBtn = new Button("Carica");
        Button menuBtn = new Button("Menu");

        saveBtn.setOnAction(e -> saveGame());
        loadBtn.setOnAction(e -> loadGame());
        menuBtn.setOnAction(e -> gameFlow.showMainMenu());

        HBox topBar = new HBox(10, saveBtn, loadBtn, menuBtn);
        topBar.setPadding(new Insets(10));

        storyArea.setWrapText(true);
        storyArea.setEditable(false);
        storyArea.setPrefWidth(650);

        statsArea.setWrapText(true);
        statsArea.setEditable(false);
        statsArea.setPrefWidth(220);

        storyArea.getStyleClass().add("mission-text");
        statsArea.getStyleClass().add("stats-panel");
        choicesBox.getStyleClass().add("choice-box");

        root.setTop(topBar);
        root.setCenter(storyArea);
        root.setRight(statsArea);
        root.setBottom(choicesBox);

        BorderPane.setMargin(choicesBox, new Insets(10));
        BorderPane.setMargin(statsArea, new Insets(10));
    }

    // ---------------- SAVE ----------------

    private void saveGame() {

        if (!gameFlow.hasSlotSelected()) {
            chooseSlotAndSave();
            return;
        }

        SaveData data = gameFlow.toSaveData();
        saveService.save(data, gameFlow.getCurrentSlot());
    }

    private void chooseSlotAndSave() {

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, 1, 2, 3);
        dialog.setTitle("Salvataggio");
        dialog.setHeaderText("Seleziona uno slot di salvataggio");
        dialog.setContentText("Slot:");

        dialog.showAndWait().ifPresent(slot -> {
            gameFlow.setCurrentSlot(slot);
            saveGame();
        });
    }

    private void showSlotDialog() {

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, 1, 2, 3);
        dialog.setTitle("Salvataggio");
        dialog.setHeaderText("Seleziona slot di salvataggio");
        dialog.setContentText("Slot:");

        dialog.showAndWait().ifPresent(slot -> {
            gameFlow.setCurrentSlot(slot);
            saveGame();
        });
    }

    // ---------------- LOAD ----------------

    private void loadGame() {

        SaveData data = saveService.load(gameFlow.getCurrentSlot());
        if (data == null) return;

        gameFlow.loadFromSave(data);

        loadMission();
        refreshUI();
    }

    // ---------------- FLOW ----------------

    private void loadMission() {

        currentMission = gameFlow.getCurrentMission();
        if (currentMission == null) return;

        loadStep(currentMission.getStartStep());
    }

    private void loadStep(MissionStep step) {

        storyArea.setText(step.getText());

        choicesBox.getChildren().clear();

        List<MissionChoice> choices = step.getChoices();

        if (choices == null || choices.isEmpty()) {
            addContinueButton();
            return;
        }

        for (MissionChoice choice : choices) {
            addChoiceButton(choice);
        }
    }

    private void addContinueButton() {

        Button btn = new Button("Continua");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(45);

        btn.setOnAction(e -> {
            worldService.advanceAfterMission();
            gameFlow.nextMission();
            loadMission();
            refreshUI();
        });

        choicesBox.getChildren().add(btn);
    }

    private void addChoiceButton(MissionChoice choice) {

        Button btn = new Button(choice.getLabel());
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(45);

        btn.setOnAction(e -> {

            MissionContext ctx = new MissionContext(player, worldService);
            choice.apply(ctx);

            MissionStep next = choice.getNextStep();

            if (next != null) {
                loadStep(next);
            } else {
                worldService.advanceAfterMission();
                gameFlow.nextMission();
                loadMission();
            }

            refreshUI();
        });

        choicesBox.getChildren().add(btn);
    }

    // ---------------- UI ----------------

    private void refreshUI() {
        statsArea.setText(buildStatsText());
    }

    private String buildStatsText() {

        return """
                ╔══ GIOCATORE ══╗

                %s
                %s

                HP: %d
                Mana: %d
                Livello: %d
                XP: %d

                ╔══ MONDO ══╗

                Anno: %d
                Instabilità: %d
                Eventi: %d
                """.formatted(
                player.getName(),
                player.getCharacterClass().getLabel(),
                player.getHp(),
                player.getMana(),
                player.getLevel(),
                player.getExperience(),
                worldService.getState().getYear(),
                worldService.getState().getInstability(),
                worldService.getMemory().getAll().size()
        );
    }

    public BorderPane getRoot() {
        return root;
    }
}