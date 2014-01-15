package org.mrsteakhouse.portlock.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.mrsteakhouse.portlock.PortLock;

public class PlayerTeleport implements Listener{
	private PortLock plugin;
	
	public PlayerTeleport(PortLock plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST) 
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if(plugin.isPlayerInCombat(event.getPlayer().getName())) {
			event.getPlayer().sendMessage(ChatColor.DARK_RED + "Du bist im Kampf und kannst dich nicht teleportieren!");
			event.setCancelled(true);
		}
	}
}
