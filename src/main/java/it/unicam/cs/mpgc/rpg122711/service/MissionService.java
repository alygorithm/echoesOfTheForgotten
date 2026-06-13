package it.unicam.cs.mpgc.rpg122711.service;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;

/**
 * Servizio logico utilizzato per la gestione degli effetti di completamento delle missioni.
 * Permette di applicare le ricompense maturate (exp) al ersonaggio isolando
 * la logica di reward dal modello dei dati.
 */
public class MissionService {

    public void completeMission(Player player, Mission mission) {

        if (player == null || mission == null) return;

        applyExperienceReward(player, mission);
    }

    private void applyExperienceReward(Player player, Mission mission) {
        player.gainExperience(mission.getRewardExperience());
    }
}