package it.unicam.cs.mpgc.rpg122711.content.missions;
import it.unicam.cs.mpgc.rpg122711.content.MissionContentFactory;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionChoice;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionStep;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import java.util.List;

public class SeventhMissionFactory implements MissionContentFactory {

    @Override
    public Mission create(Player player) {
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
                But tre direzioni di coerenza:
                
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

        MissionStep coreStepAnalytical = new MissionStep(
                """
                Ti imponi di esaminare matematicamente le fluttuazioni del nucleo.
                Ogni fallimento di possibilità genera un microscopico scarto energetico,
                un'equazione interrotta che svela le regole matematiche del nulla.
                
                La stabilità minima dell'esistenza non è un miracolo, è un bilanciamento
                forzato di forze opposte, una gabbia logica che ora giace aperta.
                """,
                List.of(
                        new MissionChoice(
                                "Usa questa consapevolezza per definire il finale",
                                ctx -> ctx.getPlayer().gainExperience(20),
                                finalStep
                        )
                )
        );

        MissionStep coreStepEmotional = new MissionStep(
                """
                Invece di analizzare, ti apri all'impatto emotivo di quei miliardi
                di tentativi falliti. Senti il peso di infinite esistenze mai nate,
                di sogni che non hanno ricevuto nemmeno lo spazio di un respiro.
                
                Il Custode ti guarda con una pietà priva di calore, comprendendo che
                stai portando sulle spalle il peso emotivo dell'intera struttura.
                """,
                List.of(
                        new MissionChoice(
                                "Porta questo fardello verso la fine",
                                ctx -> ctx.getPlayer().gainExperience(20),
                                finalStep
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
                                "Analizza razionalmente la struttura del nucleo",
                                ctx -> ctx.getPlayer().gainExperience(40),
                                coreStepAnalytical
                        ),
                        new MissionChoice(
                                "Sintonizzati sul dolore delle possibilità fallite",
                                ctx -> ctx.getPlayer().gainExperience(40),
                                coreStepEmotional
                        )
                )
        );

        MissionStep alternativeStartStep = new MissionStep(
                """
                Cerchi di ritardare l'impatto fissando lo sguardo verso lo squarcio
                da cui provieni. Per un istante intravedi i riflessi deformati della
                Valle di Nhal e della Biblioteca, come geometrie bidimensionali prive
                di un vero spessore.
                
                La transizione è ormai asimmetrica; non puoi tornare indietro, ma puoi
                scegliere come orientare la tua coscienza prima del contatto definitivo.
                """,
                List.of(
                        new MissionChoice(
                                "Abbandona le vecchie immagini e tocca il nucleo",
                                ctx -> {},
                                coreStep
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
                                "Immergiti totalmente nel meccanismo centrale",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH),
                                coreStep
                        ),
                        new MissionChoice(
                                "Trattieni la tua identità un istante prima del contatto",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH),
                                alternativeStartStep
                        )
                )
        );

        return new Mission("Il Cuore dell’Oblio", start, 200, "finale");
    }
}