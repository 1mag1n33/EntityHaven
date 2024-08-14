package com.github._1mag1n33dev.entityHaven.NMS.Common;

import com.github._1mag1n33dev.entityHaven.NMS.Common.utils.SkinFetcher;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCData implements Serializable {
    private UUID id;
    private String type;
    private String name;
    private Location location;
    private SkinFetcher.SkinData skin;
    private Map<String, Object> metadata;

    public NPCData(UUID id, String type, String name, Location location, SkinFetcher.SkinData skin) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.location = location;
        this.skin = skin;
        this.metadata = new HashMap<>();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SkinFetcher.SkinData getSkin() {
        return skin;
    }

    public void setSkin(SkinFetcher.SkinData skin) {  // Updated setter
        this.skin = skin;
    }

    // Metadata methods
    public Object get(String key) {
        return metadata.get(key);
    }

    public void set(String key, Object value) {
        metadata.put(key, value);
    }

    public void remove(String key) {
        metadata.remove(key);
    }

    public Map<String, Object> getAll() {
        return metadata;
    }

}
