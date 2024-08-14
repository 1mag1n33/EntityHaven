package com.github._1mag1n33dev.entityHaven.Utils.commands;

import com.github._1mag1n33dev.entityHaven.EntityHaven;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    protected EntityHaven plugin;

    public void setPlugin(EntityHaven plugin) {
        this.plugin = plugin;
    }

    public abstract boolean execute(CommandSender sender, String[] args) throws Exception;

    public abstract CommandType getType();
}
