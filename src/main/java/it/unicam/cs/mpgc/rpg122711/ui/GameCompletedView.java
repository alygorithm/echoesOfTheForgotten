package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameCompletedView {
    private final VBox root = new VBox(20);

    public GameCompletedView(GameFlow gameFlow) {
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Label title = new Label("FINE");
        title.setId("title");

        Label text = new Label(
                """
                        Il tuo viaggio è giunto al termine, l'ultimo passo è stato consumato sul
                        sentiero del silenzio.
                        
                        Ma la fine non è oblio: le memorie strappate al nulla e raccolte lungo il cammino
                        non svaniranno con te.
                        
                        Continueranno a fluttuare nell'oscurità, come sussurri immortali destinati
                        a riecheggiare ben oltre l'orlo dell'ultima, definitiva pagina della storia.
                   """
        );

        text.setWrapText(true);

        Button menuButton = new Button("Torna al menù principale");
        menuButton.setOnAction( e -> gameFlow.showMainMenu());

        root.getChildren().addAll(
                title,
                text,
                menuButton
        );
    }

    public Parent getRoot() {
        return root;
    }
}
