package com.github._1mag1n33dev.entityHaven.NMS.V1_8;

import com.github._1mag1n33dev.entityHaven.NMS.Common.EntityRegistry;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPC;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCData;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCManager;
import com.github._1mag1n33dev.entityHaven.NMS.Common.utils.SkinFetcher;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public class NPCManagerV1_8 implements NPCManager {

    private final JavaPlugin plugin;


    public NPCManagerV1_8(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public void removeNPC(UUID npcId) {
        NPC npc = EntityRegistry.getNPC(npcId);
        if (npc != null) {
            npc.despawn();
            EntityRegistry.getNPCInstances().remove(npcId);
        }
    }

    @Override
    public void addClickAction(NPC npc, Runnable action) {
        // Implementation for adding click actions
    }


    @Override
    public NPC getNPC(UUID npcId) {
        return EntityRegistry.getNPC(npcId);
    }

    @Override
    public void registerNPCs() {
        EntityRegistry.registerEntity("player", (Class<? extends NPC>) EntityPlayerV1_8.class);
    }

    public void set(NPCData newNpcData) {
        NPC npc = EntityRegistry.getNPC(newNpcData.getId());
        if (npc != null) {
            npc.updateData(newNpcData);
            EntityRegistry.saveNPCsToFile(new File(String.valueOf(plugin.getDataFolder())));
        }
    }

    @Override
    public void spawnNPC(UUID uuid, String type, String name, Location location, SkinFetcher.SkinData skin) {
        NPCData npcData = new NPCData(uuid, type, name, location, skin);
        NPC npc = this.createNPC(type, npcData, plugin);
        if (npc != null) {
            set(npc.toNPCData());
            npc.spawn(location, name);
        }
    }

    @Override
    public NPC createNPC(String type, NPCData npcData, JavaPlugin plugin) {
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer world = MinecraftServer.getServer().getWorldServer(0);

        Class<? extends NPC> entityClass = EntityRegistry.getEntityClass(type);
        if (entityClass != null) {
            try {
                NPC npc = entityClass.getConstructor(MinecraftServer.class, WorldServer.class, NPCData.class, JavaPlugin.class).newInstance(server, world, npcData, plugin);
                EntityRegistry.getNPCInstances().put(npcData.getId(), npc);
                return npc;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public MinecraftServer getServerInstance() {
        return ((CraftServer) plugin.getServer()).getServer();
    }

    @Override
    public WorldServer getWorldServer(Location location) {
        return ((CraftWorld) location.getWorld()).getHandle();
    }

    @Override
    public WorldServer getWorldServer() {
        return ((CraftWorld) plugin.getServer().getWorld("world")).getHandle();
    }

}
