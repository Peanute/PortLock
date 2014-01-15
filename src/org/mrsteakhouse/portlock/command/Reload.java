package org.mrsteakhouse.portlock.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mrsteakhouse.portlock.PortLock;

public class Reload implements SubCommand{
	private PortLock plugin;
	
	public Reload(PortLock plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if (!player.hasPermission("portlock.reload")) { player.sendMessage(noPerm()); return false; }
		plugin.reload();
		player.sendMessage(plugin.getPrefix() + ChatColor.DARK_GREEN + "Reload done.");
		return false;
	}

	@Override
	public String help(Player player) {
		return ChatColor.YELLOW + "/portlock reload: " + ChatColor.AQUA + "Lädt die Konfiguration neu.";
	}

	@Override
	public String noPerm() {
		return ChatColor.DARK_RED + "Permissions nicht vorhanden! " + ChatColor.RESET + "(portlock.reload)";
	}

}
