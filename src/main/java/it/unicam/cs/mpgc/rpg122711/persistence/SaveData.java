package it.unicam.cs.mpgc.rpg122711.persistence;
import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import java.util.List;

/**
 * DTO che rappresenta la struttura serializzabile di un salvataggio.
 * Permette di organizzare in "sotto-strutture" i dati del personaggio,
 * la progressione narrativa e lo stato globale del mondo.
 */
public class SaveData {

    public PlayerData player;
    public ProgressData progress;
    public WorldData world;
    public List<EventType> events;

    public static class PlayerData {
        public String name;
        public CharacterClass characterClass;
        public int level;
        public int experience;
        public int hp;
        public int mana;
    }

    public static class ProgressData {
        public int currentMissionIndex;
    }

    public static class WorldData {
        public int year;
        public int instability;
    }
}