package it.unicam.cs.mpgc.rpg122711.content;
import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionChoice;
import it.unicam.cs.mpgc.rpg122711.domain.mission.MissionStep;
import it.unicam.cs.mpgc.rpg122711.domain.world.EventType;
import it.unicam.cs.mpgc.rpg122711.domain.world.WorldMemory;

import java.util.List;

public class MissionContent {
    public static Mission firstMission(Player player) {

        // finale della missione
        MissionStep end = new MissionStep(
                """
                        Mentre i confini del villaggio sfumano alle tue spalle, 
                        un brivido scende lungo la schiena: 
                        non sei solo nel vuoto.
                        
                        Il vento si fa voce, portando con sé il peso 
                        di sillabe che il tempo aveva cancellato.
                        
                        Un'antica reminiscenza riemerge dall'oblio.
                        """,
                List.of()
        );

        // percorso chiesa
        MissionStep churchStep = new MissionStep(
                """
                        La navata è un sepolcro di ombre, dove il silenzio 
                        pesa più della pietra.
                        
                        L'altare, un tempo cuore pulsante di fede, giace ora 
                        soffocato da un sudario di polvere e oblio.
                        
                        All'ombra di un simulacro infranto, tra i detriti 
                        della devozione, riemerge un diario dalle pagine 
                        logore e stanche.
                        
                        Gli ultimi scritti sono graffi di follia: 
                        narrano di uomini che, uno dopo l’altro, 
                        hanno smarrito il suono del proprio nome.
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

        // percorso rovine
        MissionStep classStep = buildClassStep(player, end);

        // step iniziale
        MissionStep start = new MissionStep(
                """
                        Un villaggio muto emerge dalle nebbie, fermo in 
                        un'eterna e immobile attesa.
                        
                        Nessun respiro ne increspa l'aria, nessun eco turba 
                        il vuoto che regna tra i vicoli.
                        
                        Le dimore si ergono intatte, monumenti di pietra 
                        che paiono aver voltato le spalle al tempo da 
                        ere immemorabili.
                        
                        Un’aura innaturale grava su ogni cosa: è il 
                        crepuscolo della realtà, dove il luogo stesso 
                        sembra scivolare lentamente nell'abisso dell'oblio.
                     """,
                List.of(
                        new MissionChoice(
                                "Eplora le rovine",
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

        return new Mission(
                "La Rovina Silente",
                start,
                50
        );
    }

    private static MissionStep buildClassStep(Player player, MissionStep end) {

        // arcano
        if (player.getCharacterClass() == CharacterClass.ARCANIST) {
            return new MissionStep(
                    """
                            Oltre il confine delle rovine, la trama del mondo sussulta. 
                            Dalle viscere della pietra scura, iniziano a 
                            trasudare segni di una luce antica e febbrile.
                            
                            Non sono ferite inferte dal ferro, né solchi scavati 
                            dalla mano dell'uomo.
                            
                            Sono memorie vivide, impresse nella carne stessa 
                            della realtà; visioni proibite a cui solo il 
                            tuo sguardo, tra mille, ha il fardello di assistere.
                          """,
                    List.of( new MissionChoice(
                                    "Leggere le rune",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(20);
                                        ctx.getWorldService().getMemory().record(EventType.RUNES_READ);
                                    },
                                    end
                            )
                    )
            );
        }

        // studioso
        if (player.getCharacterClass() == CharacterClass.SCHOLAR) {
            return new MissionStep(
                    """
                            La geometria delle rovine sfida ogni logica mortale,
                            piegandosi in angoli che l'occhio fatica a seguire.
                            
                            Le soglie di queste dimore tradiscono i sensi: l'oscurità,
                            racchiusa all'interno, si espande oltre ogni confine fisico,
                            come se le stanze contenessero più vuoto di quanto le mura
                            possano reggere.
                            
                            Una forza innominabile sta riscrivendo l'essenza stess del luogo,
                            erodendone i lineamenti originali.
                            
                            Non è il capriccio di un incanto comune, ma il collasso della
                            realtà che dimentica la propria forma
                          """,
                    List.of( new MissionChoice(
                                    "Analizza la distorsione",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(15);
                                        ctx.getWorldService().getMemory().record(EventType.DISTORTION_ANALYZED);
                                    },
                                    end
                            )
                    )
            );
        }

        //viandante
        return new MissionStep(
                """
                        Nessun fremito arcano scuote l'aria, nessuna maledizione
                        grava visibilmente sui tuoi sensi.
                        
                        Eppure, il silenzio è una lama gelida che preme contro la
                        gola, troppo assoluto per essere naturale.
                        
                        Tra il sudario di polvere che ammanta il suolo, scorgi dei 
                        solchi nitidi, ancora freschi: tracce di un cammino che ha violato
                        la quiete di questo sepolcro.
                        
                        Qualcuno ha sfidato l'oblio prima di te e il calore della sua scia
                        indigia ancora tra le ombre.
                     """,
                List.of( new MissionChoice(
                                "Segui le tracce",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(15);
                                    ctx.getWorldService().getMemory().record(EventType.TRACKS_FOLLOWED);
                                },
                                end
                        )
                )
        );
    }

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

        MissionStep forestStep = buildForestStep(player, end);
        MissionStep memoryStep = buildMemoryStep(player, end);

        MissionStep start = new MissionStep(
                """
                        Il richiamo delle rovine non si è mai pacato; è diventato un battito
                        sordo che ti ha trascinato oltre i margini del mondo, dove le mappe
                        smettono di respirare.
                        
                        Sei giunto in un luogo che la storia ha rigettato, un vuoto bianco
                        che nessun registro ha saputo trattenere.
                        
                        Una foresta. Bianca. Immobile.
                        
                        Un labirito di rami d'avorio che pare trattenere il fiato, sospeso 
                        in un'attesa millenaria: non aspetta altro che il tuo sguardo per
                        tornare ad esistere.
                      """,
                List.of( new MissionChoice(
                                "Esamina la foresta",
                                ctx -> {
                                    ctx.getWorldService().getMemory().record(EventType.FOREST_OBSERVED);
                                },
                                forestStep
                        ),
                        new MissionChoice(
                                "Segui l'eco",
                                ctx -> {
                                    ctx.getWorldService().getMemory().record(EventType.ECHO_FOLLOWED);
                                },
                                memoryStep
                        )
                )
        );

        return new Mission(
                "L'eco nella Foresta Bianca",
                start,
                75
        );
    }

    private static MissionStep buildForestStep(Player player, MissionStep end) {
        if(player.getCharacterClass() == CharacterClass.ARCANIST) {
            return new MissionStep(
                    """
                            Tra questi rami non scorre linfa ma un silenzio che rigetta la vita.
                            
                            Ciò che vedi non è che un'architettura di puro incanto, cristallizzata
                            in un'eterna e innaturale sospensione.
                            
                            Ogni tronco, ogni scheggia d'avorio, è una prigione per frammenti
                            di un sortilegio ancestrale che si rifiuta di morire.
                         """,
                    List.of(new MissionChoice(
                                    "Analizza i residui magici",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(20);
                                        ctx.getWorldService().getMemory().record(EventType.FOREST_ANALYZED);
                                    },
                                    end
                            )
                    )
            );
        }

        if (player.getCharacterClass() == CharacterClass.SCHOLAR) {

            return new MissionStep(
                    """
                            La selva rinnega ogni legge terrena, piegandosi ad una logica che non
                            appartiene alla terra. 
                            
                            Non è carne, né fogliame, né linfa.
                            
                            E' un pensiero puro che ha preso il sopravvento sulla materia:
                            un'architettura di dati senzienti, organizzata nell'oscura e 
                            ingannevole parvenza di una forma biologica.
                         """,
                    List.of(new MissionChoice(
                                    "Studia la struttura",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(15);
                                        ctx.getWorldService()
                                                .getMemory()
                                                .record(EventType.FOREST_ANALYZED);
                                    },
                                    end
                            )
                    )
            );
        }

        return new MissionStep(
                    """
                            Questo luogo rigetta ogni pretesa di comprensione; non esiste 
                            sentiero, né intelletto, capace di tracciarne il senso.
                            
                            Eppure, mentre avanzi, senti il peso di una verità atroce: 
                            ogni tuo passo non è che un solco già scavato.
                            
                            Ti muovi lungo una trama già scritta, un’eredità di movimenti 
                            che non ti appartengono, ma che sei condannato a ricalcare.
                         """,
                List.of( new MissionChoice(
                                "Procedi",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(15);
                                    ctx.getWorldService()
                                            .getMemory()
                                            .record(EventType.FOREST_TRAVERSED);
                                },
                                end
                        )
                )
        );
    }

    private static MissionStep buildMemoryStep(Player player, MissionStep end) {

        String text;

        if (player.getCharacterClass() == CharacterClass.ARCANIST) {
            text = """
                    La foschia si raggruma, torcendosi in un simulacro che sfida ogni 
                    stabilità.
                    Non è carne, né spirito, né creatura nata dal grembo della natura.
                    
                    È un’aberrazione di memoria: un rimasuglio di esistenza che ha 
                    rigettato la pace della dissoluzione, aggrappandosi con i denti 
                    ai lembi sfilacciati della realtà.
                   """;
        }

        else if (player.getCharacterClass() == CharacterClass.SCHOLAR) {
            text = """
                    Questa aberrazione non è il caos, ma il frutto di una logica ferrea 
                    e sotterranea.
                    
                     È un nodo di memorie che ha smesso di vagare, un coagulo di tempo 
                     che ha trovato la forza di stabilizzarsi nel vuoto.
                    
                     Non è più solo un ricordo: è un sistema che ha appreso a 
                     respirare, un'entità che ha rubato all'oblio i tratti di 
                     una propria, mostruosa identità.
                   """;
        }

        else {
            text = """
                    Un’ombra si distacca dal candore dei tronchi, una presenza che 
                    lacera la trama della nebbia.
                    
                    Non conosce il peso del passo, né il ritmo del respiro: il suo 
                    moto non è un camminare, ma uno spostamento della realtà stessa.
                    
                    Semplicemente esiste, imposta ai tuoi sensi come un’eco che ha 
                    trovato la forza di farsi carne.
                   """;
        }

        return new MissionStep(
                text,
                List.of( new MissionChoice(
                                "Avvicinati",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService()
                                            .getMemory()
                                            .record(EventType.MEMORY_CONTACT);
                                },
                                end
                        )
                )
        );
    }

    public static Mission thirdMission(Player player) {

        // finale della missione
        MissionStep end = new MissionStep(
                """
                        Nel nucleo del santuario, laddove il tempo ristagna, pulsa una sfera di
                        luce esangue, simile ad un astro morente.
                        
                        Al contatto del tuo tocco, l'argine crolla: migliaia di esistenze deflagrano
                        nella tua mente come lampi accecanti.
                        
                        Volti che non sorridono più.
                        
                        Nomi che la pietra ha sputato via.
                        
                        Vite ridotte a cenere, disperse nel vuoto.
                        
                        Per un battito di ciglia, l'orrore ti è manifesto: percepisci l'abisso famelico
                        dell'oblio che sta divorando le fondamenta stesse del mondo.
                        
                        Qualcuno sta tessendo una trama con questi brandelli d'anima.
                        E attraverso i loro occhi spenti, ti sta osservando.
                     """,
                List.of()
        );

        MissionStep memoryStep = buildSanctuaryStep(player, end);

        // step iniziale
        MissionStep start = new MissionStep(
                """
                        Dinanzi al tuo cammino, le vette si ergono come titani sprofondati in un
                        sogno millenario, le loro ombre lunghe come sentenze.
                        
                        Trascinato dal riverbero che ancora vibra nelle tue ossa dalla Foresta Bianca,
                        calpesti un suolo che ha ripudiato ogni cartografia umana.
                        
                        Incastonato nel ventre spietato della roccia, sorve un antico santuario,
                        una cicatrice di pietra che sfida il tempo.
                        
                        Le sue fauci sono spalancate.
                        
                        Non è abbandono: è un invito silenzioso, come se la struttura stessa avesse trattenuto
                        il fiato attendendo il rintocco dei tuoi passi.
                     """,
                List.of( new MissionChoice(
                                "Entra nel santuario",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.SANCTUARY_DISCOVERED),
                                memoryStep
                        )
                )
        );

        return new Mission(
                "Il Santuario degli Assenti",
                start,
                100
        );
    }

    private static MissionStep buildSanctuaryStep(Player player, MissionStep end) {

        if(player.getCharacterClass() == CharacterClass.ARCANIST) {
            return new MissionStep(
                    """
                            Le pareti del santuario sono una pergamena di pietra, trasudanti gli
                            stessi gilfi che hai visto strisciare tra le rovine del mondo.
                            
                            Ma ora, quel linguaggio muto ha smesso di essere un enigma: il velo si è
                            squarciato e la tua mente ne accoglie il senso atroce.
                            
                            Quelle incisioni non sono formule di potere, né trame di antichi incantesimi.
                            
                            Sono necrologi di luce.
                            
                            Ogni runa è il sepolcro di un'anima, il sigillo che custodisce il ricordo
                            di una vita che la storia ha già vomitato nel nulla
                         """,
                    List.of( new MissionChoice(
                                    "Studia il nucleo di memoria",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(30);
                                        ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                    },
                                    end
                            )
                    )
            );
        }

        if (player.getCharacterClass() == CharacterClass.SCHOLAR) {
            return new MissionStep(
                    """
                            La struttura del santuario deride ogni legge che il tuo intelletto 
                            osa professare, un’architettura eretica che distorce il senso stesso 
                            della materia.
                            
                            Le statue, imponenti e innaturali, sembrano rivendicare un volume 
                            che la pietra non dovrebbe concedere, occupando più spazio di 
                            quanto questo luogo possa fisicamente contenere.
                            
                            È un’esistenza sospesa, un lembo di terra ferito che palpita nel 
                            crepuscolo tra la memoria perduta e la realtà che svanisce.
                            
                            In questo cuore di silenzio, comprendi la verità: ogni anomalia, 
                            ogni sussulto della realtà che hai inseguito finora, converge 
                            qui, in questo unico punto di collasso.
                         """,
                    List.of( new MissionChoice(
                                    "Analizza il fenomeno",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(25);
                                        ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                    },
                                    end
                            )
                    )
            );
        }

        if (player.getCharacterClass() == CharacterClass.WANDERER) {
            return new MissionStep(
                    """
                            Tra la polvere secolare del santuario, i tuoi occhi scorgono dei 
                            solchi familiari, tracce che riemergono come fantasmi dal passato.
                            
                            Sono le stesse impronte che avevi braccato molto tempo fa, quando 
                            il viaggio era ancora una promessa e non un tormento.
                            
                            Non sono i segni lasciati da una fiera, né gli artigli di qualche 
                            mostro partorito dall'ombra.
                            
                            Appartengono a un viandante, un'anima che ha calpestato il tuo 
                            medesimo fango e respirato la tua stessa disperazione.
                            
                            Qualcuno che ha percorso ogni singolo passo del tuo calvario, 
                            giungendo a questo epilogo prima che tu potessi rivendicarlo.
                         """,
                    List.of( new MissionChoice(
                                    "Segui le tracce",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(25);
                                        ctx.getWorldService().getMemory().record(EventType.MEMORY_CORE_FOUND);
                                    },
                                    end
                            )
                    )
            );
        }

        return new MissionStep(
                "Nulla accade.",
                List.of( new MissionChoice(
                                "Continua",
                                ctx -> {},
                                end
                        )
                )
        );
    }

    public static Mission fourthMission(Player player) {

        // passo finale
        MissionStep end = new MissionStep(
                """
                        La torre si sgretola nel silenzio, un relitto di pietra che affoga sotto
                        un cielo privato del conforto delle stelle.
                        
                        Sulla sommità, una sagoma avvolta in pesanti vesti color dell'ebano scruta l'orizzonte,
                        immobile come una statua erosa dal tempo.
                        
                        Per un battito di cuore, la rabbia divampa: credi di aver finalmente scovato 
                        l'artefice di ogni rovina, l'origine del tuo tormento.
                        
                        Ma l'uomo non emana ostilità, né impugna il ferro.
                        
                        Ti trafigge con uno sguardo muto, un abisso di comprensione che non chiede perdono.
                        
                        Poi la sua voce infrange l'aria come un vetro antico:
                            'Ogni memoria che colgo è un'anima strappata al nulla; un frammento che il mondo,
                            nella sua cecità, avrebbe condannato all'oblio.'
                        
                        Prima che il tuo grido possa prender forma, la sua figura si sfilaccia, diventando nebbia tra
                        le nebbie, svanendo nell'impalpabile.
                        
                        Resti solo, circondato dal freddo.
                        
                        E nel vuoto che ha lasciato, la tua mente non trova risposte ma solo un labirinto
                        ancora più profondo e crudele.
                     """,
                List.of()
        );

        MissionStep custodianStep = buildCustodianStep(player, end);

        MissionStep villageStep = new MissionStep(
                """
                        Gli abitanti della valle errano come ombre alla deriva, privati dal
                        timone della propria coscienza.
                        
                        Nessuno sa più quale nome abbia mai fatto vibrare la propria voce.
                        Nessuno riconosce il sangue del proprio sange, né il calore della casa.
                        
                        Respirano. Battono ciglio. Sono vivi.
                        
                        Eppure, nei loro sguardi si spalanca un deserto bianco: non rimane alcuna
                        traccia della storia che li ha resi uomini.
                        
                        Un vecchio ti artiglia il braccio con la forza disperata dei morenti, la pelle
                        tremante come carta ingiallita.
                        
                        Prima di svanire nel nulla del suo stesso spirito, esala un unico, gelido sussurro:
                        'Il Custode è passato.'
                     """,
                List.of( new MissionChoice(
                                "Prosegui verso la torre",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(20);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_MENTIONED);
                                },
                                end
                        )
                )
        );

        // step iniziale
        MissionStep start = new MissionStep(
                """
                        Oltrepassata la soglia del Santuario degli Assenti, una visione si incrosta
                        nei tuoi pensieri, un parassita che si nutre della tua veglia.
                        
                        Una città smisurata si stende dinanzi al tuo occhio interiore.
                        
                        Deserta.
                        
                        Immobile.
                        
                        Un cadavere di pietra dove, al centro esatto, una sagoma incappucciata scruta
                        il vuoto, come un sovrano che regna sul nulla.
                        
                        Sussurrato dalle tracce riemerse dall'abisso del santuario, il tuo cammino ti 
                        conduce infine ai margini della Valle di Nhal.
                        
                        Qui, una nebbia nera e densa come inchiostro cola su ogni cosa, soffocando
                        il mondo sotto un sudario d'ombra.
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

    private static MissionStep buildCustodianStep(Player player, MissionStep end) {

        if (player.getCharacterClass() == CharacterClass.ARCANIST) {
            return new MissionStep(
                    """
                            L'aria è pesante, saturata da una polvere magica che brucia i polmoni,
                            un sedimento di incanti che non somiglia a nulla di ciò che hai osato
                            studiare tra i tomi del passato.
                            
                            Non sono formule, non sono anatemi.
                            
                            E' il sottoprodotto di un'operazione metodica e spietata: qualcuno sta
                            sradicando i ricordi dalla carne delle persone, recidendo i legami tra
                            l'anima e il vissuto per preservarli altrove.
                            
                            E' un furto che ha il sapore amaro della salvezza: un tentativo disperato
                            di sottrarre brandelli d'esistenza a una forza ancora più immensa e famelica,
                            un'oscurità che non ammette superstiti.
                         """,
                    List.of( new MissionChoice(
                                    "Analizza i residui",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(25);
                                        ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_MAGIC_DISCOVERED0);
                                    },
                                    end
                            )
                    )
            );
        }

        if (player.getCharacterClass() == CharacterClass.SCHOLAR) {
            return new MissionStep(
                    """
                            Le distorsioni che dilaniano la realtà non sono figlie del caos, ma obbediscono
                            a un'architettura lucida e spietata.
                            
                            Le memorie non vengono annientate, né disperse nel vento dell'oblio.
                            
                            Vengono traslate.
                            
                            Catalogate con fredda precisione.
                            
                            Sigillate in un sonno senza fine.
                            
                            Sotto i tuoi occhi, tra le pieghe di un mondo che muore, qualcuno sta 
                            assemblando un archivio impossibile: una biblioteca di anime strappate 
                            al tempo per sfidare l'eternità.
                         """,
                    List.of( new MissionChoice(
                                    "Studia il fenomeno",
                                    ctx -> {
                                        ctx.getPlayer().gainExperience(25);
                                        ctx.getWorldService().getMemory().record(EventType.MEMORY_STORAGE_DISCOVERED);
                                    },
                                    end
                            )
                    )
            );
        }

        return new MissionStep(
                """
                        Sul fango di questa terra desolata, i tuoi occhi riconoscono istantaneamente
                        un presagio familiare: solchi incisi con una precisione che gela il sangue.
                        
                        Sono le medesime impronte che hai visto affiorare tra le cenerei del villaggio
                        dimenticato, spettri di un cammino che sembra non avere fine.
                        
                        Le hai braccate per centinaia di chilometri, inseguendo un'ombra che pare farsi
                        beffe della distanza e della stanchezza.
                        
                        Ora la verità si fa strada tra i tuoi pensieri come un veleno: qualunque sia l'entità
                        che traccia questo sentiero, essa è il filo invisibile che cuce insieme ogni singola
                        anomalia, ogni squarcio nella realtà che hai osato sfidare.
                     """,
                List.of(  new MissionChoice(
                                "Segui le impronte",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(25);
                                    ctx.getWorldService().getMemory().record(EventType.CUSTODIAN_TRACKS_FOUND);
                                },
                                end
                        )
                )
        );
    }

    public static Mission fifthMission (Player player) {

        // step finale
        MissionStep end = new MissionStep(
                """
                        Il Cuore dell’Oblio arresta il suo rintocco ancestrale. O forse, 
                        nell'oscurità del tuo petto, accade l'opposto: è tra le tue costole 
                        che ora quel battito ha trovato una nuova, terribile dimora.
                        
                        Il confine è crollato, l'argine è polvere: non esiste più 
                        distinzione tra il vuoto di ciò che è stato dimenticato e la 
                        sostanza di ciò che chiamavi reale.
                        
                        Il mondo ha smesso di essere un luogo, una dimora, una terra: 
                        è divenuto una memoria colossale che ha appreso il tormento 
                        del pensiero.
                        
                        E tu, in questo silenzio assoluto che precede la fine, sei la 
                        sua ultima, disperata domanda.
                     """,
                List.of()
        );

        MissionStep arcanistPath = new MissionStep(
                """
                        Le rune hanno abbandonato la fredda prigionia della pietra per 
                        reclamare un dominio più intimo: ora sono incise nel vuoto 
                        pneumatico che separa un pensiero dall'altro.
                        
                        Ogni simbolo che la tua mente osa decifrare agisce come un acido, 
                        dissolvendo un frammento prezioso della tua identità, un tassello 
                        del tuo io che svanisce per sempre.
                        
                        In questo luogo sacrilego, la conoscenza non è un dono, ma un 
                        baratto spietato.
                        
                        Comprendere significa perdere.
                     """,
                List.of(
                        new MissionChoice(
                                "Accetta la conoscenza",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(40);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_TRUTH_REVEALED);
                                },
                                end
                        )
                )
        );

        MissionStep scholarPath = new MissionStep(
                """
                        L’architettura stessa del creato si sgretola in una manifestazione 
                        di pura impossibilità, un collasso che non ammette spettatori, 
                        ma solo testimoni della fine.
                        
                        Ogni legge fisica, ogni certezza che credevi scolpita nel marmo 
                        del cosmo, si rivela per ciò che è sempre stata: un’ipotesi 
                        fragile, un sussurro dimenticato nel rumore del tempo.
                        
                        E ora, mentre la realtà si ripiega su se stessa, ti ritrovi a 
                        leggere la sua definitiva, spietata confutazione.
                     """,
                List.of(
                        new MissionChoice(
                                "Analizza il nucleo",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(35);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_STRUCTURE_ANALYZED);
                                },
                                end
                        )
                )
        );

        MissionStep wandererPath = new MissionStep(
                """
                        L'orizzonte è svanito e con esso l'illusione di una direzione: non esiste
                        più strada, né meta.
                        
                        Davanti a te si stendono solo percorsi contorti che si ripiegano ossessivamente
                        su se stessi, simili a cicatrici mal cicatrizzate sulla pelle della realtà.
                        
                        Eppure, un brivido viscerale ti percorre la schiena mentre continui a riconoscere
                        ogni svolta, ogni sasso, ogni ombra lungo il cammino...
                        
                        ...anche se i tuoi piedi non hanno mai calpestato questo fango prima d'ora.
                     """,
                List.of(
                        new MissionChoice(
                                "Continua a camminare",
                                ctx -> {
                                    ctx.getPlayer().gainExperience(30);
                                    ctx.getWorldService().getMemory().record(EventType.OBLIVION_PATH_WALKED);
                                },
                                end
                        )
                )
        );

        MissionStep start = new MissionStep(
                """
                        Il mondo abbandona finalmente la sua maschera e smette di fingere una coerenza
                        che non gli appartiene più.
                        
                        Le montagne si spogliano della loro illusione di pietra; le foreste rinunciano
                        alla pretesa di essere alberi e radici.
                        
                        Non sono che sedimenti di memoria, strati di ricordi sovrapposti che vibrano in
                        un'architettura di pura nostalgia e dolore.
                        
                        E li, nel punto più recondito e abbietto dell'esistenza, dove la realtà si assottiglia
                        fino a spezzarsi... qualcosa sta attendendo il tuo arrivo.
                     """,
                List.of(
                        new MissionChoice(
                                "Avanza verso il nucleo",
                                ctx -> {
                                    ctx.getWorldService().getMemory().record(EventType.ENTERED_OBLIVION_CORE);
                                },
                                switch (player.getCharacterClass()) {
                                    case ARCANIST -> arcanistPath;
                                    case SCHOLAR -> scholarPath;
                                    default -> wandererPath;
                                }
                        )
                )
        );

        return new Mission(
                "Il Cuore dell'Oblio",
                start,
                150
        );
    }


    // parte finale
    public static Mission finalMission(Player player, WorldMemory memory) {

        if (player.getCharacterClass() == CharacterClass.ARCANIST
                && memory.contains(EventType.OBLIVION_TRUTH_REVEALED)) {
            return arcanistEnding();
        }

        if (player.getCharacterClass() == CharacterClass.SCHOLAR
                && memory.contains(EventType.OBLIVION_STRUCTURE_ANALYZED)) {
            return scholarEnding();
        }

        return wandererEnding();
    }


    private static Mission arcanistEnding() {
        MissionStep end = new MissionStep(
                """
                        La tua essenza individuale è evaporata, dispersa come nebbia tra i gilfi di un cosmo
                        che non ha più bisogno della tua carne.
                        
                        Non esisti più come uomo, né come spettatore: sei diventato puro linguaggio, la 
                        sintassi stessa che regge il vuoto.
                        
                        L’Oblio non è stato la tua fine, né il tuo carnefice; non ti ha distrutto, ma ti ha 
                        riscritto, cancellando i tuoi margini per farti parte della sua trama infinita.
                        
                        Ora il tuo destino è segnato: ogni frammento, ogni nome, ogni storia che il mondo 
                        condannerà al silenzio, dovrà passare attraverso di te per trovare la sua ultima dimora.
                     """,
                List.of()
        );

        MissionStep start = new MissionStep(
                """
                        Le rune hanno infranto la barriera della carne, rinunciando alla loro fredda 
                        alterità per scorrere nelle tue vene come una seconda, febbrile circolazione.
                        
                        Il tuo sangue non è più solo linfa vitale, ma un inchiostro primordiale che 
                        trasporta segreti troppo pesanti per essere pronunciati.
                        
                        L’universo intero, nella sua vasta e indifferente coscienza, ora volge lo 
                        sguardo verso di te: ti riconosce come il nodo centrale, il punto di giuntura 
                        supremo dove converge ogni singola dimenticanza del creato.
                     """,
                List.of(
                        new MissionChoice(
                                "Accetta la trasformazione",
                                ctx -> ctx.getWorldService().getMemory()
                                        .record(EventType.OBLIVION_TRUTH_REVEALED),
                                end
                        )
                )
        );

        return new Mission(
                "Finale — Il Nuovo Linguaggio dell’Oblio",
                start,
                0
        );
    }

    private static Mission scholarEnding() {
        // step finale
        MissionStep end = new MissionStep(
                """
                        Il velo è caduto e la verità si è spalancata dinanzi a te, priva di segreti.
                        Hai compreso tutto. 
                        
                        E proprio per questo, per questa tua lucidità sacrilegia... non puoi più appartenere
                        al consorzio del mondo.
                        
                        La conoscenza assoluta è un fuoco freddo che non lascia spazio alla vita, un parassita
                        che divora la carne e l'illusione per nutrire lo spirito.
                        
                        Resti immobile, un monumento di pura coscienza, unico osservatore di un sistema immenso
                        che ha smesso di esistere, o che non ha più bisogno di essere osservato.
                     """,
                List.of()
        );

        // step iniziale
        MissionStep start = new MissionStep(
                """
                        Ogni architettura, ogni angolo recondito del creato si schiude dinanzi al
                        tuo sguardo come un tono già letto e consumato.
                        
                        Il velo del ubbio è squarciato: non c'è più spazio per il mistero,
                        né per l'incanto dell'ignoto.
                        
                        Rimane solo la fredda certezza della fine, la sequenza geometrica di conseguenze
                        inevitabili che si abbatte sul mondo.
                     """,
                List.of(
                        new MissionChoice(
                                "Osserva la verità finale",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.OBLIVION_STRUCTURE_ANALYZED),
                                end
                        )
                )
        );

        return new Mission(
                "Finale - La comprensione perfetta",
                start,
                0
        );
    }

    private static Mission wandererEnding() {
        // step finale
        MissionStep end = new MissionStep(
                """
                        La ferita non si è rimarginata: il mondo non è guarito.
                        
                        Ma lo spaventoso fragore della fine si è placato e la terra ha smesso finalmente
                        di crollare nell'abisso.
                        
                        Ciò che rimane è un relitto instabile, un'architettura imperfetta, eppure
                        disperatamente, magnificamente viva.
                        
                        E ora, nell'alba di questa nuova era, accade il miracolo più spaventoso: per la prima
                        volta, dall'inizio del tempo, il creato esiste senza il peso di alcun osservatore.
                     """,
                List.of()
        );

        // step iniziale
        MissionStep start = new MissionStep(
                """
                        Il labirinto non nasconde alcun segreto nell'ombra, nessuna rivelazione finale:
                        non c'è alcuna verità da comprendere.
                        
                        L'intelletto deve arrendersi, la mente deve tacere.
                        
                        Rimane solo un gesto, un ultimo moto della volontà: un passo da compiere nell'oscurità
                        più cieca, senza sapere dove conduce, accettando il vuoto come sola destinazione.
                     """,
                List.of(
                        new MissionChoice(
                                "Cammina",
                                ctx -> ctx.getWorldService().getMemory().record(EventType.OBLIVION_PATH_WALKED),
                                end
                        )
                )
        );

        return new Mission(
                "Finale - Il cammino senza nome",
                start,
                0
        );
    }
}