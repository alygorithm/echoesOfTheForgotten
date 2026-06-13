package it.unicam.cs.mpgc.rpg122711.flow;

import it.unicam.cs.mpgc.rpg122711.content.MissionContent;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;

public class MissionManager implements MissionFlow {

    private static final int LAST_MISSION = 7;

    private int currentMissionIndex = 1;

    @Override
    public Mission build(Player player) {
        if (isFinished()) {
            return null;
        }
        return MissionContent.getMission(currentMissionIndex, player);
    }

    @Override
    public void next() {
        currentMissionIndex++;
    }

    @Override
    public boolean isFinished() {
        return currentMissionIndex > LAST_MISSION;
    }

    @Override
    public void reset() {
        currentMissionIndex = 1;
    }

    public int getCurrentMissionIndex() {
        return currentMissionIndex;
    }

    public void setCurrentMissionIndex(int index) {
        this.currentMissionIndex = index;
    }

    public String getMissionName(int index, Player contextPlayer) {
        try {
            Mission temp = MissionContent.getMission(index, contextPlayer);
            if (temp != null) {
                return temp.getTitle();
            }
        } catch (Exception e) {
            return "Missione " + index;
        }
        return "Missione " + index;
    }
}