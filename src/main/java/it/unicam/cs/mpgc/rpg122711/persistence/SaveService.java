package it.unicam.cs.mpgc.rpg122711.persistence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Servizio di I/O a basso livello per la persistenza su disco.
 * Si occupa di serializzare e deserializzare fisicamente gli oggetti SaveData
 * in formato JSON tramite la libreria Gson, gestendo i file nei vari slot.
 */
public class SaveService {

    private static final String FILE_PREFIX = "save";
    private static final String FILE_SUFFIX = ".json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void save(SaveData data, int slot) {
        String path = buildPath(slot);
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio slot " + slot, e);
        }
    }

    public SaveData load(int slot) {
        String path = buildPath(slot);
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, SaveData.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean exists(int slot) {
        return new File(buildPath(slot)).exists();
    }

    private String buildPath(int slot) {
        return FILE_PREFIX + slot + FILE_SUFFIX;
    }
}