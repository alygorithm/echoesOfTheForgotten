package it.unicam.cs.mpgc.rpg122711.ui;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.*;
import it.unicam.cs.mpgc.rpg122711.flow.GameFlow;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Interfaccia grafica principale del gioco.
 * Permette di gestire l'aggiornamento dinamico del testo della storia,
 * dei bivi di scelta e del pannello delle statistiche in tempo reale e
 * dei pulsanti di salvataggio + caricamento + ritorno al menù princiaple.
 */
import java.util.List;

public class GameView {

    private final GameFlow gameFlow;
    private final Player player;
    private final WorldService worldService;

    private final StackPane root = new StackPane();
    private final BorderPane layout = new BorderPane();

    private Mission currentMission;

    private final TextArea storyArea = new TextArea();
    private final TextArea statsArea = new TextArea();
    private final VBox choicesBox = new VBox(10);

    private final ImageView background = new ImageView();

    public GameView(GameFlow gameFlow) {
        this.gameFlow = gameFlow;
        this.player = gameFlow.getPlayer();
        this.worldService = gameFlow.getWorldService();

        buildUI();
        setupBackgroundLayer();

        loadMission();
        refreshUI();
    }

    private void buildUI() {
        Button saveBtn = new Button("Salva");
        Button loadBtn = new Button("Carica");
        Button menuBtn = new Button("Menu");

        saveBtn.getStyleClass().add("menu-btn");
        loadBtn.getStyleClass().add("menu-btn");
        menuBtn.getStyleClass().add("menu-btn");

        saveBtn.setStyle("-fx-font-size: 13px; -fx-padding: 6 15;");
        loadBtn.setStyle("-fx-font-size: 13px; -fx-padding: 6 15;");
        menuBtn.setStyle("-fx-font-size: 13px; -fx-padding: 6 15;");

        saveBtn.setOnAction(e -> saveGame());
        loadBtn.setOnAction(e -> loadGame());
        menuBtn.setOnAction(e -> gameFlow.showMainMenu());

        HBox topBar = new HBox(12, saveBtn, loadBtn, menuBtn);
        topBar.setPadding(new Insets(10, 15, 5, 15));

        storyArea.setWrapText(true);
        storyArea.setEditable(false);
        storyArea.getStyleClass().add("mission-text");
        storyArea.setPrefWidth(700);

        statsArea.setWrapText(true);
        statsArea.setEditable(false);
        statsArea.getStyleClass().add("stats-panel");
        statsArea.setPrefWidth(165); // Larghezza riequilibrata e leggermente allargata

        choicesBox.getStyleClass().add("choice-box");
        choicesBox.setFillWidth(true);

        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(choicesBox);
        bottomPane.setStyle("-fx-background-color: rgba(20,20,25,0.75); -fx-background-radius: 6;");

        layout.setTop(topBar);
        layout.setCenter(storyArea);
        layout.setRight(statsArea);
        layout.setBottom(bottomPane);

        BorderPane.setMargin(storyArea, new Insets(10, 5, 10, 15));
        BorderPane.setMargin(statsArea, new Insets(10, 15, 10, 5));
        BorderPane.setMargin(bottomPane, new Insets(5, 15, 15, 15));

        root.getChildren().addAll(background, layout);
    }

    private void setupBackgroundLayer() {
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(root.widthProperty());
        background.fitHeightProperty().bind(root.heightProperty());
    }

    private void setBackground(String key) {
        String path = switch (key) {
            case "start" -> "/images/start.png";
            case "foresta" -> "/images/foresta.png";
            case "santuario" -> "/images/santuario.png";
            case "custode" -> "/images/custode.png";
            case "biblioteca" -> "/images/biblioteca.png";
            case "processo" -> "/images/processo.png";
            case "finale" -> "/images/finale.png";
            default -> "/images/start.png";
        };

        background.setImage(
                new Image(getClass().getResource(path).toExternalForm())
        );
    }

    private void saveGame() {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, 1, 2, 3);
        dialog.setTitle("Salvataggio");
        dialog.setHeaderText("Seleziona slot");
        dialog.setContentText("Slot:");

        dialog.showAndWait().ifPresent(slot -> {
            if (gameFlow.saveExists(slot)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma sovrascrittura");
                alert.setHeaderText("Slot già occupato");
                alert.setContentText("Lo slot " + slot + " contiene già un salvataggio.\n\nVuoi sovrascriverlo?");

                alert.showAndWait().ifPresent(result -> {
                    if (result == ButtonType.OK) {
                        gameFlow.executeSave(slot);
                    }
                });
            } else {
                gameFlow.executeSave(slot);
            }
        });
    }

    private void loadGame() {
        List<Integer> occupiedSlots = gameFlow.getOccupiedSlots();

        if (occupiedSlots.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Caricamento");
            alert.setHeaderText("Nessun salvataggio trovato");
            alert.setContentText("Non ci sono partite salvate da poter caricare.");
            alert.showAndWait();
            return;
        }

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(occupiedSlots.get(0), occupiedSlots);
        dialog.setTitle("Caricamento");
        dialog.setHeaderText("Seleziona la partita da caricare");
        dialog.setContentText("Slot occupati:");

        dialog.showAndWait().ifPresent(slot -> {
            Alert askSaveAlert = new Alert(Alert.AlertType.CONFIRMATION);
            askSaveAlert.setTitle("Salvataggio preventivo");
            askSaveAlert.setHeaderText("Vuoi salvare la partita attuale?");
            askSaveAlert.setContentText("I progressi correnti andranno persi se non salvi prima di caricare.");

            ButtonType btnSi = new ButtonType("Sì, salva e carica");
            ButtonType btnNo = new ButtonType("No, carica direttamente");
            ButtonType btnAnnulla = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
            askSaveAlert.getButtonTypes().setAll(btnSi, btnNo, btnAnnulla);

            askSaveAlert.showAndWait().ifPresent(response -> {
                if (response == btnAnnulla) return;

                if (response == btnSi) {
                    if (gameFlow.hasSlotSelected()) {
                        gameFlow.executeSave(gameFlow.getCurrentSlot());
                    } else {
                        gameFlow.executeSave(1);
                    }
                }

                boolean success = gameFlow.executeLoad(slot);
                if (success) {
                    loadMission();
                    refreshUI();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Errore");
                    errorAlert.setHeaderText("Impossibile caricare");
                    errorAlert.setContentText("Si è verificato un problema nel caricamento dello slot " + slot);
                    errorAlert.showAndWait();
                }
            });
        });
    }

    private void loadMission() {
        currentMission = gameFlow.getCurrentMission();
        if (currentMission == null) return;

        setBackground(currentMission.getBackgroundKey());
        loadStep(currentMission.getStartStep());
    }

    private void loadStep(MissionStep step) {
        storyArea.setText(step.getText());
        choicesBox.getChildren().clear();

        List<MissionChoice> choices = step.getChoices();

        if (choices == null || choices.isEmpty()) {
            addContinueButton();
            return;
        }

        for (MissionChoice choice : choices) {
            addChoiceButton(choice);
        }
    }

    private void addContinueButton() {
        Button btn = new Button("Continua");
        btn.getStyleClass().add("menu-btn");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(32);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 4 10;");

        btn.setOnAction(e -> {
            worldService.advanceAfterMission();
            gameFlow.nextMission();
            loadMission();
            refreshUI();
        });

        choicesBox.getChildren().add(btn);
    }

    private void addChoiceButton(MissionChoice choice) {
        Button btn = new Button(choice.getLabel());
        btn.getStyleClass().add("menu-btn");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(32);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 4 10;");

        btn.setOnAction(e -> {
            MissionContext ctx = new MissionContext(player, worldService);
            choice.apply(ctx);

            MissionStep next = choice.getNextStep();

            if (next != null) {
                loadStep(next);
            } else {
                worldService.advanceAfterMission();
                gameFlow.nextMission();
                loadMission();
            }

            refreshUI();
        });

        choicesBox.getChildren().add(btn);
    }

    private void refreshUI() {
        statsArea.setText("""
                ╔══ GIOCATORE ══╗
                
                   %s
                   %s
    
                   HP: %d
                   Mana: %d
                   Livello: %d
                   XP: %d
                
                ╠══   MONDO   ══╣
    
                   Anno: %d
                   Instabilità: %d
                   Eventi: %d
                """.formatted(
                player.getName(),
                player.getCharacterClass().getLabel(),
                player.getHp(),
                player.getMana(),
                player.getLevel(),
                player.getExperience(),
                worldService.getState().getYear(),
                worldService.getState().getInstability(),
                worldService.getMemory().getAll().size()
        ));
    }

    public StackPane getRoot() {
        return root;
    }
}