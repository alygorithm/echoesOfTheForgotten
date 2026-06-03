package it.unicam.cs.mpgc.rpg122711.domain;

public enum CharacterClass {
    ARCANIST("Arcano"), // -> magia pura, memoria, studio
    WANDERER("Viandante"), // -> bilanciato, esplorazione
    SCHOLAR("Studioso"); // --> debole in combat, forte in magia/analisi

    private final String label;

    CharacterClass(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
