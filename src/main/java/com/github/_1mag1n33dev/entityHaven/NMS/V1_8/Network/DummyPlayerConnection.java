package com.github._1mag1n33dev.entityHaven.NMS.V1_8.Network;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.plugin.java.JavaPlugin;

public class DummyPlayerConnection extends PlayerConnection {
    private JavaPlugin plugin;
    public DummyPlayerConnection(MinecraftServer minecraftServer, WorldServer worldServer, GameProfile gameProfile, JavaPlugin plugin) {
        super(minecraftServer, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), new EntityPlayer(minecraftServer, worldServer, gameProfile, new PlayerInteractManager(worldServer)));
        this.plugin = plugin;
    }

    @Override
    public void sendPacket(Packet packet) {
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
