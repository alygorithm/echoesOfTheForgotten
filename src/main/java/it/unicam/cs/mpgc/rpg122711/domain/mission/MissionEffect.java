package it.unicam.cs.mpgc.rpg122711.domain.mission;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionContext;

public class MissionEffect {
    public static void enteredRuins(MissionContext ctx) {
        ctx.getWorldService().register(EventType.ENTERED_RUINS);
    }

    public static void analyzedRuins(MissionContext ctx) {
        ctx.getWorldService().register(EventType.ANALYZED_RUINS);
        ctx.getPlayer().gainExperience(20);
    }

    public static void ignoredRuins(MissionContext ctx) {
        ctx.getWorldService().register(EventType.IGNORED_RUINS);
    }

    public static void enteredChurch(MissionContext ctx) {
        ctx.getWorldService().register(EventType.ENTERED_CHURCH);
    }
}
