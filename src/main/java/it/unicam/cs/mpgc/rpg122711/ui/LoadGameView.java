package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoadGameView {
    private final VBox root;

    public LoadGameView(GameFlow gameFlow) {
        root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Carica Partita");

        Button save1 = new Button("Slot Salvataggio 1");
        Button save2 = new Button("Slot salvataggio 2");
        Button save3 = new Button("Slot salvataggio 3");

        Button back = new Button("Torna indietro");
        back.setOnAction( e -> gameFlow.showMainMenu());

        root.getChildren().addAll(
                title,
                save1,
                save2,
                save3,
                back
        );
    }

    public VBox getRoot() {
        return root;
    }
}
