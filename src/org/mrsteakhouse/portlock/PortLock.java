package org.mrsteakhouse.portlock;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.mrsteakhouse.portlock.command.Handler;
import org.mrsteakhouse.portlock.listener.EntityDamage;
import org.mrsteakhouse.portlock.listener.PlayerPortal;
import org.mrsteakhouse.portlock.listener.PlayerTeleport;

public class PortLock extends JavaPlugin{
	private HashMap<String, BukkitTask> ScheduleList = new HashMap<String, BukkitTask>();
	private int combatCooldown = 5000;
	private boolean active = true;
	private String prefix = ChatColor.GOLD + "[PortLock] " + ChatColor.RESET;
	private boolean ignoreFallDamage = false;
	private boolean ignoreFallingBlock = false;
	
	@Override
	public void onEnable() {
		loadConfig();
		
		getCommand("portlock").setExecutor(new Handler(this));
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerPortal(this), this);
		pm.registerEvents(new PlayerTeleport(this), this);
		pm.registerEvents(new EntityDamage(this), this);
	}
	
	@Override
	public void onDisable() {
		save();
	}
	
	public void reload() {
		save();
		loadConfig();
	}
	
	public void loadConfig() {
		FileConfiguration config = this.getConfig();
		File file = new File("plugin/PortLock/config.yml");
		
		if (!file.exists()) {
		      config.options().copyDefaults(true);
		      saveConfig();
		}
		ConfigurationSection cs = config.getConfigurationSection("portlock");
		
		combatCooldown = cs.getInt("cooldown");
		active = cs.getBoolean("active");
		ignoreFallDamage = cs.getBoolean("ignorefalldamage");
		ignoreFallingBlock = cs.getBoolean("ignorefallingblock");
	}
	
	public void save() {
		File file = new File("/plugins/PortLock/config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection cs = config.getConfigurationSection("portlock");
		cs.set("cooldown", combatCooldown);
		cs.set("atvive", active);
		cs.set("ignorefalldamage", ignoreFallDamage);
		cs.set("ignorefallingblock", ignoreFallingBlock);
		
		try {
			config.save(file);
		} catch(Exception e) {
			Bukkit.getLogger().log(Level.FINE, "[PortLock] Unable to save config!");
		}
	}
	
	public void schedulePlayer(Player player) {
		if(!ScheduleList.containsKey(player.getName())) {
			BukkitTask task = Bukkit.getScheduler().runTaskLater(this, new CombatScheduler(this, player), combatCooldown);
			ScheduleList.put(player.getName(), task);
		} else {
			ScheduleList.get(player.getName()).cancel();
			BukkitTask task = Bukkit.getScheduler().runTaskLater(this, new CombatScheduler(this, player), combatCooldown);
			ScheduleList.put(player.getName(), task);
		}
	}
	
	public void cancelTask(Player player) {
		ScheduleList.get(player.getName()).cancel();
		ScheduleList.remove(player.getName());
	}
	
	public boolean isPlayerInCombat(String name) {
		return ScheduleList.containsKey(name);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setActive(boolean active) {
		if(!active) {
			for(BukkitTask bt : ScheduleList.values()) {
				bt.cancel();
			}
			ScheduleList.clear();
		}
		this.active = active;
	}
	
	public void setCooldown(int sec) {
		this.combatCooldown = sec;
	}
	
	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public boolean isBoolean(String str) {
		try {
			Boolean.parseBoolean(str);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean isIgnoreFallDamage() {
		return ignoreFallDamage;
	}
	
	public void setIgnoreFallDamage(boolean bool) {
		this.ignoreFallDamage = bool;
	}
	
	public boolean isIgnoreFallingBlock() {
		return ignoreFallingBlock;
	}
	
	public void isIgnoreFallingBlock(boolean bool) {
		this.ignoreFallingBlock = bool;
	}
}
