package com.github._1mag1n33dev.entityHaven;

import com.github._1mag1n33dev.entityHaven.Commands.CommandManager;
import com.github._1mag1n33dev.entityHaven.Events.EventManager;
import com.github._1mag1n33dev.entityHaven.NMS.Common.EntityRegistry;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCDataSaver;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCManager;
import com.github._1mag1n33dev.entityHaven.NMS.Common.VersionFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EntityHaven extends JavaPlugin {
    private NPCManager npcManager;
    private EntityRegistry entityRegistry;
    private CommandManager commandManager;
    private EventManager eventManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        String serverVersion = getServerVersion();
        VersionFactory factory = new VersionFactory(serverVersion);
        npcManager = factory.createNPCManager(this);

        getLogger().info("EntityHaven has been enabled!");

        new NPCDataSaver(60000, this);

        npcManager.registerNPCs();

        EntityRegistry.loadNPCsFromFile(getDataFolder(), npcManager,  this);

        commandManager = new CommandManager(this);
        eventManager = new EventManager(this);

        eventManager.enableListeners();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private String getServerVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    public NPCManager getNPCManager() {
        return npcManager;
    }

    public EntityRegistry getEntityRegistry() {
        return entityRegistry;
    }

}
