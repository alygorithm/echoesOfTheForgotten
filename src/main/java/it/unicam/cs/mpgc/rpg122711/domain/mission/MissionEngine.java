package it.unicam.cs.mpgc.rpg122711.domain.mission;

import it.unicam.cs.mpgc.rpg122711.domain.CharacterClass;
import it.unicam.cs.mpgc.rpg122711.domain.Player;

public class MissionEngine {

    public static boolean isArcanist(Player p) {
        return p.getCharacterClass() == CharacterClass.ARCANIST;
    }

    public static boolean isScholar(Player p) {
        return p.getCharacterClass() == CharacterClass.SCHOLAR;
    }

    public static boolean isWanderer(Player p) {
        return p.getCharacterClass() == CharacterClass.WANDERER;
    }
}