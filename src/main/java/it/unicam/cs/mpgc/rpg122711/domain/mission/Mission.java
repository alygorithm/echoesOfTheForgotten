package it.unicam.cs.mpgc.rpg122711.domain.mission;

/**
 * Modello che definisce la struttura ed i dati di una missione.
 * Sono inclusi: titolo, step iniziale, ricompensa exp e la "chiave di sfondo".
 */
public class Mission {

    private final String title;
    private final MissionStep startStep;
    private final int rewardExperience;

    private final String backgroundKey;

    public Mission(String title, MissionStep startStep, int rewardExperience, String backgroundKey) {
        this.title = title;
        this.startStep = startStep;
        this.rewardExperience = rewardExperience;
        this.backgroundKey = backgroundKey;
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

    public String getBackgroundKey() {
        return backgroundKey;
    }
}