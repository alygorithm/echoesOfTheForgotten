package it.unicam.cs.mpgc.rpg122711.domain.mission;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;

/*
 * Contesto operativo delle missioni.
 * Fornisce accesso controllato ai sistemi di gioco.
 */
public class MissionContext {

    private final Player player;
    private final WorldService worldService;

    public MissionContext(Player player, WorldService worldService) {
        this.player = player;
        this.worldService = worldService;
    }

    public Player getPlayer() {
        return player;
    }

    public WorldService getWorldService() {
        return worldService;
    }
}