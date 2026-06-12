package it.unicam.cs.mpgc.rpg122711.service;

import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import it.unicam.cs.mpgc.rpg122711.domain.world.WorldMemory;
import it.unicam.cs.mpgc.rpg122711.domain.world.WorldState;

/**
 * Servizio di coordinamento del mondo di gioco.
 *
 * Responsabilità (SRP):
 * - gestione dello stato globale del mondo
 * - gestione della memoria degli eventi
 * - applicazione delle regole base di evoluzione del mondo
 *
 * Nota:
 * Le regole sono volutamente semplici ma centralizzate
 * per mantenere controllo sulla narrativa globale.
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