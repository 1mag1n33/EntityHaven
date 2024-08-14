package com.github._1mag1n33dev.entityHaven.NMS.Common.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class LocationSerializer implements JsonSerializer<Location> {
    @Override
    public JsonElement serialize(Location src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("world", src.getWorld().getName());
        obj.addProperty("x", src.getX());
        obj.addProperty("y", src.getY());
        obj.addProperty("z", src.getZ());
        obj.addProperty("yaw", src.getYaw());
        obj.addProperty("pitch", src.getPitch());
        return obj;
    }
}
