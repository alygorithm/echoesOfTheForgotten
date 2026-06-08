package it.unicam.cs.mpgc.rpg122711.flow;

import it.unicam.cs.mpgc.rpg122711.content.MissionContent;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import it.unicam.cs.mpgc.rpg122711.ui.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameFlow {

    private final Stage stage;
    private Player player;

    private int currentMissionIndex = 1;
    private Mission currentMission;

    private final WorldService worldService = new WorldService();

    public WorldService getWorldService() {
        return worldService;
    }

    public GameFlow(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        showMainMenu();
    }

    public void showPrologue() {
        PrologueView view = new PrologueView(this);
        setScene(view.getRoot());
    }

    public void showCharacterCreation() {
        CharacterCreationView view = new CharacterCreationView(this);
        setScene(view.getRoot());
    }

    public void showGameStart() {
        GameView view = new GameView(this);
        setScene(view.getRoot());
    }

    private void setScene(javafx.scene.Parent root) {

        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("RPG");
        stage.show();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.currentMissionIndex = 1;
    }

    public Player getPlayer() {
        return player;
    }

    public void showMainMenu() {
        MainMenuView view = new MainMenuView(this);
        setScene(view.getRoot());
    }

    public void showLoadGame() {
        LoadGameView view = new LoadGameView(this);
        setScene(view.getRoot());
    }

    private Mission buildMission(Player player) {
        return switch (currentMissionIndex) {
            case 1 -> MissionContent.firstMission(player);
            case 2 -> MissionContent.secondMission(player);
            case 3 -> MissionContent.thirdMission(player);
            case 4 -> MissionContent.fourthMission(player);
            case 5 -> MissionContent.fifthMission(player);
            case 6 -> MissionContent.finalMission(player, worldService.getMemory());

            default -> null;
        };
    }

    public Mission getCurrentMission() {
        if (player == null) return null;
        return buildMission(player);
    }

    public void nextMission() {
        currentMissionIndex++;

        if (currentMissionIndex > 6) {
            showGameCompleted();
            return;
        }

        showGameStart();
    }

    public void showGameCompleted() {
        GameCompletedView view = new GameCompletedView(this);
        setScene(view.getRoot());
    }
}