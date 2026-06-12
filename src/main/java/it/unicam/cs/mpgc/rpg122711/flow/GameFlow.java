package it.unicam.cs.mpgc.rpg122711.flow;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import it.unicam.cs.mpgc.rpg122711.persistence.GamePersistence;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveData;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import it.unicam.cs.mpgc.rpg122711.ui.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Controller principale del flusso di gioco.
 *
 * Responsabilità (SRP):
 * - gestione navigazione UI JavaFX
 * - orchestrazione dei sistemi di gioco (world, missioni, save)
 * - gestione stato sessione player
 *
 * Nota:
 * GameFlow è il punto di composizione tra dominio e infrastruttura.
 */
public class GameFlow {

    private final Stage stage;
    private final GamePersistence saveManager;

    private Player player;

    private final WorldService worldService = new WorldService();
    private final MissionManager missionManager = new MissionManager();

    private int currentSlot = 1;

    public GameFlow(Stage stage, GamePersistence saveManager) {
        this.stage = stage;
        this.saveManager = saveManager;
    }

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

    /*
     * Salvataggio stato completo del gioco.
     */
    public SaveData toSaveData() {
        return saveManager.save(player, missionManager, worldService);
    }

    /*
     * Ripristino stato di gioco da salvataggio.
     *
     * Nota architetturale:
     * - Player viene ricostruito qui (responsabilità del GameFlow)
     * - SaveManager si occupa solo di world + progress
     */
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

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(int slot) {
        this.currentSlot = slot;
    }
}