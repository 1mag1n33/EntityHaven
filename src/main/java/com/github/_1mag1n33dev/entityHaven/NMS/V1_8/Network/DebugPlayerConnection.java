package com.github._1mag1n33dev.entityHaven.NMS.V1_8.Network;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class DebugPlayerConnection extends PlayerConnection {
    private JavaPlugin plugin;
    public DebugPlayerConnection(MinecraftServer minecraftServer, WorldServer worldServer, GameProfile gameProfile, JavaPlugin plugin) {
        super(minecraftServer, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), new EntityPlayer(minecraftServer, worldServer, gameProfile, new PlayerInteractManager(worldServer)));
        this.plugin = plugin;
    }

    @Override
    public void sendPacket(Packet packet) {
        if (packet instanceof PacketPlayOutPlayerInfo) {
            PacketPlayOutPlayerInfo infoPacket = (PacketPlayOutPlayerInfo) packet;
            try {
                Field actionField = PacketPlayOutPlayerInfo.class.getDeclaredField("a");
                actionField.setAccessible(true);
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction action = (PacketPlayOutPlayerInfo.EnumPlayerInfoAction) actionField.get(infoPacket);
                System.out.println("Sending PacketPlayOutPlayerInfo with action: " + action.name());


                Field playersField = PacketPlayOutPlayerInfo.class.getDeclaredField("b");
                playersField.setAccessible(true);
                Collection<?> players = (Collection<?>) playersField.get(infoPacket);
                for (Object obj : players) {
                    System.out.println("Player Info: " + obj.toString());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (packet instanceof PacketPlayOutEntityMetadata) {
            PacketPlayOutEntityMetadata metadataPacket = (PacketPlayOutEntityMetadata) packet;
            try {
                Field entityIdField = PacketPlayOutEntityMetadata.class.getDeclaredField("a");
                entityIdField.setAccessible(true);
                int entityId = (Integer) entityIdField.get(metadataPacket);
                System.out.println("Sending PacketPlayOutEntityMetadata for entity ID: " + entityId);

                Field metadataField = PacketPlayOutEntityMetadata.class.getDeclaredField("b");
                metadataField.setAccessible(true);
                Object metadata = metadataField.get(metadataPacket);
                if (metadata instanceof List) {
                    List<?> metadataItems = (List<?>) metadata;
                    for (Object item : metadataItems) {
                        System.out.println("Metadata: " + item.toString());
                    }
                } else {
                    System.out.println("Metadata: " + metadata.toString());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (packet instanceof PacketPlayOutNamedEntitySpawn) {
            PacketPlayOutNamedEntitySpawn spawnPacket = (PacketPlayOutNamedEntitySpawn) packet;
            try {
                Field entityIdField = PacketPlayOutNamedEntitySpawn.class.getDeclaredField("a");
                entityIdField.setAccessible(true);
                int entityId = (Integer) entityIdField.get(spawnPacket);
                System.out.println("Sending PacketPlayOutNamedEntitySpawn for entity ID: " + entityId);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (packet instanceof PacketPlayOutRespawn) {
            PacketPlayOutRespawn respawnPacket = (PacketPlayOutRespawn) packet;
            try {
                Field worldTypeField = PacketPlayOutRespawn.class.getDeclaredField("b");
                worldTypeField.setAccessible(true);
                Object worldType = worldTypeField.get(respawnPacket);
                System.out.println("Sending PacketPlayOutRespawn with world type: " + worldType);

                Field difficultyField = PacketPlayOutRespawn.class.getDeclaredField("c");
                difficultyField.setAccessible(true);
                EnumDifficulty difficulty = (EnumDifficulty) difficultyField.get(respawnPacket);
                System.out.println("Difficulty: " + difficulty);

                Field gameModeField = PacketPlayOutRespawn.class.getDeclaredField("d");
                gameModeField.setAccessible(true);
                WorldSettings.EnumGamemode gameMode = (WorldSettings.EnumGamemode) gameModeField.get(respawnPacket);
                System.out.println("Game Mode: " + gameMode);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sending packet: " + packet.getClass().getSimpleName());
        }

        super.sendPacket(packet);
    }

    @Override
    public void a(PacketPlayInChat packet) {
        // Do nothing
    }

    @Override
    public void a(PacketPlayInClientCommand packet) {
        // Do nothing
    }

    // Implement other methods as needed
}
