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

public class SecondMissionFactory implements MissionContentFactory {

    private final Map<CharacterClass, ClassStepBuilder> forestBuilders = new HashMap<>();
    private final Map<CharacterClass, ClassStepBuilder> memoryBuilders = new HashMap<>();

    public SecondMissionFactory() {
        initForestBuilders();
        initMemoryBuilders();
    }

    @Override
    public Mission create(Player player) {
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

        MissionStep forestStep = forestBuilders.getOrDefault(player.getCharacterClass(),
                (p, e) -> new MissionStep(
                        """
                                   Cammini tra i tronchi per ore.
                                   Nessun animale. Nessun vento. Nessun suono.
                                   Eppure non hai mai la sensazione di essere solo.
                                
                                   Alcune volte ti sembra di riconoscere una voce.
                                   Altre volte un volto.
                                
                                   La foresta non sembra abitata da creature.
                                   Sembra abitata da assenze.
                                """,
                        List.of(
                                new MissionChoice(
                                        "Ascolta le assenze in profondità",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(15);
                                            ctx.getWorldService().getMemory().record(EventType.FOREST_TRAVERSED);
                                        },
                                        e
                                ),
                                new MissionChoice(
                                        "Cerca l'origine dei sussurri e segui l'eco",
                                        ctx -> {},
                                        end
                                )
                        )
                )
        ).build(player, end);

        MissionStep memoryStep = memoryBuilders.getOrDefault(player.getCharacterClass(),
                (p, e) -> new MissionStep(
                        """
                                   Un'ombra si stacca lentamente dal candore della foresta.
                                
                                   Non senti passi. Non senti respiro. Eppure la presenza si avvicina.
                                   Rimane immobile a pochi metri da te, osservandoti nel silenzio.
                                
                                   Poi qualcosa attraversa la tua mente.
                                   Una casa illuminata dal soleil. Una strada deserta. Una risata lontana.
                                
                                   Quando il flusso termina, l'ombra inizia a dissolversi.
                                """,
                        List.of(
                                new MissionChoice(
                                        "Tenta un contatto diretto con l'ombra",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(25);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CONTACT);
                                        },
                                        e
                                ),
                                new MissionChoice(
                                        "Arretra e studia l'ambiente circostante",
                                        ctx -> {},
                                        forestStep
                                )
                        )
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
                        """,
                List.of(
                        new MissionChoice(
                                "Esamina la vegetazione della foresta",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.FOREST_OBSERVED),
                                forestStep
                        ),
                        new MissionChoice(
                                "Inseguendo l'eco ancestrale",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.ECHO_FOLLOWED),
                                memoryStep
                        )
                )
        );

        return new Mission("L'eco nella Foresta Bianca", start, 75, "foresta");
    }

    private void initForestBuilders() {
        forestBuilders.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                        Ogni tronco emana un debole residuo magico.
                        Frammenti di vite dimenticate scorrono ancora sotto la 
                        superficie bianca del legno.
                        
                        Per un istante senti emozioni che non ti appartengono.
                        Paura. Gioia. Rimpianto.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Incanala l'energia dei tronchi",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(25);
                                            ctx.getWorldService().getMemory().record(EventType.FOREST_ANALYZED);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Purifica la linfa e stabilizza i ricordi",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(20);
                                            ctx.getWorldService().getMemory().record(EventType.FOREST_ANALYZED);
                                        },
                                        end
                                )
                        )
                )
        );

        forestBuilders.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                        Osservando la struttura dei tronchi, noti uno schema ricorrente.
                        Le venature non seguono una crescita naturale.
                        
                        Sembrano organizzate secondo una logica precisa, quasi archivistica.
                        E se questa foresta fosse un immenso registro biologico?
                        """,
                        List.of(
                                new MissionChoice(
                                        "Decodifica il sistema delle venature",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(25);
                                            ctx.getWorldService().getMemory().record(EventType.FOREST_ANALYZED);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Prendi campioni di corteccia e cataloga",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(15);
                                            ctx.getWorldService().getMemory().record(EventType.FOREST_ANALYZED);
                                        },
                                        end
                                )
                        )
                )
        );
    }

    private void initMemoryBuilders() {
        memoryBuilders.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                        La foschia si addensa tra i tronchi candidi della foresta.
                        Una figura emerge lentamente dal bianco.
                        
                        Al suo interno percepisci decine di presenze sovrapposte,
                        frammenti di esistenze diverse intrecciate in un'unica forma.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Fondi la tua barriera mentale con l'essenza",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(30);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CONTACT);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Contieni l'entità entro un sigillo runico",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(20);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CONTACT);
                                        },
                                        end
                                )
                        )
                )
        );

        memoryBuilders.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                        La figura prende forma davanti a te come un'anomalia.
                        La sua sagoma muta continuamente, come se decine di identità
                        diverse stessero tentando di occupare lo stesso spazio.
                        
                        Non è caos. È organizzazione.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Analizza le frequenze di oscillazione dell'anomalia",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(30);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CONTACT);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Interroga l'aggregato tramite formule logiche",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(25);
                                            ctx.getWorldService().getMemory().record(EventType.MEMORY_CONTACT);
                                        },
                                        end
                                )
                        )
                )
        );
    }
}