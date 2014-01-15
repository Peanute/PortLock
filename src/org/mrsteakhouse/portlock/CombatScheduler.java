package org.mrsteakhouse.portlock;

import org.bukkit.entity.Player;

public class CombatScheduler implements Runnable{
	private PortLock plugin;
	private Player player;
	
	public CombatScheduler(PortLock plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	@Override
	public void run() {
		plugin.cancelTask(player);
	}

}
