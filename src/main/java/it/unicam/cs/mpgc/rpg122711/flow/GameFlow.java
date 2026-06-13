package it.unicam.cs.mpgc.rpg122711.flow;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import it.unicam.cs.mpgc.rpg122711.persistence.GamePersistence;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveData;
import it.unicam.cs.mpgc.rpg122711.persistence.SaveService;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import it.unicam.cs.mpgc.rpg122711.ui.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * E' il controller principale del flusso di gioco.
 * Permette di coordinare il passaggio visivo tra le schermate dell'interfaccia,
 * gestire il ciclo di vita delle missioni e di interfacciare la persistenza per i salvataggi.
 */
public class GameFlow {

    private final Stage stage;
    private final GamePersistence saveManager;
    private final SaveService saveService = new SaveService();

    private Scene scene;
    private Player player;

    private final WorldService worldService = new WorldService();
    private final MissionManager missionManager = new MissionManager();

    private Integer currentSlot = null;

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

    private void setScene(Parent root) {
        if (scene == null) {
            scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(
                    getClass().getResource("/style.css").toExternalForm()
            );
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
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

    public MissionManager getMissionManager() {
        return missionManager;
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

    public boolean saveExists(int slot) {
        return saveService.exists(slot);
    }

    public List<Integer> getOccupiedSlots() {
        List<Integer> occupied = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            if (saveService.exists(i)) {
                occupied.add(i);
            }
        }
        return occupied;
    }

    public void executeSave(int slot) {
        this.currentSlot = slot;
        SaveData data = saveManager.save(player, missionManager, worldService);
        saveService.save(data, slot);
    }

    public boolean executeLoad(int slot) {
        SaveData data = saveService.load(slot);
        if (data != null) {
            this.currentSlot = slot;
            loadFromSave(data);
            return true;
        }
        return false;
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

    public int getCurrentSlot() {
        if (currentSlot == null) {
            throw new IllegalStateException("Slot non selezionato");
        }
        return currentSlot;
    }

    public boolean hasSlotSelected() {
        return currentSlot != null;
    }

    public void setCurrentSlot(int slot) {
        this.currentSlot = slot;
    }

    public void startGameFromLoad() {
        showGameStart();
    }

    public String getMissionName(int index, Player contextPlayer) {
        return missionManager.getMissionName(index, contextPlayer);
    }
}