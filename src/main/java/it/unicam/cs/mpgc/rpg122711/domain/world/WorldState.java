package it.unicam.cs.mpgc.rpg122711.domain.world;

/**
 * Va a gestire lo stato globale + le variabili ambientali del mondo di gioco,
 * lo fa tracciando l'avanzamento cronologico del tempo ed il livello di instabilità narrativa (funzionalità non del tutto implementata).
 */
public class WorldState {

    private int year = 1024;
    private int instability = 0;

    public void advanceTime() {
        year++;
    }

    public void increaseInstability(int value) {
        if (value > 0) {
            instability += value;
        }
    }

    public int getYear() {
        return year;
    }

    public int getInstability() {
        return instability;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setInstability(int instability) {
        this.instability = instability;
    }
}