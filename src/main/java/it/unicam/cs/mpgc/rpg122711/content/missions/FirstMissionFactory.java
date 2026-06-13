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

public class FirstMissionFactory implements MissionContentFactory {

    private final Map<CharacterClass, ClassStepBuilder> classBuilders = new HashMap<>();

    public FirstMissionFactory() {
        initClassBuilders();
    }

    @Override
    public Mission create(Player player) {
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

        MissionStep classStep = classBuilders
                .getOrDefault(player.getCharacterClass(), this::buildDefaultClassStep)
                .build(player, end);

        MissionStep diaryDecidedStep = new MissionStep(
                """
                Stringi il diario tra le mani. Le parole scritte tra queste pagine
                sembrano lottare per non sbiadire del tutto sotto i tuoi occhi.
                 Il peso di questa memoria ora è parzialmente tuo.
                
                Voltando lo sguardo verso l'uscita della chiesa, noti che la nebbia
                sulle rovine esterne si è addensata, rivelando nuove ombre.
                """,
                List.of(
                        new MissionChoice(
                                "Esci e perlustra i dintorni del villaggio",
                                ctx -> {},
                                classStep
                        ),
                        new MissionChoice(
                                "Abbandona il villaggio seguendo il sentiero",
                                ctx -> {},
                                end
                        )
                )
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
                List.of(
                        new MissionChoice(
                                "Leggi e prendi il diario",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(15);
                                    ctx.getWorldService().getMemory().record(EventType.DIARY_FOUND);
                                },
                                diaryDecidedStep
                        ),
                        new MissionChoice(
                                "Non toccare il diario e torna alle rovine",
                                ctx -> {},
                                classStep
                        )
                )
        );

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
                                "Esplora le rovine esterne",
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

        return new Mission("La Rovina Silente", start, 50, "start");
    }

    public MissionStep buildDefaultClassStep(Player player, MissionStep end) {
        return new MissionStep(
                """
                Non possiedi la sensibilità degli arcanisti né gli strumenti
                degli studiosi.
                
                Eppure c'è qualcosa che attira immediatamente la tua attenzione.
                Tracce. Solchi impressi nella polvere.
                
                Seguono un percorso incerto tra le rovine e sembrano anche
                relativamente recenti.
                
                Ti inginocchi per osservarli meglio. Chiunque sia passato di qui 
                si è fermato più volte, come se stesse cercando una via di fuga
                o una risposta.
                """,
                List.of(
                        new MissionChoice(
                                "Segui le tracce fino in fondo",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(15);
                                    ctx.getWorldService().getMemory().record(EventType.TRACKS_FOLLOWED);
                                },
                                end
                        ),
                        new MissionChoice(
                                "Ignora le tracce e affrettati verso l'uscita",
                                ctx -> ctx.getPlayer().gainExperience(5),
                                end
                        )
                )
        );
    }

    private void initClassBuilders() {
        classBuilders.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                        Oltre le ultime case del villaggio, qualcosa attira il tuo sguardo.
                         
                        Sulle pietre consumate compaiono dei segni deboli, come incisioni
                        emerse dall'interno della materia stessa.
        
                        Non sono rune nel senso comune del termine.
                        Sembrano piuttosto impronte di ricordi che si rifiutano di scomparire.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Assorbi l'energia residua delle incisioni",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(25);
                                            ctx.getWorldService().getMemory().record(EventType.RUNES_READ);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Trascrivi i segni e prosegui oltre",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(15);
                                            ctx.getWorldService().getMemory().record(EventType.RUNES_READ);
                                        },
                                        end
                                )
                        )
                )
        );

        classBuilders.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                        Osservando le rovine più da vicino, inizi a notare particolari
                        impossibili da ignorare.
        
                        Alcuni edifici sembrano occupare meno spazio all'esterno che
                        all'interno. Finestre e porte non coincidono.
                        Distanze che dovrebbero essere identiche risultano diverse.
                        
                        La logica geometrica stessa si sta sgretolando.
                        """,
                        List.of(
                                new MissionChoice(
                                        "Mappa geometricamente la distorsione",
                                        ctx -> {
                                            ctx.getPlayer().gainExperience(20);
                                            ctx.getWorldService().getMemory().record(EventType.DISTORTION_ANALYZED);
                                        },
                                        end
                                ),
                                new MissionChoice(
                                        "Evita le anomalie fisiche e passa oltre",
                                        ctx -> ctx.getPlayer().gainExperience(10),
                                        end
                                )
                        )
                )
        );
    }
}