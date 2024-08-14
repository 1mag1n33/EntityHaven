package com.github._1mag1n33dev.entityHaven.NMS.Common.utils;

import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonUtil {

    private static final Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Location.class, new LocationSerializer());
        builder.registerTypeAdapter(Location.class, new LocationDeserializer());
        gson = builder.setPrettyPrinting().create();
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static Map<UUID, NPCData> loadNPCData(File dataFolder) {
        File file = new File(dataFolder, "npcs.json");
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Type type = new com.google.gson.reflect.TypeToken<Map<UUID, NPCData>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void saveNPCData(File dataFolder, Map<UUID, NPCData> npcDataMap) {
        File file = new File(dataFolder, "npcs.json");
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(npcDataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
