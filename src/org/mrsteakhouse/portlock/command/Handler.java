package org.mrsteakhouse.portlock.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.mrsteakhouse.portlock.PortLock;

public class Handler implements CommandExecutor
{
	private PortLock plugin;
	private HashMap<String, SubCommand> commands;
	
	public Handler(PortLock plugin)
	{
		this.plugin = plugin;
		this.commands = new HashMap<String, SubCommand>();
		loadCommands();
	}
	
	private void loadCommands()
	{
		  commands.put("reload", new Reload(plugin));
		  commands.put("toggle", new Toggle(plugin));
		  commands.put("cooldown", new Cooldown(plugin));
		  commands.put("togglefall", new FallDamage(plugin));
		  commands.put("toggleblock", new FallingBlock(plugin));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd1, String commandLabel, String[] args)
	{
		String cmd = cmd1.getName();
		PluginDescriptionFile pdfFile = this.plugin.getDescription();
		
		Player player = null;
		if ((sender instanceof Player)) {
			player = (Player)sender;
		}
		else {
			System.out.println("Kommandos können nur ingame benutzt werden.");
			return true;
		}
	
		if (cmd.equalsIgnoreCase("portlock")) {
			if ((args == null) || (args.length < 1)) {
				player.sendMessage(String.valueOf(ChatColor.GOLD) + ChatColor.BOLD + "PortLock - MrSteakhouse" + ChatColor.RESET + ChatColor.YELLOW + " Version: " + pdfFile.getVersion());
				player.sendMessage(ChatColor.GOLD + "Gib '/portlock help' für die Hilfe ein.");
				
				return true;
			}
			
			if (args[0].equalsIgnoreCase("help")) {
				if(args.length == 1) {	
					help(player);
					return true;
				} else {
					SubCommand subClass = getCommandClass(args[1], player);
					if(subClass != null) {
						player.sendMessage(plugin.getPrefix() + "\n" + subClass.help(player));
					}
				}
			}
			
			String sub = args[0];
			
			Vector<String> l = new Vector<String>();
			l.addAll(Arrays.asList(args));
			l.remove(0);
			
			args = (String[])l.toArray(new String[0]);
			SubCommand subClass = getCommandClass(sub, player);
			if(subClass != null) {
				subClass.onCommand(player, args);
			}
			return true;
		}
		return false;
	}
	
	public void help(Player p) {
		p.sendMessage(plugin.getPrefix() + ChatColor.BLUE + "Version: " + plugin.getDescription().getVersion());
		for(SubCommand sub : commands.values()) {
			p.sendMessage(sub.help(p));
		}
	}
	
	public SubCommand getCommandClass(String command, Player player) {
		if (!this.commands.containsKey(command)) {
			player.sendMessage(plugin.getPrefix());
			player.sendMessage(ChatColor.DARK_RED + "Befehl existiert nicht!");
			player.sendMessage(ChatColor.GOLD + "Gib '/portlock help' für die Hilfe ein.");
			return null;
		}
		try
		{
			return ((SubCommand)this.commands.get(command)); 
		} catch (Exception e) {
			e.printStackTrace(); 
			Bukkit.getLogger().log(Level.FINE, "[PortLock] Error loading command: " + command);
			return null;
		}
	}
}