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

public class FifthMissionFactory implements MissionContentFactory {

    private final Map<CharacterClass, ClassStepBuilder> custodianBuilders = new HashMap<>();

    public FifthMissionFactory() {
        initCustodianBuilders();
    }

    @Override
    public Mission create(Player player) {
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

        MissionStep custodianStep = custodianBuilders.getOrDefault(
                player.getCharacterClass(),
                (p, e) -> new MissionStep(
                        """
                            Ti fermi di fronte alla figura del Custode che si staglia nel cuore
                            della Biblioteca. Non possiedi un metodo preciso per analizzare
                            la sua natura, ma l'impatto visivo di questo archivio infinito ti
                            restituisce la vastità di ciò che sta andando perduto.
                            
                            Ogni punto di luce risponde alla sua presenza silenziosa.
                         """,
                        List.of(
                                new MissionChoice(
                                        "Ascolta in silenzio le parole del Custode",
                                        ctx -> ctx.getPlayer().gainExperience(15),
                                        e
                                ),
                                new MissionChoice(
                                        "Sostieni lo sguardo del Custode cercando risposte",
                                        ctx -> ctx.getPlayer().gainExperience(20),
                                        e
                                )
                        )
                )
        ).build(player, end);

        MissionStep alternativeLibraryStep = new MissionStep(
                """
                    Decidi di non toccare direttamente le sfere e ti muovi lungo i corridoi
                    sospesi della struttura, osservando le geometrie di luce da una distanza
                    di sicurezza.
                    
                    Da questa prospettiva, noti come l'intero spazio converga simmetricamente
                    verso il centro esatto dell'area, dove la figura immobile del Custode
                    sembra l'unico pilastro a sorreggere l'intero complesso.
                 """,
                List.of(
                        new MissionChoice(
                                "Raggiungi il Custode al centro dell'archivio",
                                ctx -> {},
                                custodianStep
                        )
                )
        );

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
                                "Avvicinati e tocca le sfere di memoria",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(20);
                                    ctx.getWorldService().getMemory().record(EventType.MEMORY_ARCHIVE_OBSERVED);
                                },
                                custodianStep
                        ),
                        new MissionChoice(
                                "Cammina tra le strutture senza interagire con i ricordi",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.MEMORY_ARCHIVE_OBSERVED),
                                alternativeLibraryStep
                        )
                )
        );

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
                                "Varca la ferita nella realtà seguendo la scia",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(10);
                                    ctx.getWorldService().getMemory().record(EventType.LIBRARY_FOUND);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH);
                                },
                                libraryStep
                        ),
                        new MissionChoice(
                                "Esamina i bordi della distorsione prima di entrare",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(15);
                                    ctx.getWorldService().getMemory().record(EventType.LIBRARY_FOUND);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_BREACH);
                                },
                                libraryStep
                        )
                )
        );

        return new Mission("La Biblioteca dei Perduti", start, 150, "biblioteca");
    }

    private void initCustodianBuilders() {
        custodianBuilders.put(CharacterClass.ARCANIST, (player, end) ->
                new MissionStep(
                        """
                            Le sfere di luce che compongono l'archivio reagiscono alla tua energia.
                            I flussi magici che collegano le memorie al Custode diventano visibili
                            ai tuoi occhi: una rete complessa di incantesimi di contenimento che
                            sfidano il decadimento della valle.
                            
                            Ti rendi conto che l'intera struttura consuma una quantità immensa di
                            potere primordiale solo per mantenere intatti i singoli ricordi.
                         """,
                        List.of(
                                new MissionChoice(
                                        "Interpreta e mappa i flussi energetici della rete",
                                        ctx -> ctx.getPlayer().gainExperience(35),
                                        end
                                ),
                                new MissionChoice(
                                        "Sintonizza la tua magia con i sigilli di contenimento",
                                        ctx -> ctx.getPlayer().gainExperience(30),
                                        end
                                )
                        )
                )
        );

        custodianBuilders.put(CharacterClass.SCHOLAR, (player, end) ->
                new MissionStep(
                        """
                            Riconosci immediatamente l'ordine tassonomico che regola la Biblioteca.
                            Le sfere non galleggiano a caso; sono disposte secondo criteri cronologici
                            e concettuali speculari a quelli delle colonne del santuario.
                            
                            Questo posto è a tutti gli effetti l'estensione logica finale dell'archivio,
                            progettato per impedire che l'incoerenza dell'Oblio corrompa i dati archiviati.
                         """,
                        List.of(
                                new MissionChoice(
                                        "Comprendi e decifra l'indice logico dell'archivio",
                                        ctx -> ctx.getPlayer().gainExperience(35),
                                        end
                                ),
                                new MissionChoice(
                                        "Confronta i dati presenti con i testi del santuario",
                                        ctx -> ctx.getPlayer().gainExperience(30),
                                        end
                                )
                        )
                )
        );

        custodianBuilders.put(CharacterClass.WANDERER, (player, end) ->
                new MissionStep(
                        """
                            Mentre cammini in questo vuoto, i tuoi passi incrociano la fine ideale di
                            tutti i sentieri che hai battuto finora. Riconosci frammenti visivi del
                            villaggio, della foresta e delle montagne all'interno delle sfere sospese.
                            
                            Ogni traccia che hai seguito nel mondo reale è nata o è stata salvata qui.
                            Il Custode si volta verso di te, riconoscendo il cammino che hai percorso.
                         """,
                        List.of(
                                new MissionChoice(
                                        "Riconosci i luoghi perduti ripercorrendo la tua mappa mentale",
                                        ctx -> ctx.getPlayer().gainExperience(35),
                                        end
                                ),
                                new MissionChoice(
                                        "Mostra i tuoi cimeli di viaggio per testimoniare il cammino",
                                        ctx -> ctx.getPlayer().gainExperience(30),
                                        end
                                )
                        )
                )
        );
    }
}