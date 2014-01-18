package org.mrsteakhouse.portlock.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mrsteakhouse.portlock.PortLock;

public class FallingBlock implements SubCommand{
	private PortLock plugin;
	
	public FallingBlock(PortLock plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length < 1) { player.sendMessage(plugin.getPrefix() + ChatColor.DARK_GREEN + "Zu wenig Argumente!"); return false; }
		if(!player.hasPermission("portlock.toggleblock")) { player.sendMessage(noPerm()); return false; }
		if(!plugin.isBoolean(args[0])) { player.sendMessage(ChatColor.DARK_RED + "Argument muss true oder false sein!"); return false; }
		boolean fd = Boolean.parseBoolean(args[0]);
		plugin.setIgnoreFallDamage(fd);
		player.sendMessage(plugin.getPrefix() + ChatColor.DARK_GREEN + "Schaden durch fallende Blöcke wird " + (fd? "":"nicht") + " ignoriert.");
		return true;
	}

	@Override
	public String help(Player player) {
		return ChatColor.YELLOW + "/portlock toggleblock [true|false]: " + ChatColor.AQUA + "Setzt, ob Spieler bei falldenden Blöcken gesperrt werden.";
	}

	@Override
	public String noPerm() {
		return ChatColor.DARK_RED + "Permissions nicht vorhanden! " + ChatColor.RESET + "(portlock.toggleblock)";
	}
	
	
}
