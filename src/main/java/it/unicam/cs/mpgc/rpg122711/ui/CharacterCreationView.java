package it.unicam.cs.mpgc.rpg122711.ui;

import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CharacterCreationView {
    private final GameFlow gameFlow;
    private final VBox root;

    public CharacterCreationView(GameFlow gameFlow) {
        this.gameFlow = gameFlow;
        this.root = new VBox(15);

        buildUI();
    }

    private void buildUI() {
        root.setAlignment(Pos.CENTER);
        root.setId("creationView");
        root.setStyle("""
            -fx-background-color: #1b1d24;
        """);

        // titolo
        Label title = new Label("Creazione Personaggio");
        title.setStyle("""
            -fx-font-size: 28px;
            -fx-text-fill: #e6e5e0;
            -fx-font-weight: bold;
        """);

        // input name
        TextField nameField = new TextField();
        nameField.setPromptText("Nome del viaggiatore");
        nameField.setMaxWidth(260);

        // selezione della classe
        ComboBox<CharacterClass> classBox = new ComboBox<>();
        classBox.getItems().addAll(CharacterClass.values());
        classBox.setValue(CharacterClass.WANDERER);
        classBox.setMaxWidth(260);

        // descrizione della classe
        Label classDescription = new Label();
        classDescription.setWrapText(true);
        classDescription.setMaxWidth(300);
        classDescription.setStyle("""
            -fx-text-fill: #b8b8b8;
            -fx-font-size: 13px;
        """);

        updateClassDescription(classDescription, classBox.getValue());

        classBox.setOnAction(e ->
                updateClassDescription(classDescription, classBox.getValue())
        );

        // pulsanti
        Button createBtn = new Button("Inizia Viaggio");

        createBtn.setStyle("""
            -fx-background-color: #3a3f4b;
            -fx-text-fill: #e6e5e0;
            -fx-padding: 10 22;
            -fx-background-radius: 6;
        """);

        createBtn.setOnMouseEntered(e ->
                createBtn.setStyle("""
                    -fx-background-color: #4a5160;
                    -fx-text-fill: #ffffff;
                    -fx-padding: 10 22;
                    -fx-background-radius: 6;
                """)
        );

        createBtn.setOnMouseExited(e ->
                createBtn.setStyle("""
                    -fx-background-color: #3a3f4b;
                    -fx-text-fill: #e6e5e0;
                    -fx-padding: 10 22;
                    -fx-background-radius: 6;
                """)
        );

        // errori
        Label result = new Label();
        result.setStyle("-fx-text-fill: #c96a6a;");

        // azioni
        createBtn.setOnAction(e -> {

            String name = nameField.getText();
            CharacterClass selectedClass = classBox.getValue();

            if (name == null || name.isBlank()) {
                result.setText("Inserisci un nome valido.");
                return;
            }

            Player player = new Player(name, selectedClass);

            gameFlow.setPlayer(player);
            gameFlow.showGameStart();
        });

        // layout
        root.getChildren().addAll(
                title,
                nameField,
                classBox,
                classDescription,
                createBtn,
                result
        );
    }

    private void updateClassDescription(Label label, CharacterClass type) {
        switch (type) {

            case ARCANIST -> label.setText(
                    "ARCANO\n" +
                            "Studioso della memoria e della magia antica.\n" +
                            "✔ Bonus: eventi nascosti\n✔ Maggiore XP da analisi\n✔ Visioni del passato"
            );

            case WANDERER -> label.setText(
                    "VIANDANTE\n" +
                            "Viaggiatore equilibrato tra esplorazione e sopravvivenza.\n" +
                            "✔ Scelte bilanciate\n✔ Meno penalità\n✔ Accesso standard alle missioni"
            );

            case SCHOLAR -> label.setText(
                    "STUDIOSO\n" +
                            "Interprete della memoria del mondo.\n" +
                            "✔ Bonus XP da investigazione\n✔ Può alterare eventi\n✔ Sblocca finali alternativi"
            );
        }
    }

    public VBox getRoot() {
        return root;
    }
}