package it.unicam.cs.mpgc.rpg122711.service;

import it.unicam.cs.mpgc.rpg122711.domain.world.*;

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
        memory.record(event);
        state.increaseInstability(1);
    }

    public void advanceAfterMission() {
        state.advanceTime();
    }


}