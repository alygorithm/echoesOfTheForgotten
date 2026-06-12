package it.unicam.cs.mpgc.rpg122711.persistence;

import it.unicam.cs.mpgc.rpg122711.domain.Player;
import it.unicam.cs.mpgc.rpg122711.service.WorldService;
import it.unicam.cs.mpgc.rpg122711.flow.MissionManager;

import java.util.LinkedHashSet;

/**
 * Implementazione della persistenza dello stato di gioco.
 *
 * Responsabilità (SRP):
 * - serializzazione del game state
 * - deserializzazione di world + progressione
 *
 * Nota:
 * La ricostruzione del Player è responsabilità del GameFlow.
 */
public class SaveManager implements GamePersistence {

    @Override
    public SaveData save(Player player, MissionManager mm, WorldService ws) {

        SaveData data = new SaveData();

        data.player = new SaveData.PlayerData();
        data.player.name = player.getName();
        data.player.characterClass = player.getCharacterClass();
        data.player.level = player.getLevel();
        data.player.experience = player.getExperience();
        data.player.hp = player.getHp();
        data.player.mana = player.getMana();

        data.progress = new SaveData.ProgressData();
        data.progress.currentMissionIndex = mm.getCurrentMissionIndex();

        data.world = new SaveData.WorldData();
        data.world.year = ws.getState().getYear();
        data.world.instability = ws.getState().getInstability();

        data.events = ws.getMemory().getAll().stream().toList();

        return data;
    }

    @Override
    public void load(SaveData data, MissionManager mm, WorldService ws) {

        mm.setCurrentMissionIndex(data.progress.currentMissionIndex);

        ws.getState().setYear(data.world.year);
        ws.getState().setInstability(data.world.instability);

        ws.getMemory().setAll(new LinkedHashSet<>(data.events));
    }
}