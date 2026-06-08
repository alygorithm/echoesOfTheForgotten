package it.unicam.cs.mpgc.rpg122711.ui;

import it.unicam.cs.mpgc.rpg122711.content.*;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.*;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import it.unicam.cs.mpgc.rpg122711.content.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class GameView {

    private final GameFlow gameFlow;
    private final Player player;

    private final WorldService worldService;

    private final BorderPane root;

    private Mission currentMission;
    private MissionStep currentStep;

    // UI ELEMENTS
    private final Label missionText = new Label();
    private final Label stats = new Label();
    private final VBox choicesBox = new VBox(10);

    public GameView(GameFlow gameFlow) {
        this.gameFlow = gameFlow;
        this.player = gameFlow.getPlayer();

        this.worldService = gameFlow.getWorldService();

        this.root = new BorderPane();
        this.root.setPadding(new Insets(15));

        buildUI();
        loadMission();
        refreshUI();
    }

    // ---------------- UI SETUP ----------------

    private void buildUI() {

        missionText.setWrapText(true);
        missionText.setStyle("-fx-font-size: 16px;");

        stats.setStyle("-fx-font-size: 12px;");

        choicesBox.setSpacing(10);

        root.setCenter(missionText);
        root.setBottom(choicesBox);
        root.setRight(stats);

        BorderPane.setMargin(choicesBox, new Insets(10));
        BorderPane.setMargin(stats, new Insets(10));

        missionText.setId("missionText");
        stats.setId("stats");
    }

    // ---------------- GAME FLOW ----------------

    private void loadMission() {

        currentMission = gameFlow.getCurrentMission();
        currentStep = currentMission.getStartStep();

        missionText.setText(
                WorldContent.getIntro(worldService.getState()) +
                        "\n\n=== " + currentMission.getTitle() + " ===\n\n" +
                        currentStep.getText()
        );

        loadStep(currentStep);
    }

    private void loadStep(MissionStep step) {

        currentStep = step;
        missionText.setText(step.getText());
        choicesBox.getChildren().clear();

        List<MissionChoice> choices = step.getChoices();

        if (choices == null || choices.isEmpty()) {

            Button continueBtn = new Button("Continua");
            continueBtn.setMaxWidth(Double.MAX_VALUE);
            continueBtn.setOnAction(e -> {
                worldService.advanceAfterMission();
                gameFlow.nextMission();
                refreshUI();
            });

            choicesBox.getChildren().add(continueBtn);
            return;
        }

        for (MissionChoice choice : choices) {

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
                }

                refreshUI();
            });

            choicesBox.getChildren().add(btn);
        }
    }

    // ---------------- STATS ----------------

    private void refreshUI() {

        stats.setText(
                "PLAYER\n" +
                        player.getName() + "\n" +
                        player.getCharacterClass() + "\n\n" +
                        "HP: " + player.getHp() + "\n" +
                        "Mana: " + player.getMana() + "\n" +
                        "Level: " + player.getLevel() + "\n" +
                        "XP: " + player.getExperience() + "\n\n" +
                        "WORLD\n" +
                        "Year: " + worldService.getState().getYear() + "\n" +
                        "Flags: " + worldService.getMemory().contains(EventType.ENTERED_RUINS)
        );
    }

    // ---------------- ROOT ----------------

    public BorderPane getRoot() {
        return root;
    }
}