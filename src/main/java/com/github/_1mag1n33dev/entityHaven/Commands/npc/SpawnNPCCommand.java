package com.github._1mag1n33dev.entityHaven.Commands.npc;

import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCManager;
import com.github._1mag1n33dev.entityHaven.NMS.Common.utils.SkinFetcher;
import com.github._1mag1n33dev.entityHaven.Utils.commands.CommandType;
import com.github._1mag1n33dev.entityHaven.Utils.commands.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpawnNPCCommand extends SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 3) {
            player.sendMessage("Usage: /hubhaven npc spawn <type> <name>");
            return true;
        }

        String type = args[2];
        String name = args[3];
        Location location = player.getLocation();

        UUID npcId = UUID.randomUUID();
        NPCManager npcManager = plugin.getNPCManager();
        npcManager.spawnNPC(npcId, type, name, location, new SkinFetcher.SkinData("", ""));

        player.sendMessage("Spawned NPC: " + name + " of type " + type + " at your location.");

        return true;
    }

    @Override
    public CommandType getType() {
        return CommandType.ADMIN;
    }
}
