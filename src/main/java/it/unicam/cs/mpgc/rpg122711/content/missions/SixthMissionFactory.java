package it.unicam.cs.mpgc.rpg122711.content.missions;
import it.unicam.cs.mpgc.rpg122711.content.ClassStepBuilder;
import it.unicam.cs.mpgc.rpg122711.content.MissionContentFactory;
import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionChoice;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionStep;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SixthMissionFactory implements MissionContentFactory {

    private final Map<CharacterClass, ClassStepBuilder> coreBuilders = new HashMap<>();

    public SixthMissionFactory() {
        initCoreBuilders();
    }

    @Override
    public Mission create(Player player) {
        MissionStep end = new MissionStep(
                """
                Non esiste più una direzione.  Solo un movimento senza origine.
                Senza destinazione.
                
                Il mondo non sta cambiando. Sta perdendo coerenza.
                
                Ogni passo che compi non ti avvicina a qualcosa.
                Ti sottrae qualcosa.
                
                I contorni della realtà iniziano a cedere.
                Prima i suoni. Poi i nomi. Poi il significato delle cose.
                
                E infine comprendi:
                
                Non stai attraversando l’Oblio.
                L’Oblio sta attraversando te.
                
                E in quel momento… smetti di essere un osservatore.
             """,
                List.of()
        );

        MissionStep coreStep = coreBuilders.getOrDefault(
                player.getCharacterClass(),
                (p, e) -> new MissionStep(
                        """
                        La distorsione avvolge ogni tua vecchia certezza. Non provi a
                        sezionare il vuoto, né cerchi di ricondurlo a un senso logico.
                        
                        Ti lasci semplicemente trasportare dal flusso incoerente di
                        questa scomposizione, mentre i confini della tua stessa percezione
                        cominciano a sfilacciarsi.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Accetta il collasso e abbandona la resistenza",
                                        ctx -> ctx.getPlayer().gainExperience(40),
                                        e
                                ),
                                new MissionChoice(
                                        "Ancorati all'ultimo pensiero cosciente",
                                        ctx -> ctx.getPlayer().gainExperience(45),
                                        e
                                )
                        )
                )
        ).build(player, end);

        MissionStep alternativeStartStep = new MissionStep(
                """
                Invece di assecondare passivamente il flusso, cerchi di muoverti in modo
                trasversale rispetto alle linee di scomposizione della realtà.
                
                Lo spazio circostante reagisce al tuo spostamento anomalo generando
                una serie di rifrazioni visive statiche, come istantanee congelate
                dell'intero viaggio che hai compiuto fino a questo istante.
                """,
                List.of(
                        new MissionChoice(
                                "Attraversa le rifrazioni statiche della realtà",
                                ctx -> {},
                                coreStep
                        )
                )
        );

        MissionStep start = new MissionStep(
                """
                Il varco non conduce in un luogo.
                
                Conduce in una condizione.
                
                La Biblioteca è ormai solo un’eco distante,
                come un sogno ricordato male.
                
                Davanti a te, lo spazio perde consistenza.
                Le distanze non sono più misurabili.
                
                Non esiste sopra.
                Non esiste sotto.
                Non esiste avanti.
                
                Solo una direzione che esiste perché la stai ancora percependo.
                
                E più avanzi,
                più anche quella percezione si indebolisce.
                """,
                List.of(
                        new MissionChoice(
                                "Segui passivamente la distorsione geometrica",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(30);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH);
                                },
                                coreStep
                        ),
                        new MissionChoice(
                                "Forza una deviazione laterale nell'incoerenza",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(35);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH);
                                },
                                alternativeStartStep
                        )
                )
        );

        return new Mission("Il Processo", start, 150, "processo");
    }

    private void initCoreBuilders() {
        coreBuilders.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                        Le particelle non sono luce.
                        Sono istruzioni incomplete.
    
                        Ogni frammento che tocchi non mostra un ricordo,
                        ma il momento in cui quel ricordo è stato interrotto.
    
                        Vedi la struttura dell’Oblio per la prima volta:
                        non come assenza, ma come interferenza attiva.
    
                        Qualcosa sta riscrivendo ciò che può diventare reale.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Decodifica la sequenza di interferenza",
                                        ctx -> ctx.getPlayer().gainExperience(45),
                                        end
                                ),
                                new MissionChoice(
                                        "Inverti la polarità dei flussi frammentati",
                                        ctx -> ctx.getPlayer().gainExperience(40),
                                        end
                                )
                        )
                )
        );

        coreBuilders.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                        Non è un fenomeno.
                        È un sistema coerente.
    
                        Ogni perdita segue una regola.
                        Ogni assenza è riproducibile.
    
                        L’Oblio non è caos.
                        È una funzione di esclusione.
    
                        Elimina tutto ciò che può diventare incoerente.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Formalizza il modello matematico dell'esclusione",
                                        ctx -> ctx.getPlayer().gainExperience(45),
                                        end
                                ),
                                new MissionChoice(
                                        "Trova l'assioma fallato all'interno del sistema",
                                        ctx -> ctx.getPlayer().gainExperience(40),
                                        end
                                )
                        )
                )
        );

        coreBuilders.put(CharacterClass.WANDERER, (player, end) ->
                new MissionStep(
                        """
                        Non pensi in termini di struttura.
    
                        Ma riconosci il pattern.
    
                        Ogni posto in cui sei stato…
                        sta perdendo qualcosa che non riesci più a nominare.
    
                        Non è il mondo a cambiare.
                        Sei tu a ricordarlo in modo diverso ogni volta.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Segui ostinatamente le ultime tracce rimaste",
                                        ctx -> ctx.getPlayer().gainExperience(45),
                                        end
                                ),
                                new MissionChoice(
                                        "Accetta l'inversione della tua mappa interiore",
                                        ctx -> ctx.getPlayer().gainExperience(40),
                                        end
                                )
                        )
                )
        );
    }
}