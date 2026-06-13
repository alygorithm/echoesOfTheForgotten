package it.unicam.cs.mpgc.rpg122711.content;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionStep;

/**
 * Interfaccia per la creazione dinamica di step di missione personalizzati
 * in base alla classe specifica del personaggio (Guerriero, Mago, ecc.).
 */
public interface ClassStepBuilder {
    MissionStep build(Player player, MissionStep end);
}