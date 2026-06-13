package it.unicam.cs.mpgc.rpg122711.content;
import java.util.List;

/**
 *  Repository statico che continene tutto il testo del prologo di inizio gioco.
 */
public class PrologueContent {
    public static List<String> lines() {
        return List.of(
                "Il mondo non è più quello delle grandi guerre tra eroi e demoni." +
                        "È un'epoca silenziosa, in cui le leggende sono diventate ricordi... e i ricordi svaniscono lentamente.",
                "Le città crescono sopra le rovine di regni dimenticati e " +
                        "la magia — un tempo temuta — è ora studiata come una scienza antica.",
                "Tu non sei un eroe." +
                        " Almeno, non ancora",
                "Sei un viaggiatore che ha ereditato solo frammenti di conoscenza: " +
                        "pagine sparse, incantesimi incompleti, nomi di luoghi che nessuno ricorda più davvero. " +
                        "Eppure... qualcosa ti spinge a partire.",
                "Dicono che oltre le terre di Eryndor, esistono ancora 'memorie vive' del passato.",
                "Luoghi dove il tempo non scorre come dovrebbe. " +
                        "E ogni memoria, cambia chi la attraversa...",
                "Il mondo osserva in silenzio il tuo primo passo."
        );
    }
}
