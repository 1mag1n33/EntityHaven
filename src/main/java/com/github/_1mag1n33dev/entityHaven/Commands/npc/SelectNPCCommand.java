package com.github._1mag1n33dev.entityHaven.Commands.npc;

import com.github._1mag1n33dev.entityHaven.NMS.Common.NPC;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCData;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCManager;
import com.github._1mag1n33dev.entityHaven.Utils.commands.CommandType;
import com.github._1mag1n33dev.entityHaven.Utils.commands.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class SelectNPCCommand extends SubCommand {

    private final NPCManager npcManager;

    public SelectNPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        NPCData nearestNPC = findNearestNPC(playerLocation);

        if (nearestNPC != null) {
            player.sendMessage("Nearest NPC: " + nearestNPC.getName());
            player.setMetadata("selectedNPC", new FixedMetadataValue(plugin, nearestNPC.getId()));
        } else {
            player.sendMessage("No NPCs found.");
        }

        return true;
    }

    private NPCData findNearestNPC(Location playerLocation) {
        NPCData nearestNPC = null;
        double nearestDistance = Double.MAX_VALUE;

        for (NPC npc : plugin.getEntityRegistry().getAllNPCs()) {
            Location npcLocation = npc.getLocation();
            double distance = playerLocation.distance(npcLocation);

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNPC = npc.toNPCData();
            }
        }

        return nearestNPC;
    }


    @Override
    public CommandType getType() {
        return CommandType.ADMIN;
    }
}
