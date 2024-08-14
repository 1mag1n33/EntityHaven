package com.github._1mag1n33dev.entityHaven.NMS.Common;

import com.github._1mag1n33dev.entityHaven.NMS.Common.utils.SkinFetcher;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public interface NPCManager {
    NPC getNPC(UUID npcId);

    void registerNPCs();
    void spawnNPC(UUID uuid, String type, String name, Location location, SkinFetcher.SkinData skin);
    void removeNPC(UUID npcId);
    void addClickAction(NPC npc, Runnable action);

    NPC createNPC(String type, NPCData npcData, JavaPlugin plugin);

    MinecraftServer getServerInstance();

    WorldServer getWorldServer(Location location);

    WorldServer getWorldServer();
}
