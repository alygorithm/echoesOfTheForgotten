package it.unicam.cs.mpgc.rpg122711.domain;

public class Player {
    private final String name;
    private final CharacterClass characterClass;

    private int level;
    private int experience;

    private int hp;
    private int mana;

    public Player(String name, CharacterClass characterClass) {
        this.name = name;
        this.characterClass = characterClass;

        this.level = 1;
        this.experience = 0;

        // base stats differenziate per classe
        switch (characterClass) {
            case ARCANIST -> {
                this.hp = 80;
                this.mana = 120;
            }
            case WANDERER -> {
                this.hp = 100;
                this.mana = 80;
            }
            case SCHOLAR -> {
                this.hp = 70;
                this.mana = 140;
            }
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

    public String getName() {
        return name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return mana;
    }
}
