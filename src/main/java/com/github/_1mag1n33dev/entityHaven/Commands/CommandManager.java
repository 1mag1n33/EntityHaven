package com.github._1mag1n33dev.entityHaven.Commands;

import com.github._1mag1n33dev.entityHaven.Commands.npc.SpawnNPCCommand;
import com.github._1mag1n33dev.entityHaven.EntityHaven;
import com.github._1mag1n33dev.entityHaven.Utils.commands.CommandType;
import com.github._1mag1n33dev.entityHaven.Utils.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {
    private final Map<String, SubCommand> commands = new HashMap<>();
    private final EntityHaven plugin;

    public CommandManager(EntityHaven plugin) {
        this.plugin = plugin;

        registerCommand("spawn", new SpawnNPCCommand());
    }

    private void registerCommand(String name, SubCommand command) {
        command.setPlugin(plugin);
        commands.put(name.toLowerCase(), command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("Please specify a command!");
            return false;
        }

        SubCommand subCommand = commands.get(args[0].toLowerCase());
        if (subCommand == null) {
            sender.sendMessage("Unknown command!");
            return false;
        }


        CommandType type = subCommand.getType();
        VerifyType(type, sender);

        try {
            return subCommand.execute(sender, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean VerifyType(CommandType type, CommandSender sender) {
        if (type == CommandType.PLAYER && !sender.hasPermission("entityhaven.use")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        } else if (type == CommandType.ADMIN && !sender.hasPermission("entityhaven.admin")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        } else if (type == CommandType.DEVELOPER && !sender.hasPermission("entityhaven.developer")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        return false;
    }
}

