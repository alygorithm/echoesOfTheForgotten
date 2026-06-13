package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Vista JavaFX dedicata alla schermata di creazione del personaggio.
 * Creata per gestire l'inserimento del nome, la selezione della classe con aggiornamento dinamico
 * della descrizione + inizializzazione del Player nel flusso di gioco.
 */
public class CharacterCreationView {

    private final GameFlow gameFlow;
    private final VBox root;

    public CharacterCreationView(GameFlow gameFlow) {
        this.gameFlow = gameFlow;
        this.root = new VBox(30);
        buildUI();
    }

    private void buildUI() {
        root.setAlignment(Pos.CENTER);
        root.setId("creationView");
        root.setPadding(new Insets(40));

        Label title = new Label("CREAZIONE PERSONAGGIO");
        title.getStyleClass().add("creation-title");

        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(360);
        formBox.getStyleClass().add("creation-box");

        TextField nameField = new TextField();
        nameField.setPromptText("Nome del viaggiatore...");
        nameField.getStyleClass().add("creation-input");
        nameField.setMaxWidth(280);

        ComboBox<CharacterClass> classBox = new ComboBox<>();
        classBox.getStyleClass().add("menu-btn");
        classBox.getItems().addAll(CharacterClass.values());
        classBox.setValue(CharacterClass.WANDERER);
        classBox.setMaxWidth(280);

        Label classDescription = new Label();
        classDescription.getStyleClass().add("class-desc-text");
        classDescription.setWrapText(true);
        classDescription.setMaxWidth(280);
        classDescription.setMinHeight(130);
        classDescription.setAlignment(Pos.TOP_LEFT);

        updateClassDescription(classDescription, classBox.getValue());

        classBox.setOnAction(e ->
                updateClassDescription(classDescription, classBox.getValue())
        );

        Button createBtn = new Button("Inizia Viaggio");
        createBtn.getStyleClass().add("menu-btn");
        createBtn.setPrefWidth(200);
        createBtn.setPrefHeight(45);

        Label result = new Label();
        result.getStyleClass().add("error-label");

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

        formBox.getChildren().addAll(
                nameField,
                classBox,
                classDescription,
                createBtn,
                result
        );

        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        root.getChildren().addAll(topSpacer, title, formBox, bottomSpacer);
    }

    private void updateClassDescription(Label label, CharacterClass type) {
        if (type == null) return;

        switch (type) {
            case ARCANIST -> label.setText(
                    "ARCANO\n" +
                            "Studioso della memoria e della magia antica.\n\n" +
                            "✔ Bonus: eventi nascosti\n" +
                            "✔ Maggiore XP da analisi\n" +
                            "✔ Visioni del passato"
            );
            case WANDERER -> label.setText(
                    "VIANDANTE\n" +
                            "Viaggiatore equilibrato tra esplorazione e sopravvivenza.\n\n" +
                            "✔ Scelte bilanciate\n" +
                            "✔ Meno penalità\n" +
                            "✔ Accesso standard alle missioni"
            );
            case SCHOLAR -> label.setText(
                    "STUDIOSO\n" +
                            "Interprete della memoria del mondo.\n\n" +
                            "✔ Bonus XP da investigazione\n" +
                            "✔ Può alterare eventi\n" +
                            "✔ Sblocca finali alternativi"
            );
        }
    }

    public VBox getRoot() {
        return root;
    }
}