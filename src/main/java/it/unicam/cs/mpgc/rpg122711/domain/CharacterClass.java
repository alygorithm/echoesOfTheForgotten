package it.unicam.cs.mpgc.rpg122711.domain;

/*
 * Enum che rappresenta le classi del giocatore.
 * Responsabilità: definire il tipo di personaggio (SRP).
 */
public enum CharacterClass {
    ARCANIST("Arcano"),
    WANDERER("Viandante"),
    SCHOLAR("Studioso");

    private final String label;

    CharacterClass(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}