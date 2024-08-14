package com.github._1mag1n33dev.entityHaven.NMS.V1_8;

import com.github._1mag1n33dev.entityHaven.NMS.Common.NPC;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCData;
import com.github._1mag1n33dev.entityHaven.NMS.Common.utils.SkinFetcher;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.apache.logging.log4j.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.UUID;

public class EntityPlayerV1_8 extends EntityPlayer implements NPC {

    private String name;
    private JavaPlugin plugin;
    private NPCData npcData;

    private static final Field PROFILE_FIELD;

    private static final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "NPC");

    static {
        try {
            PROFILE_FIELD = EntityHuman.class.getDeclaredField("bH");
            PROFILE_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to access profile field", e);
        }
    }

    public EntityPlayerV1_8(MinecraftServer server, WorldServer world, NPCData npcData, JavaPlugin plugin) {
        super(server, world, gameProfile, new PlayerInteractManager(world));
        this.npcData = npcData;
        this.plugin = plugin;
        this.setName(npcData.getName());
        setSkin(npcData.getSkin().getValue(), npcData.getSkin().getSignature());
    }

    @Override
    public void spawn(Location location, String name) {
        this.setCustomName(npcData.getName());
        this.setName(npcData.getName());
        setPosition(location.getX(), location.getY(), location.getZ());
        this.setCustomNameVisible(true);
        this.setInvisible(false);

        this.spawnPackets(location);
    }

    private void spawnPackets(Location location) {
        PacketPlayOutPlayerInfo packetPlayOutPlayerInfoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this);
        PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(this);
        PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation(this, (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
        PacketPlayOutPlayerInfo packetPlayOutPlayerInfoRemove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this);

        for (Player player : location.getWorld().getPlayers()) {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            craftPlayer.getHandle().playerConnection.sendPacket(packetPlayOutPlayerInfoAdd);
            craftPlayer.getHandle().playerConnection.sendPacket(packetPlayOutNamedEntitySpawn);
            craftPlayer.getHandle().playerConnection.sendPacket(packetPlayOutEntityHeadRotation);
            craftPlayer.getHandle().playerConnection.sendPacket(packetPlayOutPlayerInfoRemove);
        }
    }

    @Override
    public void despawn() {
        this.getBukkitEntity().remove();
    }

    @Override
    public void setName(String name) {
        this.name = name;
        setGameProfileName(name);
        setCustomName(name);
        setCustomNameVisible(true);
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void setGameProfileName(String name) {
        try {
            Field profileField = EntityHuman.class.getDeclaredField("bH");
            profileField.setAccessible(true);
            GameProfile profile = (GameProfile) profileField.get(this);
            Field nameField = GameProfile.class.getDeclaredField("name");
            nameField.setAccessible(true);

            nameField.set(profile, name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSkin(String skinValue, String skinSignature) {
        GameProfile profile = this.getPlayerProfile();
        if (profile == null) {
            plugin.getLogger().warning("Profile is null!");
            return;
        }

        this.npcData.setSkin(new SkinFetcher.SkinData(skinValue, skinSignature));

        profile.getProperties().put("textures", new Property("textures", skinValue, skinSignature));

        PacketPlayOutPlayerInfo removePlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            craftPlayer.getHandle().playerConnection.sendPacket(removePlayerPacket);
        }


        spawnPackets(this.npcData.getLocation());
    }

    @Override
    public NPCData toNPCData() {
        return npcData;
    }

    @Override
    public void updateData(NPCData newNpcData) {
        this.npcData = newNpcData;
    }


    @Override
    public Location getLocation() {
        return this.npcData.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        teleportTo(location, false);
    }

    @Override
    public org.bukkit.entity.Entity getEntity() {
        return getBukkitEntity();
    }

    public GameProfile getPlayerProfile() {
        return this.getProfile();
    }
}
