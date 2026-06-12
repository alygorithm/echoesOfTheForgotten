package it.unicam.cs.mpgc.rpg122711.persistence;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import it.unicam.cs.mpgc.rpg122711.flow.MissionManager;

/*
 * Contratto per la persistenza dello stato di gioco.
 *
 * Responsabilità (SRP):
 * - salvare lo stato del gioco
 * - ripristinare stato di mondo e progressione
 *
 * Nota:
 * La ricostruzione del Player NON è responsabilità della persistence,
 * ma del GameFlow (orchestratore della sessione).
 */
public interface GamePersistence {
    SaveData save(Player player, MissionManager mm, WorldService ws);
    void load(SaveData data, MissionManager mm, WorldService ws);
}