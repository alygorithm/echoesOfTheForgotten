package it.unicam.cs.mpgc.rpg122711.content;
import it.unicam.cs.mpgc.rpg122711.domain.world.WorldState;

public class WorldContent {
    public static String getIntro(WorldState state) {
        return """
                MONDO DI ERYNDOR
                Anno: """ + state.getYear() + """
                
                Le terre di Eryndor sono ciò che resta di un continente antico.
                Le guerre tra regni e magia hanno lasciato solo distruzione e rovina.
                La memoria del mondo è instabile: """ + state.getInstability() + """
                
                Le persone non ricordano più la vera storia.
                Ricordano solo frammenti... memorie spezzate.
                
                Da qui inizia il tuo viaggio.
                """;
    }
}
