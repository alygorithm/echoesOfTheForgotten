package it.unicam.cs.mpgc.rpg122711.domain.world;

import java.util.LinkedHashSet;
import java.util.Set;

public class WorldMemory {

    private final Set<EventType> events = new LinkedHashSet<>();

    public void record(EventType event) {
        events.add(event);
    }

    public boolean contains(EventType event) {
        return events.contains(event);
    }

    public Set<EventType> getAll() {
        return events;
    }
}