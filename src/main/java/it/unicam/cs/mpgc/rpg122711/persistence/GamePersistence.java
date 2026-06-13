package it.unicam.cs.mpgc.rpg122711.persistence;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import it.unicam.cs.mpgc.rpg122711.flow.MissionManager;

/**
 * Interfaccia per la gestione della persistenza dello stato di gioco.
 * Definisce tutti i contratti per estrarre i dati correnti in un oggetto di salvataggio
 * e di ripristinare lo stato dei vari sistemi a partire da dati persistenti.
 */
public interface GamePersistence {
    SaveData save(Player player, MissionManager mm, WorldService ws);
    void load(SaveData data, MissionManager mm, WorldService ws);
}