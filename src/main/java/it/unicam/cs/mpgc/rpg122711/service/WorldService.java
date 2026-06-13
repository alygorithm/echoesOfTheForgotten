package it.unicam.cs.mpgc.rpg122711.service;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import it.unicam.cs.mpgc.rpg122711.domain.world.WorldMemory;
import it.unicam.cs.mpgc.rpg122711.domain.world.WorldState;

/**
 * Servizio di coordinamento e gestione dello stato del mondo di gioco.
 * Centralizza la registrazione degli eventi narrativi, l'evoluzione temporale
 * e l'applicazione delle regole globali come il calcolo dell'instabilità.
 */
public class WorldService {

    private final WorldState state = new WorldState();
    private final WorldMemory memory = new WorldMemory();

    public WorldState getState() {
        return state;
    }

    public WorldMemory getMemory() {
        return memory;
    }

    public void register(EventType event) {
        recordEvent(event);
        applyDefaultInstabilityRule(event);
    }

    private void recordEvent(EventType event) {
        memory.record(event);
    }

    private void applyDefaultInstabilityRule(EventType event) {
        state.increaseInstability(1);
    }

    public void advanceAfterMission() {
        state.advanceTime();
    }
}