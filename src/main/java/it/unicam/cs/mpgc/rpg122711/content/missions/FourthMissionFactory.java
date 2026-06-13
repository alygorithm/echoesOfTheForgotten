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

public class FourthMissionFactory implements MissionContentFactory {

    private final Map<CharacterClass, ClassStepBuilder> custodianBuilders = new HashMap<>();

    public FourthMissionFactory() {
        initCustodianBuilders();
    }

    @Override
    public Mission create(Player player) {
        MissionStep end = new MissionStep(
                """
                    La torre si sgretola nel silenzio. Sulla sommità, avvolta dalla nebbia, 
                    una figura osserva il paesaggio morente.
                    
                    Per un istante i suoi occhi incontrano i tuoi.
                    Non c'è ostilità. Non c'è paura. Solo una stanchezza antica.
                    
                    Tra le sue mani fluttuano decine di frammenti luminosi.
                    Memorie. Le stesse che hai visto affiorare nella foresta.
                    Le stesse che hai percepito nel santuario.
                    
                    La figura le raccoglie una ad una, come un archivista intento a 
                    salvare le ultime pagine di un libro in fiamme.
                    
                    Poi la nebbia si richiude. Prima di svanire, una sola frase 
                    raggiunge il tuo orecchio.
                    
                    "Quando l'ultima memoria cadrà,
                    non resterà più nulla da ricordare."
                    E la figura scompare.
                 """,
                List.of()
        );

        MissionStep custodianStep = custodianBuilders.getOrDefault(
                player.getCharacterClass(), (p, e) ->
                        new MissionStep(
                                """
                                    Ti inoltri tra i sentieri tormentati della valle. Non possiedi
                                    gli strumenti per decifrare l'energia residua né l'istinto dei
                                    cacciatori di tracce.
                                    
                                    Eppure, la pesantezza dell'aria è innegabile. Senti il vuoto
                                    premere ai lati della tua mente, spingendoti in un'unica
                                    direzione inevitabile.
                                    
                                    La presenza è passata di qui, lasciando dietro di sé una scia
                                    di puro silenzio che ti guida dritto verso la destinazione.
                                 """,
                                List.of(
                                        new MissionChoice(
                                                "Forza il passo attraverso la nebbia verso la guglia",
                                                ctx -> ctx.getPlayer().gainExperience(15),
                                                e
                                        ),
                                        new MissionChoice(
                                                "Avanza con cautela proteggendo la tua mente dal vuoto",
                                                ctx -> ctx.getPlayer().gainExperience(20),
                                                e
                                        )
                                )
                        )
        ).build(player, end);

        MissionStep villageStep = new MissionStep(
                """
                Gli abitanti della valle errano come ombre alla deriva,
                intrappolati in una vita che non comprendono più.
        
                Nei loro occhi riconosci qualcosa di familiare.
        
                È la stessa assenza che hai percepito nel santuario.
                Lo stesso vuoto della Foresta Bianca.
        
                Nessuno ricorda il proprio nome.
                Nessuno ricorda i propri legami.
                Nessuno ricorda perché esista.
        
                Un vecchio si avvicina e ti afferra il braccio.
                Per un istante sembra combattere contro qualcosa dentro di sé.
        
                Poi sussurra:
        
                "Il Custode è passato."
        
                Il suo sguardo si spegne.
                """,
                List.of(
                        new MissionChoice(
                                "Lascia il villaggio e segui il Custode verso la torre",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(20);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_MENTIONED);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_ENCOUNTERED);
                                },
                                custodianStep
                        ),
                        new MissionChoice(
                                "Tenta di calmare gli abitanti prima di rimetterti in marcia",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_MENTIONED);
                                },
                                custodianStep
                        )
                )
        );

        MissionStep start = new MissionStep(
                """
                    Dopo aver lasciato il santuario, una visione continua
                    a perseguitarti.
                    
                    Non è un ricordo. Non è un sogno.
                    
                    E' qualcosa che hai percepito attraverso il nucleo di memoria
                    custodito nelle profondità del santuario.
                    
                    Una città immensa. Silenziosa. Immobile.
                    
                    Al centro delle sue strade deserte erge una figura avvolta
                    nell'oscurità.
                    
                    La stessa presenza che sembra riaffiorare dietro ogni anomalia
                    incontrata durante il viaggio.
                    
                    Seguendo le tracce lasciate in quella visione,
                    il tuo cammino ti conduce fino alla valle di Nhal.
                    
                    Una nebbia nera avvolge la regione.
                    Persino il vento sembra aver dimenticato come soffiare
                 """,
                List.of(
                        new MissionChoice(
                                "Entra nell'insediamento e parla con gli abitanti",
                                ctx -> {},
                                villageStep
                        ),
                        new MissionChoice(
                                "Evita il villaggio e segui le tracce esterne della figura",
                                ctx -> {},
                                custodianStep
                        )
                )
        );

        return new Mission("Il Custode dell'Oblio", start, 125, "custode");
    }

    private void initCustodianBuilders() {
        custodianBuilders.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                            L'aria è impregnata della stessa energia che hai percepito
                            tra le rune del santuario.
                            
                            Particelle luminose fluttuano nella nebbia come cenere sospesa.
                            
                            Quando provi a toccarle, immagini estranee attraversano 
                            la tua mente.
                            
                            Volti sconosciuti. Voci dimenticate. Istanti di vite che non ti appartengono.
                            Non sono semplici residui magici. Sono frammenti di memoria.
                            
                            Qualcuno è passato di qui.
                            Qualcuno che conosce il linguaggio inciso nelle rune.
                         """,
                        List.of(
                                new MissionChoice(
                                        "Assorbi i residui magici per svelare l'identità del Custode",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(30);
                                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_MAGIC_DISCOVERED);
                                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Dissipa le particelle instabili ed entra direttamente nella torre",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(20);
                                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                        },
                                        end
                                )
                        )
                )
        );

        custodianBuilders.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                            Le anomalie della valle seguono uno schema preciso.
                            Non sono casuali.
                            
                            Ogni persona colpita presenta la stessa assenza.
                            Gli stessi vuoti. Le stesse lacune.
                            
                            Come se qualcuno stesse rimuovendo selettivamente
                            intere porzioni di existência.
                            
                            Le memorie non sembrano svanire. Sembrano trasferite altrove.
                            Conservate. Archiviate.
                            
                            Tutti gli indizi conducono alla torre che domina la valle.
                         """,
                        List.of(
                                new MissionChoice(
                                        "Catalogate l'ordine dei vuoti per comprendere il sistema di archiviazione",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(30);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_STORAGE_DISCOVERED);
                                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Ignora lo schema teorico e corri alla sommità della struttura",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(15);
                                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                        },
                                        end
                                )
                        )
                )
        );

        custodianBuilders.put(CharacterClass.WANDERER, (player, end) ->
                new MissionStep(
                        """
                            Sul terreno umido riconosci immediatamente quelle tracce.
                            Le hai viste nel villaggio silenzioso.
                            Le hai ritrovate tra i sentieri della foresta.
                            
                            Ora sono di nuovo davanti a te.
                            Sempre identiche e sempre fresche.
                            
                            Come se il loro proprietario non appartenesse alle leggi
                            del tempo.
                            
                            Le hai inseguite per l'intero viaggio e ogni volta ti hanno
                            condotto verso un'altra anomalia.
                         """,
                        List.of(
                                new MissionChoice(
                                        "Segui meticolosamente le impronte calpestando i passi del Custode",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(30);
                                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_TRACKS_FOUND);
                                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Precorri i tempi tagliando la strada verso l'ingresso della torre",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(20);
                                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                        },
                                        end
                                )
                        )
                )
        );
    }
}