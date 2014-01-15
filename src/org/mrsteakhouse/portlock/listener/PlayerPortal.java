package org.mrsteakhouse.portlock.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.mrsteakhouse.portlock.PortLock;

public class PlayerPortal implements Listener{
	private PortLock plugin;
	
	
	public PlayerPortal(PortLock plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPortal(PlayerPortalEvent event) {
		if(plugin.isPlayerInCombat(event.getPlayer().getName())) {
			event.getPlayer().sendMessage(ChatColor.DARK_RED + "Du bist im Kampf und kannst dich nicht teleportieren!");
			event.setCancelled(true);
		}
	}
}
