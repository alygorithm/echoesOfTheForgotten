package it.unicam.cs.mpgc.rpg122711.flow;

import it.unicam.cs.mpgc.rpg122711.content.MissionContent;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;

/*
 * Implementazione concreta del flusso missioni.
 *
 * Responsabilità (SRP):
 * - gestire lo stato di avanzamento delle missioni
 * - costruire la missione corrente in base all'indice
 *
 * Nota architetturale:
 * Il flusso è lineare e hardcoded tramite MissionContent.
 * Questo è accettabile per un RPG narrativo a struttura fissa.
 */
public class MissionManager implements MissionFlow {

    private static final int LAST_MISSION = 7;

    private int currentMissionIndex = 1;

    @Override
    public Mission build(Player player) {
        return switch (currentMissionIndex) {
            case 1 -> MissionContent.firstMission(player);
            case 2 -> MissionContent.secondMission(player);
            case 3 -> MissionContent.thirdMission(player);
            case 4 -> MissionContent.fourthMission(player);
            case 5 -> MissionContent.fifthMission(player);
            case 6 -> MissionContent.sixthMission(player);
            case 7 -> MissionContent.seventhMission(player);
            default -> null;
        };
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
}