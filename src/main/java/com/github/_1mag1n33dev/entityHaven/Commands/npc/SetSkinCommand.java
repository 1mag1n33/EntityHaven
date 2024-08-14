package com.github._1mag1n33dev.entityHaven.Commands.npc;

import com.github._1mag1n33dev.entityHaven.NMS.Common.NPC;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCManager;
import com.github._1mag1n33dev.entityHaven.NMS.Common.utils.SkinFetcher;
import com.github._1mag1n33dev.entityHaven.Utils.commands.CommandType;
import com.github._1mag1n33dev.entityHaven.Utils.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.UUID;

public class SetSkinCommand extends SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) throws Exception {
        Player player = (Player) sender;
        NPCManager npcManager = plugin.getNPCManager();
        UUID npcId = getSelectedNPC(player);

        if (npcId == null) {
            player.sendMessage("No NPC selected. Use /hubhaven npc select to select one.");
            return true;
        }

        String skinUrl = args[2];


        NPC npc = npcManager.getNPC(npcId);

        if (npc == null) {
            sender.sendMessage("NPC instance not found.");
            return true;
        }


        SkinFetcher.SkinData skindata = SkinFetcher.getSkinUrl(skinUrl);
        npc.setSkin(skindata.getValue(), skindata.getSignature());
        plugin.getLogger().info("NAME: " + npc.getName());

        sender.sendMessage("NPC skin updated.");
        return true;

    }

    private UUID getSelectedNPC(Player player) {
        List<MetadataValue> metadata = player.getMetadata("selectedNPC");
        if (metadata != null && !metadata.isEmpty()) {
            String npcIdString = metadata.get(0).asString();
            return UUID.fromString(npcIdString);
        }
        return null;
    }

    @Override
    public CommandType getType() {
        return null;
    }
}
