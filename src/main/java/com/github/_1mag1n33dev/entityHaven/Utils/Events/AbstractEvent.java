package com.github._1mag1n33dev.entityHaven.Utils.Events;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractEvent implements Listener {
    protected JavaPlugin plugin;
    protected String name;

    protected JavaPlugin getPlugin() {
        return this.plugin;
    }

    protected String getName() { return this.name; }

    public void setName (String name) {this.name = name;}

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }


}
