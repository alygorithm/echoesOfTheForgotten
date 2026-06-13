package it.unicam.cs.mpgc.rpg122711.content;
import it.unicam.cs.mpgc.rpg122711.content.missions.*;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import java.util.HashMap;
import java.util.Map;

/**
 * Registro centrale e factory per la gestione delle varie missioni di gioco (presenti nel package "missions").
 * Mappa gli identificativi numerici ai rispettivi creatori di contenuto,
 * restituendo così la missione corretta istanziata in base al giocatore.
 */
public class MissionContent {

    private static final Map<Integer, MissionContentFactory> MISSIONS = new HashMap<>();

    static {
        MISSIONS.put(1, new FirstMissionFactory());
        MISSIONS.put(2, new SecondMissionFactory());
        MISSIONS.put(3, new ThirdMissionFactory());
        MISSIONS.put(4, new FourthMissionFactory());
        MISSIONS.put(5, new FifthMissionFactory());
        MISSIONS.put(6, new SixthMissionFactory());
        MISSIONS.put(7, new SeventhMissionFactory());
    }

    public static Mission getMission(int id, Player player) {
        MissionContentFactory factory = MISSIONS.get(id);
        if (factory != null) {
            return factory.create(player);
        }
        throw new IllegalArgumentException("Missione numero " + id + " non trovata nel catalogo!");
    }
}