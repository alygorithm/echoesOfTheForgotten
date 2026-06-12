package it.unicam.cs.mpgc.rpg122711.content;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionStep;

/**
 * Definisce il contratto per la creazione di uno step
 * specifico in base alla classe del giocatore.
 */
public interface ClassStepBuilder {
    /**
     * @param player -> giocatore corrente
     * @param end -> step successivo della missione
     * @return step personalizzato
     */
    MissionStep build(Player player, MissionStep end);
}
