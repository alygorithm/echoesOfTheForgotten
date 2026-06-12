package it.unicam.cs.mpgc.rpg122711.domain.world;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Memoria eventi del mondo.
 * Responsabilità: gestione eventi narrativi.
 */
public class WorldMemory {

    private final Set<EventType> events = new LinkedHashSet<>();

    public void record(EventType event) {
        if (event != null) {
            events.add(event);
        }
    }

    public boolean contains(EventType event) {
        return events.contains(event);
    }

    public Set<EventType> getAll() {
        return Collections.unmodifiableSet(events);
    }

    public void setAll(Set<EventType> newEvents) {
        events.clear();
        if (newEvents != null) {
            events.addAll(newEvents);
        }
    }
}