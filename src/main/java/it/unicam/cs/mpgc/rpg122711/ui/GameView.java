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

import java.util.List;

public class GameView {

    private final GameFlow gameFlow;
    private final Player player;
    private final WorldService worldService;
    private final SaveService saveService;

    private final BorderPane root = new BorderPane();

    private Mission currentMission;
    private MissionStep currentStep;

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

        statsArea.setWrapText(true);
        statsArea.setEditable(false);
        statsArea.setMinWidth(220);

        root.setTop(topBar);
        root.setCenter(storyArea);
        root.setRight(statsArea);
        root.setBottom(choicesBox);

        BorderPane.setMargin(choicesBox, new Insets(10));
        BorderPane.setMargin(statsArea, new Insets(10));
    }

    // ---------------- SAVE / LOAD ----------------

    private void saveGame() {
        SaveData data = gameFlow.toSaveData();
        saveService.save(data, gameFlow.getCurrentSlot());
    }

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

        currentStep = step;

        storyArea.setText(buildStoryText(step));

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

    // ---------------- TEXT RENDERING ----------------

    private String buildStoryText(MissionStep step) {
        return """
                %s

                === %s ===

                %s
                """.formatted(
                buildWorldHeader(),
                currentMission.getTitle(),
                step.getText()
        );
    }

    private String buildWorldHeader() {
        return """
                WORLD
                Year: %d
                Instability: %d
                """.formatted(
                worldService.getState().getYear(),
                worldService.getState().getInstability()
        );
    }

    private void refreshUI() {
        statsArea.setText(buildStatsText());
    }

    private String buildStatsText() {
        return """
                PLAYER
                %s
                %s

                HP: %d
                Mana: %d
                Level: %d
                XP: %d

                WORLD
                Year: %d
                Instability: %d
                Flags: %d
                """.formatted(
                player.getName(),
                player.getCharacterClass(),
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