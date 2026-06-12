package it.unicam.cs.mpgc.rpg122711.service;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;

/**
 * Servizio responsabile dell'applicazione degli effetti
 * derivanti dal completamento di una missione.
 *
 * Responsabilità (SRP):
 * - applicazione reward al player
 *
 * Nota:
 * Attualmente gestisce solo esperienza, ma è estendibile
 * a ulteriori effetti (OCP-friendly).
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