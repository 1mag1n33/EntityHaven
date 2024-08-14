package com.github._1mag1n33dev.entityHaven.NMS.Common;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface NPC {

    void spawn(Location location, String name);
    void despawn();
    void setName(String name);
    String getName();
    void setLocation(Location location);
    Entity getEntity();
    default void setSkin(String skinValue, String skinSignature)  {}

    void updateData(NPCData newNpcData);
    NPCData toNPCData();

    Location getLocation();
}
