package it.unicam.cs.mpgc.rpg122711.domain.mission;

public class Mission {

    private final String title;
    private final MissionStep startStep;
    private final int rewardExperience;

    public Mission(String title, MissionStep startStep, int rewardExperience) {
        this.title = title;
        this.startStep = startStep;
        this.rewardExperience = rewardExperience;
    }

    public String getTitle() {
        return title;
    }

    public MissionStep getStartStep() {
        return startStep;
    }

    public int getRewardExperience() {
        return rewardExperience;
    }
}