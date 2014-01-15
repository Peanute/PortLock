package org.mrsteakhouse.portlock.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mrsteakhouse.portlock.PortLock;

public class Toggle implements SubCommand{
	private PortLock plugin;
	
	public Toggle(PortLock plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(!player.hasPermission("portlock.toggle")) {  return false; }
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("on")) {
				plugin.setActive(true);
				player.sendMessage(plugin.getPrefix() + ChatColor.DARK_GREEN + "Aktiviert!");
				return true;
			}
			else if(args[0].equalsIgnoreCase("off")) {
				plugin.setActive(false);
				player.sendMessage(plugin.getPrefix() + ChatColor.DARK_GREEN + "Deaktiviert!");
				return true;
			}
			else {
				player.sendMessage(help(player));
				return true;
			}
		}
		player.sendMessage(plugin.getPrefix() + ChatColor.DARK_GREEN + (plugin.isActive() ? "Deaktiviert!" : "Aktiviert!"));
		plugin.setActive(plugin.isActive() ? false : true);
		return true;
	}

	@Override
	public String help(Player player) {
		return ChatColor.YELLOW + "/portlock toggle [on|off]: " + ChatColor.AQUA + "Plugin ein- oder ausschalten.";
	}

	@Override
	public String noPerm() {
		return ChatColor.DARK_RED + "Permissions nicht vorhanden! " + ChatColor.RESET + "(portlock.toggle)";
	}

}
