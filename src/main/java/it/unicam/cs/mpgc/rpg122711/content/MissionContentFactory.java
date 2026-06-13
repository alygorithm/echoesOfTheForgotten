package it.unicam.cs.mpgc.rpg122711.content;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;

/**
 * Interfaccia factory creata per l'istanziamento di una specifica missione,
 * configurata in base allo stato e/o caratteristiche del giocatore.
 */
public interface MissionContentFactory {
    Mission create(Player player);
}