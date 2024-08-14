package com.github._1mag1n33dev.entityHaven.Events;

import com.github._1mag1n33dev.entityHaven.EntityHaven;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private final Map<String, Listener> listeners = new HashMap<>();
    private final EntityHaven plugin;

    public EventManager(EntityHaven plugin) {
        this.plugin = plugin;
    }

    public void enableListeners() {
        registerListener("npcclicklistener", new NPCClickListener(plugin.getNPCManager()));
    }

    public void registerListener(String name, Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        listeners.put(name, listener);
    }

    public void unregisterListener(String name) {
        Listener listener = listeners.remove(name);
        if (listener != null) {
            HandlerList.unregisterAll(listener);
        }
    }

    public void unregisterAllListeners() {
        for (Listener listener : listeners.values()) {
            HandlerList.unregisterAll(listener);
        }
        listeners.clear();
    }

    public Listener getListener(String name) {
        return listeners.get(name);
    }

    public Map<String, Listener> getListeners() {
        return new HashMap<>(listeners);
    }
}
