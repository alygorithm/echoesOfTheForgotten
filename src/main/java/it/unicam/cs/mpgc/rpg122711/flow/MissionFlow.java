package it.unicam.cs.mpgc.rpg122711.flow;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;

/**
 * Gestisce la progressione delle missioni.
 * Responsabilità: controllare stato e avanzamento del flusso narrativo.
 */
public interface MissionFlow {
    Mission build(Player player);
    void next();
    boolean isFinished();
    void reset();
}