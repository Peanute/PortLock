package org.mrsteakhouse.portlock.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mrsteakhouse.portlock.PortLock;

public class Cooldown implements SubCommand{
	private PortLock plugin;
	
	public Cooldown(PortLock plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length < 1) { player.sendMessage(plugin.getPrefix() + ChatColor.DARK_GREEN + "Zu wenig Argumente!"); return false; }
		if(!player.hasPermission("portlock.cooldown")) { player.sendMessage(noPerm()); return false; }
		if(!plugin.isInteger(args[0])) { player.sendMessage(ChatColor.DARK_RED + "Argument muss eine Zahl sein!"); return false; }
		int cd = Integer.parseInt(args[0]);
		plugin.setCooldown(cd);
		player.sendMessage(plugin.getPrefix() + ChatColor.DARK_GREEN + "Cooldown auf " + ChatColor.BLUE + cd + ChatColor.DARK_GREEN + " gesetzt.");
		return true;
	}

	@Override
	public String help(Player player) {
		return ChatColor.YELLOW + "/portlock cooldown <zahl>: " + ChatColor.AQUA + "Setzt die Cooldown in Sekunden";
	}
	
	@Override
	public String noPerm() {
		return ChatColor.DARK_RED + "Permissions nicht vorhanden! " + ChatColor.RESET + "(portlock.cooldown)";
	}
}
