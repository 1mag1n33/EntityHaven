package com.github._1mag1n33dev.entityHaven.NMS.Common.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;

public class LocationDeserializer implements JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject obj = json.getAsJsonObject();
        World world = Bukkit.getWorld(obj.get("world").getAsString());
        double x = obj.get("x").getAsDouble();
        double y = obj.get("y").getAsDouble();
        double z = obj.get("z").getAsDouble();
        float yaw = obj.get("yaw").getAsFloat();
        float pitch = obj.get("pitch").getAsFloat();
        return new Location(world, x, y, z, yaw, pitch);
    }
}

