package it.unicam.cs.mpgc.rpg122711.domain;

/*
 * Entità che rappresenta il giocatore.
 *
 * Responsabilità (SRP):
 * - mantenere lo stato del player (HP, mana, livello, esperienza)
 * - gestire la progressione base (level up)
 *
 * Nota:
 * Questa classe è una domain entity e contiene logica minima
 * legata esclusivamente al modello del personaggio.
 */
public class Player {

    private final String name;
    private final CharacterClass characterClass;

    private int level = 1;
    private int experience = 0;

    private int hp;
    private int mana;

    public Player(String name, CharacterClass characterClass) {
        this.name = name;
        this.characterClass = characterClass;

        switch (characterClass) {
            case ARCANIST -> { hp = 80; mana = 120; }
            case WANDERER -> { hp = 100; mana = 80; }
            case SCHOLAR -> { hp = 70; mana = 140; }
        }
    }

    public void gainExperience(int amount) {
        experience += amount;

        if (experience >= 100) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        experience = 0;
        hp += 10;
        mana += 15;
    }

    public String getName() { return name; }
    public CharacterClass getCharacterClass() { return characterClass; }
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getHp() { return hp; }
    public int getMana() { return mana; }
}