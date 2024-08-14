package com.github._1mag1n33dev.entityHaven.NMS.Common;


import com.github._1mag1n33dev.entityHaven.NMS.V1_8.NPCManagerV1_8;
import org.bukkit.plugin.java.JavaPlugin;

public class VersionFactory {
    private final String serverVersion;

    public VersionFactory(String serverVersion) {
        this.serverVersion = serverVersion;
    }


    public NPCManager createNPCManager(JavaPlugin plugin) {
        switch (serverVersion) {
            case "v1_8_R3":
                return new NPCManagerV1_8(plugin);
            case "v1_16_R3":
                // return new NPCManagerV1_16(plugin);
            default:
                throw new IllegalArgumentException("Unsupported version: " + serverVersion);
        }
    }
}
