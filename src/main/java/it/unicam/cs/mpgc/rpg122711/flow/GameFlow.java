package it.unicam.cs.mpgc.rpg122711.flow;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import it.unicam.cs.mpgc.rpg122711.persistence.GamePersistence;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveData;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import it.unicam.cs.mpgc.rpg122711.ui.*;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameFlow {

    private final Stage stage;
    private final GamePersistence saveManager;

    private Player player;

    private final WorldService worldService = new WorldService();
    private final MissionManager missionManager = new MissionManager();

    private int currentSlot = -1;

    public GameFlow(Stage stage, GamePersistence saveManager) {
        this.stage = stage;
        this.saveManager = saveManager;
    }

    // ---------------- NAVIGATION ----------------

    public void start() {
        showMainMenu();
    }

    public void showMainMenu() {
        setScene(new MainMenuView(this).getRoot());
    }

    public void showPrologue() {
        setScene(new PrologueView(this).getRoot());
    }

    public void showCharacterCreation() {
        setScene(new CharacterCreationView(this).getRoot());
    }

    public void showGameStart() {
        setScene(new GameView(this).getRoot());
    }

    public void showLoadGame() {
        setScene(new LoadGameView(this).getRoot());
    }

    public void showGameCompleted() {
        setScene(new GameCompletedView(this).getRoot());
    }

    private void setScene(javafx.scene.Parent root) {
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("RPG");
        stage.show();
    }

    // ---------------- PLAYER ----------------

    public void setPlayer(Player player) {
        this.player = player;
        missionManager.reset();
    }

    public Player getPlayer() {
        return player;
    }

    public WorldService getWorldService() {
        return worldService;
    }

    // ---------------- MISSIONS ----------------

    public Mission getCurrentMission() {
        if (player == null) return null;
        return missionManager.build(player);
    }

    public void nextMission() {
        missionManager.next();

        if (missionManager.isFinished()) {
            showGameCompleted();
            return;
        }

        showGameStart();
    }

    // ---------------- SAVE SYSTEM ----------------

    public SaveData toSaveData() {
        return saveManager.save(player, missionManager, worldService);
    }

    public void loadFromSave(SaveData data) {

        this.player = new Player(
                data.player.name,
                data.player.characterClass
        );

        for (int i = 1; i < data.player.level; i++) {
            player.gainExperience(100);
        }

        saveManager.load(data, missionManager, worldService);
    }

    // ---------------- SLOT MANAGEMENT (FIX) ----------------

    public int getCurrentSlot() {

        if (currentSlot < 1 || currentSlot > 3) {
            throw new IllegalStateException("Slot non selezionato");
        }

        return currentSlot;
    }

    public void setCurrentSlot(int slot) {
        if (slot < 1 || slot > 3) {
            throw new IllegalArgumentException("Slot non valido");
        }
        this.currentSlot = slot;
    }

    public boolean hasSlotSelected() {
        return currentSlot >= 1 && currentSlot <= 3;
    }
}