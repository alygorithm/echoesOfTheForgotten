package it.unicam.cs.mpgc.rpg122711.domain;

/**
 * Definisce le classi selezionabili dal giocatore per il proprio personaggio,
 * associando a ciacun "tipo" una stringa descrittiva per l'interfaccia.
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