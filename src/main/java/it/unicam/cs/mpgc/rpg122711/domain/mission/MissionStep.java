package it.unicam.cs.mpgc.rpg122711.domain.mission;
import java.util.List;


/**
 * Rappresenta un singolo nodo narrativo all'interno di una missione.
 * Contiene il testo descrittivo dello scenario corrente e l'elenco delle scelte possibili.
 */
public class MissionStep {

    private final String text;
    private final List<MissionChoice> choices;

    public MissionStep(String text, List<MissionChoice> choices) {
        this.text = text;
        this.choices = choices;
    }

    public String getText() {
        return text;
    }

    public List<MissionChoice> getChoices() {
        return choices;
    }

    public boolean hasChoices() {
        return choices != null && !choices.isEmpty();
    }
}