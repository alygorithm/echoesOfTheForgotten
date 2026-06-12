package it.unicam.cs.mpgc.rpg122711.content;
import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionChoice;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionStep;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import it.unicam.cs.mpgc.rpg122711.domain.world.WorldMemory;

import java.util.List;
import java.util.*;

public class MissionContent {

    private static final Map<CharacterClass, ClassStepBuilder> CLASS_BUILDERS = new HashMap<>();


    /**
     * MISSIONE 1
     * Costruisce la prima missione del gioco.
     * Introduce il mondo, il sistema delle scelte e le prime varianti per classe
     */

    public static Mission firstMission(Player player) {

        MissionStep end = new MissionStep(
                """
                Mentre i confini del villaggio sfumano alle tue spalle,
                non hai la sensazione di esserne davvero uscito.
    
                È come se il luogo non si interrompesse ma continuasse a
                vibrare altrove, in una forma più sottile.
    
                Il vento non porta suoni: porta frammenti.
                Frammenti di parole che non appartengono più a nessuno.
    
                E per la prima volta, comprendi che l’oblio non cancella:
                trattiene.
                """,
                List.of()
        );

        MissionStep churchStep = new MissionStep(
                """
                La chiesa non è un edificio, ma un residuo di attenzione.
    
                Ogni pietra sembra ricordare di essere stata osservata.
                Ora che nessuno la guarda più, sta dimenticando la propria forma.
    
                Tra le navate spezzate, qualcosa resiste alla dissoluzione:
                un diario.
    
                Non è stato abbandonato.
                È stato lasciato qui come si lascia un pensiero a metà.
                
                Le pagine non raccontano eventi.
                Raccontano la perdita progressiva del significato delle cose.
                """,
                List.of( new MissionChoice(
                                "Leggi il diario",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(10);
                                    ctx.getWorldService().getMemory().record(EventType.DIARY_FOUND);
                                },
                                end
                        )
                )
        );

        MissionStep classStep = CLASS_BUILDERS
                .getOrDefault(player.getCharacterClass(),
                        (p,e) -> buildDefaultClassStep(p,e)).
                build(player, end);

        MissionStep start = new MissionStep(
                """
                Il villaggio non appare come un luogo abbandonato.
    
                Appare come un luogo che sta venendo lentamente rimosso
                dalla realtà... ma senza riuscirci del tutto.
    
                Le case non sono vuote: sono incomplete.
                Come ricordi che qualcuno ha iniziato a perdere.
    
                Nell’aria c’è una sensazione precisa:
                qualcosa è già accaduto, ma non è ancora finito di accadere.
                """,
                List.of(
                        new MissionChoice(
                                "Esplora le rovine",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.ENTERED_RUINS),
                                classStep
                        ),

                        new MissionChoice(
                                "Entra nella chiesa abbandonata",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.ENTERED_CHURCH),
                                churchStep
                        )
                )
        );

        return new Mission("La Rovina Silente", start, 50);
    }

    static {
        CLASS_BUILDERS.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                               Oltre le ultime case del villaggio, qualcosa attira il tuo sguardo.
                                
                               Sulle pietre consumate compaiono dei segni deboli, come incisioni
                               emerse dall'interno della materia stessa.

                               Non sono rune nel senso comune del termine.

                               Sembrano piuttosto tracce lasciate da pensieri troppo antichi per sopravvivere,
                               impronte di ricordi che si rifiutano di scomparire.

                               Più le osservi, più comprendi che quelle forme non sono state scritte
                               da qualcuno.

                               Sono affiorate.

                               Come se il mondo stesso stesse tentando disperatamente di ricordare qualcosa.
                        """,
                        List.of(new MissionChoice(
                                "Studia le incisioni",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(20);
                                    ctx.getWorldService().getMemory().record(EventType.RUNES_READ);
                                },
                                end
                        ))
                )
        );

        CLASS_BUILDERS.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                               Osservando le rovine più da vicino, inizi a notare particolari
                               impossibili da ignorare.
    
                               Alcuni edifici sembrano occupare meno spazio all'esterno che
                               all'interno. Finestre e porte non coincidono.
    
                               Distanze che dovrebbero essere identiche risultano diverse ad ogni
                               misurazione. Non è un'illusione.
    
                               E' come se il villaggio stesse perdendo lentamente le regole che
                               lo definiscono.
    
                               Per la prima volta nella tua vita, ti trovi davanti a qualcosa
                               che non si limita a sfidare la logica.
    
                               Qualcosa la sta dimenticando.
                        """,
                        List.of(new MissionChoice(
                                "Analizza la distorsione",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(15);
                                    ctx.getWorldService().getMemory().record(EventType.DISTORTION_ANALYZED);
                                },
                                end
                        ))
                )
        );
    }

    /**
     * Step fallback utilizzato quando una classe non ha contenuto specifico
     */

    public static MissionStep buildDefaultClassStep(Player player, MissionStep end) {
        return new MissionStep(
                """
                        Non possiedi la sensibilità degli arcanisti né gli strumenti
                        degli studiosi.
                        
                        Eppure c'è qualcosa che attira immediatamente la tua attenzione.
                        Tracce. Solchi impressi nella polvere.
                        
                        Seguono un percorso incerto tra le rovine e sembrano anche
                        relativamente recenti.
                        
                        Ti inginocchi per osservarli meglio.
                        Non appartengono ad un animale.
                        
                        Non appartengono nemmeno a qualcuno che stava semplicemente
                        attraversando il villaggio.
                        
                        Chiunque sia passato di qui si è fermato più volte.
                        Ha osservato.
                        Ha cercato qualcosa.
                        E alla fine è scomparso oltre le nebbie.
                        
                        Come se stesse inseguendo la stessa domanda che ora tormenta te.
                     """,
                List.of( new MissionChoice(
                        "Segui le tracce",
                        ctx -> {
                            ctx.getPlayer().gainExperience(15);
                            ctx.getWorldService().getMemory().record(EventType.TRACKS_FOLLOWED);
                        },
                        end
                ))
        );
    }


    /**
     * MISSIONE 2
     * Introduce la foresta bianca ed il concetto di memoria.
     * Ramificazioen basata su classe e percezione del player.
     */
    private static final Map<CharacterClass, ClassStepBuilder> FOREST_BUILDERS = new HashMap<>();
    private static final Map<CharacterClass, ClassStepBuilder> MEMORY_BUILDERS = new HashMap<>();

    public static Mission secondMission(Player player) {


        MissionStep end = new MissionStep(
                """
                          La Foresta Bianca recede lentamente, sbiadendo come un sogno
                          al risveglio, ma le sue radici non ti hanno abbandonato.
                        
                          L'eco persiste, vibrando sotto la tua pelle: un rintocco che
                          non accenna a spegnersi.
                        
                          Ora respira dentro di te: un frammento di vita vissuta da altri,
                          una memoria che reclama spazio nel tuo cuore pur non 
                          essendone mai stata parte.
                        """,
                List.of()
        );

        MissionStep forestStep =
                FOREST_BUILDERS.getOrDefault(player.getCharacterClass(),
                        (p, e) -> new MissionStep(
                                """
                                           Cammini tra i tronchi per ore.
                                           Nessun animale. Nessun vento. Nessun suono.
                                           Eppure non hai mai la sensazione di essere solo.
                                        
                                           Alcune volte ti sembra di riconoscere una voce.
                                           Altre volte un volto.
                                        
                                           Sensazioni fugaci che svaniscono prima di poter
                                           essere afferrate.
                                           Come ricordi appartenuti a qualcun altro.
                                        
                                           La foresta non sembra abitata da creature.
                                           Sembra abitata da assenze.
                                        """,
                                List.of(new MissionChoice(
                                        "Procedi",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(15);
                                            ctx.getWorldService().getMemory().record(EventType.FOREST_TRAVERSED);
                                        },
                                        e
                                ))
                        )
                ).build(player, end);

        MissionStep memoryStep = MEMORY_BUILDERS
                .getOrDefault(player.getCharacterClass(),
                        (p, e) -> new MissionStep(
                                """
                                           Un'ombra si stacca lentamente dal candore della foresta.
                                        
                                           Non senti passi. Non senti respiro. Eppure la presenza si avvicina.
                                           Rimane immobile a pochi metri da te, osservandoti nel silenzio.
                                        
                                           Non percepisci ostilità. Non percepisci paura.
                                        
                                           Poi qualcosa attraversa la tua mente.
                                           Una casa illuminata dal sole. Una strada deserta. Una risata lontana.
                                        
                                           Immagini sconosciute si susseguono senza alcun ordine,
                                           come frammenti di vite appartenute ad altri.
                                        
                                           Quando il flusso termina, l'ombra inizia a dissolversi.
                                        
                                           Non hai scoperto il suo nome. Non sai chi fosse.
                                           Ma una certezza rimane. Hai appena incontrato qualcuno che non esiste più.
                                        """,
                                List.of(new MissionChoice(
                                        "Avvicinati",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(25);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CONTACT);
                                        },
                                        e
                                ))
                        )
                ).build(player, end);

        MissionStep start = new MissionStep(
                """
                           Il richiamo delle rovine non si è mai pacato; è diventato un battito
                           sordo che ti ha trascinato oltre i margini del mondo, dove le mappe
                           smettono di respirare.
                        
                           Sei giunto in un luogo che la storia ha rigettato, un vuoto bianco
                           che nessun registro ha saputo trattenere.
                        
                           Una foresta. Bianca. Immobile.
                        
                           Un labirinto di tronchi d'avorio che sembra esistere
                           solo a metà.
                        
                           Come se il luogo fosse stato dimenticato dal mondo,
                           ma si ostinasse ancora a rimanere qui.
                        """,
                List.of(new MissionChoice(
                                "Esamina la foresta",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.FOREST_OBSERVED),
                                forestStep
                        ),
                        new MissionChoice(
                                "Segui l'eco",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.ECHO_FOLLOWED),
                                memoryStep
                        )
                )
        );

        return new Mission("L'eco nella Foresta Bianca", start, 75);

    }

    /**
     * Contenuti specializzati per la seconda missione,
     * differenziati per classe del personaggio.
     */

    static {
        FOREST_BUILDERS.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                        Ogni tronco emana un debole residuo magico.
                        Ma non è energia.
                        
                        E' ricordo.
                        Frammenti di vite dimenticate scorrono ancora sotto la 
                        superficie bianca del legno.
                        
                        Per un istante senti emozioni che non ti appartengono.
                        Paura. Gioia. Rimpianto
                        
                        Come se la foresta fosse cresciuta nutrendosi delle 
                        memorie di chi è scomparso.
                        """,
                        List.of(new MissionChoice(
                                "Analizza i residui magici",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(20);
                                    ctx.getWorldService().getMemory().record(EventType.FOREST_ANALYZED);
                                },
                                end
                        ))
                )
        );

        FOREST_BUILDERS.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                        Osservando la struttura dei tronchi, noti uno schema
                        ricorrente.
                        
                        Le venature non seguono una crescita naturale.
                        
                        Sembrano organizzate secondo una logica precisa,
                        quasi archivistica.
                        
                        Ogni albero appare diverso dagli altri eppure parte
                        di un sistema più grande.
                        
                        Per la prima volta ti attraversa un pensiero inquietante:
                        e se questa foresta non fosse vegetazione?
                        
                        E se fosse un immenso archivio costruito con i ricordi
                        di qualcosa che non esiste più?
                        """,
                        List.of(new MissionChoice(
                                "Studia la struttura",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(15);
                                    ctx.getWorldService().getMemory().record(EventType.FOREST_ANALYZED);
                                },
                                end
                        ))
                )
        );
    }

    static {
        MEMORY_BUILDERS.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                        La foschia si addensa tra i tronchi candidi della foresta,
                        piegandosi in forme che nessun vento dovrebbe poter modellare.
                        
                        Una figura emerge lentamente dal bianco.
                        Non è viva.
                        Ma non è nemmeno morta.
                        
                        La osservi e comprendi immediatamente che non si tratta di
                        una creatura.
                        Al suo interno percepisci decine di presenze sovrapposte,
                        frammenti di esistenze diverse intrecciate in un'unica forma.
                        
                        Quando la figura si avvicina, immagini estranee invadono la tua mente.
                        
                        Una madre che stringe la mano di suo figlio.
                        Un uomo che osserva il mare.
                        Una voce che pronuncia un nome ormai dimenticato.
                        
                        Ricordi.. non tuoi.
                        
                        Per un istante comprendi la verità.
                        Ciò che hai davanti non è un essere vivente... è ciò che rimane 
                        di persone che il mondo ha ormai dimenticato.
                        """,
                        List.of(new MissionChoice(
                                "Avvicinati",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.MEMORY_CONTACT);
                                },
                                end
                        ))
                )
        );

        MEMORY_BUILDERS.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                        La figura prende forma davanti a te come un'anomalia
                        impossibile da classificare. Non possiede una struttura stabile.
                        
                        La sua sagoma muta continuamente, come se decine di identità
                        diverse stessero tentando di occupare lo stesso spazio.
                        
                        Non è caos. E' organizzazione.
                        Un sistema composto da memorie che si sono aggregate finoa
                        a diventare qualcosa di nuovo.
                        
                        Quando ti avvicini, informazioni sconosciute attraversano
                        la tua mente.
                        
                        Volti. Luoghi. Eventi che non hai mai vissuto.
                        
                        Per alcuni istanti la tua coscienza si sovrappone a quella 
                        di individui che non hai mai incontrato.
                        
                        Poi tutto svanisce. E comprendi ciò che stai osservando.
                        La creatura non consera ricordi... la creatura è fatta di ricordi.
                        """,
                        List.of(new MissionChoice(
                                "Avvicinati",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.MEMORY_CONTACT);
                                },
                                end
                        ))
                )
        );
    }


    /**
     * MISSIONE 3
     * Introduzione del santuario.
     * Espande il tema delle memorie e della loro materializzazione
     */
    private static final Map<CharacterClass, ClassStepBuilder> SANCTUARY_BUILDERS = new HashMap<>();

    public static Mission thirdMission(Player player) {

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

        MissionStep sanctuaryStep =
                SANCTUARY_BUILDERS.getOrDefault(
                        player.getCharacterClass(),
                        (p, e) -> new MissionStep(
                                "Nulla accade.",
                                List.of(new MissionChoice(
                                        "Continua",
                                        ctx -> {},
                                        e
                                ))
                        )
                ).build(player, end);

        // step iniziale
        MissionStep start = new MissionStep(
                """
                        L'incontro nella Foresta Bianca non ti ha abbandonato.
                        
                        Da quando hai toccato quella presenza, immagini che non ti
                        appartengono continuano ad affiorare nella tua mente.
                        
                        Volti sconosciuti.
                        Nomi che non ricordi di aver mai udito.
                        Frammenti di vite appartenute ad altri.
                        
                        Seguendo queste tracce di memoria, il tuo cammino ti conduce
                        tra montagne dimenticate dal tempo.
                        
                        Lì, incastonato nella roccia, sorge un antico santuario.
                        
                        Se le creature della foresta erano ciò che resta dei ricordi,
                        forse questo luogo custodisce la loro origine.
                     """,
                List.of( new MissionChoice(
                                "Entra nel santuario",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.SANCTUARY_DISCOVERED),
                                sanctuaryStep
                        )
                )
        );

        return new Mission(
                "Il Santuario degli Assenti",
                start,
                100
        );
    }

    /**
     * Variazioni narrative della missione 3 in base alla classe.
     */
    static {
        SANCTUARY_BUILDERS.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                                Le pareti del santuario sono ricoperte dagli stessi gilfi
                                che avevi visto emergere tra le rovine del villaggio.
                                
                                Ma qui non appaiono come semplici incisioni.
                                Pulsano. Respirano.
                                
                                Ogni simbolo custodisce un frammento di esistenza:
                                un volto, una voce, un ricordo strappato al tempo.
                                
                                All'improvviso comprendi ciò che hai incontrato nella
                                Foresta Bianca.
                                
                                Quell'entità non era una semplice creatura.
                                Era una memoria che aveva finalmente trovato il modo 
                                di esistere.
                             """,
                        List.of(new MissionChoice(
                                "Studia il nucleo di memoria",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(30);
                                    ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                },
                                end
                        ))
                )

        );

        SANCTUARY_BUILDERS.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                                L'intera struttura sembra costruita attorno ad una logica
                                impossibile.
                                
                                Le anomalie osservate nelle rovine e nella Foresta Bianca
                                non sono eventi separati. Sono sintomi.
                                
                                Al centro del santuario scopri enormi colonne ricoperte
                                da schemi e sequenze ripetute all'infinito.
                                
                                Comprendi allora la verità. Questo luogo non conserva oggetti né reliquie.
                                Conserva memorie.
                                
                                E alcune di esse stanno acquisendo una consistenza tale
                                da manifestarsi nel mondo reale.
                             """,
                        List.of(new MissionChoice(
                                "Analizza il fenomeno",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                },
                                end
                        ))
                )
        );

        SANCTUARY_BUILDERS.put(CharacterClass.WANDERER, (player, end) ->
                new MissionStep(
                        """
                                Le tracce che avevi seguito nel villaggio riappaiono
                                ancora una volta.
                                
                                Attraversano il santuario e conducono verso una camera
                                nascosta nel cuore della struttura.
                                
                                Qui il sentiero termina. Non trovi alcun viandante.
                                Nessun corpo. Nessuna presenza.
                                
                                Solo un insieme di immagini sfocate che appaiono e
                                scompaiono nell'aria come ricordi dimenticati.
                                
                                In quell'istante comprendi che le tracce non appartenevano
                                a qualcuno che stavi inseguendo.
                                
                                Appartenevano ad una memoria che stava cercando di
                                tornare ad esistere
                             """,
                        List.of(new MissionChoice(
                                "Segui le tracce",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                },
                                end
                        ))
                )
        );
    }


    /**
     * MISSIONE 4
     * La valle di Nhal ed il custode.
     * Introduce il concetto di entità centrale legata all'Oblio
     */
    private static final Map<CharacterClass, ClassStepBuilder> CUSTODIAN_BUILDERS = new HashMap<>();

    public static Mission fourthMission(Player player) {

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

        MissionStep custodianStep =
                CUSTODIAN_BUILDERS.getOrDefault(
                        player.getCharacterClass(), (p,e) ->
                                new MissionStep(
                                        "Nulla accade.",
                                        List.of( new MissionChoice(
                                                "Continua",
                                                ctx -> {}, e
                                        ))
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
                List.of(new MissionChoice(
                        "Prosegui verso la torre",
                        ctx -> {
                            ctx.getPlayer().gainExperience(20);
                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_MENTIONED);
                            ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_ENCOUNTERED);
                        },
                        end
                ))
        );

        // step iniziale
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
                List.of( new MissionChoice(
                                "Parla con gli abitanti",
                                ctx -> {},
                                villageStep
                        ),
                        new MissionChoice(
                                "Segui le tracce della figura",
                                ctx -> {},
                                custodianStep
                        )
                )
        );

        return new Mission(
                "Il Custode dell'Oblio",
                start,
                125
        );
    }

    static {
        CUSTODIAN_BUILDERS.put (CharacterClass.ARCANIST, (player, end) ->
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
                                
                                Qualcuno che sta lasciando dietro di sé una scia di ricordi
                                strappati alla realtà.
                             """,
                        List.of(new MissionChoice(
                                "Analizza i residui",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_MAGIC_DISCOVERED);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                },
                                end
                        ))
                )
        );

        CUSTODIAN_BUILDERS.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                                Le anomalie della valle seguono uno schema preciso.
                                Non sono casuali.
                                
                                Ogni persona colpita presenta la stessa assenza.
                                Gli stessi vuoti. Le stesse lacune.
                                
                                Come se qualcuno stesse rimuovendo selettivamente
                                intere porzioni di esistenza.
                                
                                Le memorie non sembrano svanire. Sembrano trasferite altrove.
                                Conservate. Archiviate.
                                
                                Tutti gli indizi conducono alla torre che domina la valle. 
                                Qualcuno sta raccogliendo i ricordi del mondo prima che 
                                possano scomparire per sempre.
                             """,
                        List.of(new MissionChoice(
                                "Studia il fenomeno",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.MEMORY_STORAGE_DISCOVERED);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                },
                                end
                        ))
                )
        );

        CUSTODIAN_BUILDERS.put(CharacterClass.WANDERER, (player, end) ->
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
                                Verso un altro frammento di verità.
                                
                                Qualunque cosa stia lasciando quelle impronte, si trova
                                al centro di tutto questo.
                             """,
                        List.of( new MissionChoice(
                                "Segui le impronte",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_TRACKS_FOUND);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_REVEAL);
                                },
                                end
                        ))
                )
        );
    }


    /**
     * MISSIONE 5
     * Accesso alla biblioteca dei perduti
     * Espande il sistema di archiviazione delle memorie
     *
     * @param player
     * @return
     */
    public static Mission fifthMission (Player player) {

        // step finale
        MissionStep end = new MissionStep(
                """
                        Il Custode non si muove. Non reagisce come un nemico.
                        Non si comporta come una guida. Semplicemente… osserva.

                        Come se finalmente qualcosa, dopo lungo tempo, avesse trovato il suo posto.
                        Poi parla.

                        "Hai visto ciò che resta quando una vita viene sottratta all’oblio."
                        "Ma ciò che chiami oblio… non è la fine."

                        Dietro di lui, la Biblioteca si apre ancora di più. Le memorie tremano.
                        Come se qualcosa, molto più in profondità, stesse richiamando anche loro.

                        "Se vuoi comprendere ciò che accade… devi vedere ciò che consuma ogni cosa."

                        E oltre la Biblioteca… si apre il vuoto.
                     """,
                List.of()
        );

        MissionStep custodianStep =
                CUSTODIAN_BUILDERS.getOrDefault(
                        player.getCharacterClass(),
                        (p,e) -> new MissionStep(
                                "Nulla accade.",
                                List.of(new MissionChoice("Continua", ctx -> {}, e))
                        )
                ).build(player, end);

        MissionStep libraryStep = new MissionStep(
                """
                        Non è un luogo. È una sospensione.
                        Uno spazio che esiste solo perché qualcosa ha deciso di trattenere ciò che stava scomparendo.

                        Davanti a te si estende la Biblioteca dei Perduti.
                        Non esistono pareti. Non esistono confini.
                        Solo strutture di luce che fluttuano nel vuoto come pensieri non ancora dissolti.

                        E dentro ogni sfera…
                        una memoria. Una vita intera. Una storia che non vuole sparire.
                     """,
                List.of(
                        new MissionChoice(
                                "Avvicinati alle memorie",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(20);
                                    ctx.getWorldService().getMemory().record(EventType.MEMORY_ARCHIVE_OBSERVED);
                                },
                                custodianStep
                        )
                )
        );

        // step iniziale
        MissionStep start = new MissionStep(
                """
                        La figura non è fuggita. Ha lasciato una traccia.
                        Non fatta di materia, ma di direzione.

                        Una scia che attraversa la Valle di Nhal come se il mondo stesso
                        avesse scelto di aprirsi al suo passaggio.

                        Seguirla non è una decisione. È una conseguenza inevitabile.

                        Più avanzi, più la nebbia si dissolve.
                        Non perché sparisca… ma perché qualcosa ti sta permettendo di vedere.

                        Davanti a te, la realtà si apre.
                        Non come una porta. Ma come una ferita ancora viva.

                        E oltre quella ferita… qualcosa attende.
                     """,
                List.of(
                        new MissionChoice(
                                "Segui la traccia",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(10);
                                    ctx.getWorldService().getMemory().record(EventType.LIBRARY_FOUND);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH);
                                },
                                libraryStep
                        )
                )
        );

        return new Mission(
                "La Biblioteca dei Perduti",
                start,
                150
        );
    }


    /**
     * MISSIONE 6
     * Collasso della realtà
     * Il mondo diventa instabile e perde coerenza
     * @param player
     * @return
     */
    public static Mission sixthMission(Player player) {

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

        MissionStep coreStep =
                SANCTUARY_BUILDERS
                        .getOrDefault(
                                player.getCharacterClass(),
                                (p, e) -> new MissionStep(
                                        "Nulla accade.",
                                        List.of(new MissionChoice("Continua", ctx -> {}, e))
                                )
                        )
                        .build(player, end);


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
                                "Seguire la distorsione",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(30);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH);
                                },
                                coreStep
                        )
                )
        );

        return new Mission(
                "Il Processo",
                start,
                150
        );
    }

    static {

        SANCTUARY_BUILDERS.put(CharacterClass.ARCANIST, (player, end) ->
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
                                        "Decodifica la distorsione",
                                        ctx -> ctx.getPlayer().gainExperience(40),
                                        end
                                )
                        )
                )
        );

        SANCTUARY_BUILDERS.put(CharacterClass.SCHOLAR, (player, end) ->
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
                                        "Formalizza il modello",
                                        ctx -> ctx.getPlayer().gainExperience(40),
                                        end
                                )
                        )
                )
        );

        SANCTUARY_BUILDERS.put(CharacterClass.WANDERER, (player, end) ->
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
                                        "Segui ciò che resta",
                                        ctx -> ctx.getPlayer().gainExperience(40),
                                        end
                                )
                        )
                )
        );
    }


    /**
     * MISSIONE 7
     * Scelta tra 3 esiti dell'Oblio
     * Determina la conclusione della storia ed il ruolo del player
     * nel sistema.
     *
     * @param player
     * @return
     */
    public static Mission seventhMission(Player player) {

        MissionStep endConserve = new MissionStep(
                """
                Non distruggi l’Oblio. Lo fermi.
                Per un istante, tutto sembra stabilizzarsi.
                
                Le memorie non svaniscono.
                Non si salvano completamente. 
                Restano. Imperfette. Vive.
                
                Il mondo continua. Ma è fragile.
                
                Ogni cosa può essere ricordata…  ma nulla è più garantito.
                 E per la prima volta, la realtà diventa un archivio instabile.
                """,
                List.of()
        );

        MissionStep endErase = new MissionStep(
                """
                Non opponi resistenza. E il processo si completa. 
                Non c’è esplosione. Non c’è silenzio.
                
                C’è la rimozione del concetto stesso di esistenza.
                
                Le cose non spariscono. Non arrivano mai a esistere.
                Non c’è passato. Non c’è futuro.
                
                Solo un presente che non si forma mai.
                """,
                List.of()
        );

        MissionStep endCustodian = new MissionStep(
                """
                Non scegli. Comprendi.
                
                E nel momento in cui comprendi, la funzione ti riconosce.
                
                Il Custode non è un individuo.
                È un punto di stabilità nel processo.
                
                Ora quel punto sei tu.
                
                Le memorie non passano più attraverso qualcuno.
                Passano attraverso ciò che sei diventato.
                
                Non osservi più il sistema. Lo mantieni.
                Tra ciò che esiste e ciò che non può ancora esistere.
                """,
                List.of()
        );

        MissionStep finalStep = new MissionStep(
                """
                L’Oblio non ti parla.
                Perché non è qualcosa che comunica. È qualcosa che accade.
                
                E ora accade attorno a te.
                
                Tutto ciò che hai visto fino a questo momento:
                la foresta, il santuario, il custode, la biblioteca…
                non erano luoghi.
                
                Erano livelli di filtraggio della realtà.
                E ora sei nel punto in cui il filtro si rompe.
                
                Davanti a te non c’è una scelta.
                Ma tre direzioni di coerenza:
                
                1) Conservare → il mondo continua come memoria instabile
                2) Liberare → il mondo non si forma mai
                3) Diventare Custode → diventare struttura del sistema
                
                Il Custode appare un’ultima volta.
                E questa volta non osserva. Aspetta la tua definizione.
                """,
                List.of(
                        new MissionChoice(
                                "Conserva",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.OBLIVION_TRUTH_REVEALED),
                                endConserve
                        ),
                        new MissionChoice(
                                "Libera tutto",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.OBLIVION_PATH_WALKED),
                                endErase
                        ),
                        new MissionChoice(
                                "Diventa il Custode",
                                ctx -> ctx.getPlayer().gainExperience(50),
                                endCustodian
                        )
                )
        );

        MissionStep coreStep = new MissionStep(
                """
                Non è un luogo.
                
                È un’origine che non ha mai deciso di esserlo.
                Qui ogni cosa viene “tentata”.
                Non creata. Non distrutta.
                
                Tentata.
                
                Ogni possibilità nasce… e viene immediatamente testata contro il nulla.
                E quasi tutte falliscono prima di diventare reali.
                
                Ora capisci il Custode.
                
                Non protegge memorie.
                Protegge la continuità minima dell’esistenza.
                """,
                List.of(
                        new MissionChoice(
                                "Interagisci con il nucleo",
                                ctx -> ctx.getPlayer().gainExperience(40),
                                finalStep
                        )
                )
        );

        MissionStep start = new MissionStep(
                """
                Il varco non si apre. Si completa.
                Non stai entrando. Stai diventando compatibile con ciò che c’è oltre.
                
                La realtà non si rompe.
                Si arrende alla tua presenza.
                
                E quando finalmente attraversi… non trovi un mondo.
                Trovi il meccanismo che decide se un mondo può esistere.
                """,
                List.of(
                        new MissionChoice(
                                "Attraversa il nucleo",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH),
                                coreStep
                        )
                )
        );

        return new Mission(
                "Il Cuore dell’Oblio",
                start,
                200
        );
    }





}