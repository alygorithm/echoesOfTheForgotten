package it.unicam.cs.mpgc.rpg122711.domain.mission;

import java.util.function.Consumer;

/*
 * Rappresenta una scelta narrativa.
 * Incapsula effetto e transizione tra stati narrativi.
 */
public class MissionChoice {

    private final String label;
    private final Consumer<MissionContext> effect;
    private final MissionStep nextStep;

    public MissionChoice(String label,
                         Consumer<MissionContext> effect,
                         MissionStep nextStep) {
        this.label = label;
        this.effect = effect;
        this.nextStep = nextStep;
    }

    public String getLabel() {
        return label;
    }

    public MissionStep getNextStep() {
        return nextStep;
    }

    public void apply(MissionContext ctx) {
        if (effect != null) {
            effect.accept(ctx);
        }
    }
}