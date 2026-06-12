package it.unicam.cs.mpgc.rpg122711.ui;

import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/*
 * View responsabile della creazione del personaggio.
 *
 * SOLID:
 * - SRP: solo UI + validazione input base
 * - DIP: dipende da GameFlow (controller), non da logica interna
 */
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
        root.setStyle("-fx-background-color: #1b1d24;");

        Label title = new Label("Creazione Personaggio");
        styleTitle(title);

        TextField nameField = new TextField();
        nameField.setPromptText("Nome del viaggiatore");
        nameField.setMaxWidth(260);

        ComboBox<CharacterClass> classBox = new ComboBox<>();
        classBox.getItems().addAll(CharacterClass.values());
        classBox.setValue(CharacterClass.WANDERER);
        classBox.setMaxWidth(260);

        Label classDescription = new Label();
        styleDescription(classDescription);

        updateClassDescription(classDescription, classBox.getValue());

        classBox.setOnAction(e ->
                updateClassDescription(classDescription, classBox.getValue())
        );

        Button createBtn = new Button("Inizia Viaggio");
        styleButton(createBtn);

        Label result = new Label();
        result.setStyle("-fx-text-fill: #c96a6a;");

        /*
         * Azione principale:
         * - validazione input
         * - creazione Player
         * - passaggio al GameFlow
         */
        createBtn.setOnAction(e -> {

            String name = nameField.getText();
            CharacterClass selectedClass = classBox.getValue();

            if (name == null || name.isBlank()) {
                result.setText("Inserisci un nome valido.");
                return;
            }

            if (selectedClass == null) {
                result.setText("Seleziona una classe.");
                return;
            }

            Player player = new Player(name, selectedClass);

            gameFlow.setPlayer(player);
            gameFlow.showGameStart();
        });

        root.getChildren().addAll(
                title,
                nameField,
                classBox,
                classDescription,
                createBtn,
                result
        );
    }

    private void styleTitle(Label title) {
        title.setStyle("""
            -fx-font-size: 28px;
            -fx-text-fill: #e6e5e0;
            -fx-font-weight: bold;
        """);
    }

    private void styleDescription(Label label) {
        label.setWrapText(true);
        label.setMaxWidth(300);
        label.setStyle("""
            -fx-text-fill: #b8b8b8;
            -fx-font-size: 13px;
        """);
    }

    private void styleButton(Button button) {

        button.setStyle("""
            -fx-background-color: #3a3f4b;
            -fx-text-fill: #e6e5e0;
            -fx-padding: 10 22;
            -fx-background-radius: 6;
        """);

        button.setOnMouseEntered(e ->
                button.setStyle("""
                    -fx-background-color: #4a5160;
                    -fx-text-fill: #ffffff;
                    -fx-padding: 10 22;
                    -fx-background-radius: 6;
                """)
        );

        button.setOnMouseExited(e ->
                button.setStyle("""
                    -fx-background-color: #3a3f4b;
                    -fx-text-fill: #e6e5e0;
                    -fx-padding: 10 22;
                    -fx-background-radius: 6;
                """)
        );
    }

    /*
     * Single Responsibility: solo descrizione UI della classe
     */
    private void updateClassDescription(Label label, CharacterClass type) {

        if (type == null) return;

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