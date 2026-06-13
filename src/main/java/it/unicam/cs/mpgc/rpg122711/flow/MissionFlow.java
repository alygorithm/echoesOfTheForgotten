package it.unicam.cs.mpgc.rpg122711.flow;
import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.domain.mission.Mission;

/**
 * Interfaccia utilizzata per controllare la progressione narrativa.
 * Definisce i metodi necessari per costruire la missione corrente, avanzare,
 * verificare la conclusione della storia e resettare lo stato.
 */
public interface MissionFlow {
    Mission build(Player player);
    void next();
    boolean isFinished();
    void reset();
}