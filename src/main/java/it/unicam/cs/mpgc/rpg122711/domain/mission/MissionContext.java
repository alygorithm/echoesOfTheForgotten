package it.unicam.cs.mpgc.rpg122711.domain.mission;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;

/**
 * Contesto operativo che va ad aggregare e fornire i riferimenti ai sistemi di gioco
 * (giocatore + servizi del mondo) necessari per applicare gli effetti delle scelte.
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