package it.unicam.cs.mpgc.rpg122711.persistence;

import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;

import java.util.List;

/**
 * DTO di persistenza del gioco.
 *
 * Responsabilità (SRP):
 * - rappresentare lo stato serializzabile del gioco
 * - fungere da struttura di trasferimento tra runtime e storage
 *
 * Nota architetturale:
 * Questa classe NON contiene logica di dominio.
 */
public class SaveData {

    public PlayerData player;
    public ProgressData progress;
    public WorldData world;
    public List<EventType> events;

    /*
     * Stato del giocatore serializzato.
     */
    public static class PlayerData {
        public String name;
        public CharacterClass characterClass;
        public int level;
        public int experience;
        public int hp;
        public int mana;
    }

    /*
     * Stato della progressione narrativa.
     */
    public static class ProgressData {
        public int currentMissionIndex;
    }

    /*
     * Stato del mondo di gioco.
     */
    public static class WorldData {
        public int year;
        public int instability;
    }
}