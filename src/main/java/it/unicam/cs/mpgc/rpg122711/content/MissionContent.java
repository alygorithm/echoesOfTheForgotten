package it.unicam.cs.mpgc.rpg122711.content;
import it.unicam.cs.mpgc.rpg122711.domain.mission.*;
import java.util.List;

public class MissionContent {

    public static Mission firstMission() {

        MissionStep end = new MissionStep("""
            Le rovine si dissolvono nella memoria del mondo.
            Qualcosa è stato risvegliato.
        """, List.of());

        MissionStep step2 = new MissionStep("""
            Le pareti sono coperte da simboli magici dimenticati.
            Vibrano leggermente al tuo passaggio.
        """, List.of(
                new MissionChoice(
                        "Analizza i simboli",
                        MissionEffect::analyzedRuins,
                        end
                ),
                new MissionChoice(
                        "Ignora e prosegui",
                        MissionEffect::ignoredRuins,
                        end
                )
        ));

        MissionStep start = new MissionStep("""
            Un villaggio silenzioso appare davanti a te.
            Nessuna voce. Nessun movimento.
            Solo memoria sospesa nell’aria.
        """, List.of(
                new MissionChoice(
                        "Esplora le rovine",
                        MissionEffect::enteredRuins,
                        step2
                ),
                new MissionChoice(
                        "Entrare nella chiesa abbandonata",
                        MissionEffect::enteredChurch,
                        end
                )
        ));

        return new Mission("La Rovina Silente", start, 50);
    }
}