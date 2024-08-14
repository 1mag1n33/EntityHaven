package com.github._1mag1n33dev.entityHaven.NMS.Common;


import com.github._1mag1n33dev.entityHaven.NMS.Common.utils.JsonUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EntityRegistry {

    private static final Map<String, Class<? extends NPC>> registeredEntities = new ConcurrentHashMap<>();
    private static final Map<UUID, NPC> npcInstances = new ConcurrentHashMap<>();

    public static void registerEntity(String type, Class<? extends NPC> entityClass) {
        registeredEntities.put(type, entityClass);
    }

    public static NPC createEntity(String type, NPCManager npcManager, NPCData npcData, JavaPlugin plugin) {
        return npcManager.createNPC(type, npcData, plugin);
    }

    public static NPC getNPC(UUID id) {
        return npcInstances.get(id);
    }

    public Collection<NPC> getAllNPCs() {
        return npcInstances.values();
    }

    public static void saveNPCsToFile(File dataFolder) {
        Map<UUID, NPCData> npcDataMap = new ConcurrentHashMap<>();

        for (NPC npc : npcInstances.values()) {
            NPCData npcData = npc.toNPCData();
            npcDataMap.put(npcData.getId(), npcData);
        }

        JsonUtil.saveNPCData(dataFolder, npcDataMap);
    }

    public static void loadNPCsFromFile(File dataFolder, NPCManager npcManager, JavaPlugin plugin) {
        Map<UUID, NPCData> npcDataMap = JsonUtil.loadNPCData(dataFolder);

        for (NPCData npcData : npcDataMap.values()) {
            NPC npc = createEntity(npcData.getType(), npcManager, npcData, plugin);
            if (npc != null) {
                npc.spawn(npcData.getLocation(), npcData.getName());
            }
        }
    }

    public static Set<String> getTypes() {
        return registeredEntities.keySet();
    }

    public static Class<? extends NPC> getEntityClass(String type) {
        return registeredEntities.get(type);
    }

    public static Map<UUID, NPC> getNPCInstances() {
        return npcInstances;
    }
}
