package it.unicam.cs.mpgc.rpg122711.service;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;

public class MissionService {

    public void completeMission(it.unicam.cs.mpgc.rpg122711.domain.Player player, Mission mission) {
        player.gainExperience(mission.getRewardExperience());
    }
}
