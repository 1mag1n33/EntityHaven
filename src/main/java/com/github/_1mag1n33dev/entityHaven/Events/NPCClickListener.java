package com.github._1mag1n33dev.entityHaven.Events;


import com.github._1mag1n33dev.entityHaven.NMS.Common.NPC;
import com.github._1mag1n33dev.entityHaven.NMS.Common.NPCManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NPCClickListener implements Listener {

    private final NPCManager npcManager;

    public NPCClickListener(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof NPC) {
            NPC npc = (NPC) entity;

            player.sendMessage("You clicked on NPC: " + npc.getName());
        }
    }
}
