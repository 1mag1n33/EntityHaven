package com.github._1mag1n33dev.entityHaven.NMS.Common;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCDataSaver {

    private JavaPlugin plugin;
    private final long saveInterval;

    public NPCDataSaver(long saveInterval, JavaPlugin plugin) {
        this.plugin = plugin;
        this.saveInterval = saveInterval;
        startSavingTask();
    }

    private void startSavingTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::saveNPCs, saveInterval, saveInterval);
    }

    private void saveNPCs() {
        EntityRegistry.saveNPCsToFile(plugin.getDataFolder());
    }
}

