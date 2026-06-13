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

public class ThirdMissionFactory implements MissionContentFactory {

    private final Map<CharacterClass, ClassStepBuilder> sanctuaryBuilders = new HashMap<>();

    public ThirdMissionFactory() {
        initSanctuaryBuilders();
    }

    @Override
    public Mission create(Player player) {
        MissionStep end = new MissionStep(
                """
                        Nel nucleo del santuario, laddove il tempo ristagna, pulsa una sfera di
                        luce esangue, simile ad un astro morente.
                        
                        Al contatto del tuo tocco, l'argine crolla: migliaia di esistenze deflagrano
                        nella tua mente come lampi accecanti.
                        
                        Volti che non sorridono più. Nomi che la pietra ha sputato via.
                        Vite ridotte a cenere, disperse nel vuoto.
                        
                        Per un battito di ciglia, l'orrore ti è manifesto: percepisci l'abisso famelico
                        dell'oblio che sta divorando le fondamenta stesse del mondo.
                        
                        Qualcuno sta tessendo una trama con questi brandelli d'anima.
                        E attraverso i loro occhi spenti, ti sta osservando.
                     """,
                List.of()
        );

        MissionStep sanctuaryStep = sanctuaryBuilders.getOrDefault(
                player.getCharacterClass(),
                (p, e) -> new MissionStep(
                        """
                                Ti inoltri tra le navate silenziose del santuario. Le pareti spoglie
                                non rivelano glifi complessi né anomalie geometriche evidenti ai tuoi occhi,
                                ma la densità dell'aria e il silenzio innaturale parlano da soli.
                                
                                Qualcosa di immenso e dimenticato è racchiuso nel profondo di questa struttura,
                                e il freddo della pietra sembra quasi voler respingere la tua stessa presenza vivente.
                             """,
                        List.of(
                                new MissionChoice(
                                        "Avanza nell'oscurità verso il centro del complesso",
                                        ctx -> ctx.getPlayer().gainExperience(20),
                                        e
                                ),
                                new MissionChoice(
                                        "Resta in ascolto prima di procedere oltre",
                                        ctx -> ctx.getPlayer().gainExperience(10),
                                        e
                                )
                        )
                )
        ).build(player, end);

        MissionStep altarsStep = new MissionStep(
                """
                        Ti allontani dal corridoio principale per esplorare le cripte laterali.
                        Qui trovi una serie di altari minori, ricoperti da uno strato di polvere argentea.
                        
                        Non ci sono icone sacre o offerte votive. Al loro posto, piccole fessure nella pietra
                        emettono un sibilo impercettibile, come se l'aria stessa stesse cercando di articolare
                        parole che non è più in grado di formulare.
                        
                        Il vuoto di queste stanze amplifica la sensazione che il santuario non sia stato costruito
                        per venerare una divinità, ma per contenere una perdita.
                     """,
                List.of(
                        new MissionChoice(
                                "Raccogli la polvere argentea e prosegui verso la camera centrale",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(15);
                                    ctx.getWorldService().getMemory().record(EventType.SANCTUARY_EXPLORED);
                                },
                                sanctuaryStep
                        ),
                        new MissionChoice(
                                "Ignora gli altari e muoviti direttamente verso il cuore della struttura",
                                ctx -> {},
                                sanctuaryStep
                        )
                )
        );

        MissionStep start = new MissionStep(
                """
                        L'incontro nella Foresta Bianca non ti ha abbandonato.
                        
                        Da quando hai toccato quella presenza, immagini che non ti
                        appartengono continuano ad affiorare nella tua mente.
                        
                        Volti sconosciuti.
                        Nomi che non ricordi di avere mai udito.
                        Frammenti di vite appartenute ad altri.
                        
                        Seguendo queste tracce di memoria, il tuo cammino ti conduce
                        tra montagne dimenticate dal tempo.
                        
                        Lì, incastonato nella roccia, sorge un antico santuario.
                        
                        Se le creature della foresta erano ciò che resta dei ricordi,
                        forse questo luogo custodisce la loro origine.
                     """,
                List.of(
                        new MissionChoice(
                                "Entra direttamente nel corridoio centrale del santuario",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.SANCTUARY_DISCOVERED),
                                sanctuaryStep
                        ),
                        new MissionChoice(
                                "Esplora le cripte e gli altari laterali prima di addentrarti",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.SANCTUARY_DISCOVERED),
                                altarsStep
                        )
                )
        );

        return new Mission("Il Santuario degli Assenti", start, 100, "santuario");
    }

    private void initSanctuaryBuilders() {
        sanctuaryBuilders.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                                Le pareti del santuario sono ricoperte dagli stessi glifi
                                che avevi visto emergere tra le rovine del villaggio.
                                
                                Ma qui non appaiono come semplici incisioni. Pulsano. Respirano.
                                Ogni simbolo custodisce un frammento di esistenza:
                                un volto, una voce, un ricordo strappato al tempo.
                                
                                All'improvviso comprendi ciò che hai incontrato nella Foresta Bianca.
                                Quell'entità non era una semplice creatura.
                                Era una memoria che aveva finalmente trovato il modo di esistere.
                             """,
                        List.of(
                                new MissionChoice(
                                        "Assorbi la risonanza dei glifi per comprendere l'origine",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(30);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Isola il flusso dei simboli per evitare distorsioni magiche",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(25);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                        },
                                        end
                                )
                        )
                )
        );

        sanctuaryBuilders.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                                L'intera struttura sembra costruita attorno ad una logica impossibile.
                                Le anomalie osservate nelle rovine e nella Foresta Bianca non sono eventi separati.
                                Sono sintomi.
                                
                                Al centro del santuario scopri enormi colonne ricoperte da schemi e sequenze
                                ripetute all'infinito.
                                
                                Comprendi allora la verità. Questo luogo non conserva oggetti né reliquie.
                                Conserva memorie. E alcune di esse stanno acquisendo una consistenza tale
                                da manifestarsi nel mondo reale.
                             """,
                        List.of(
                                new MissionChoice(
                                        "Trascrivi i pattern numerici e formalizza la sequenza",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(30);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Analizza storicamente l'origine architettonica delle colonne",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(20);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                        },
                                        end
                                )
                        )
                )
        );

        sanctuaryBuilders.put(CharacterClass.WANDERER, (player, end) ->
                new MissionStep(
                        """
                                Le tracce che avevi seguito nel villaggio riappaiono ancora una volta.
                                Attraversano il santuario e conducono verso una camera nascosta
                                nel cuore della struttura.
                                
                                Qui il sentiero termina. Non trovi alcun viandante. Nessun corpo.
                                Solo un insieme di immagini sfocate che appaiono e scompaiono nell'aria
                                come ricordi dimenticati.
                                
                                In quell'istante comprendi che le tracce non appartenevano a qualcuno
                                che stavi inseguendo. Appartenevano ad una memoria che stava cercando
                                di tornare ad esistere.
                             """,
                        List.of(
                                new MissionChoice(
                                        "Calpesta l'ultimo passo della traccia unendoti alla visione",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(30);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Mappa i confini fisici della stanza per trovare uscite nascoste",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(20);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                        },
                                        end
                                )
                        )
                )
        );
    }
}