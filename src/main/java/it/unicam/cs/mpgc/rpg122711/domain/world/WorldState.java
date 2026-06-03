package it.unicam.cs.mpgc.rpg122711.domain.world;

public class WorldState {

    private int year = 1024;
    private int instability = 0;

    public void advanceTime() {
        year++;
    }

    public void increaseInstability(int value) {
        instability += value;
    }

    public int getYear() {
        return year;
    }

    public int getInstability() {
        return instability;
    }
}