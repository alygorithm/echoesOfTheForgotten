package it.unicam.cs.mpgc.rpg122711.flow;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.ui.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameFlow {

    private final Stage stage;
    private Player player;

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

        if (player == null) {
            System.out.println("ERRORE: Player non inizializzato!");
            return;
        }

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

}