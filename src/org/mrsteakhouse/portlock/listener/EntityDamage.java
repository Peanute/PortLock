package org.mrsteakhouse.portlock.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.mrsteakhouse.portlock.PortLock;

public class EntityDamage implements Listener{
	private PortLock plugin;
	
	public EntityDamage(PortLock plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) { return; }
		if(!plugin.isActive()) { return; }
		if(((Player) event.getEntity()).hasPermission("portlock.ignore")) { return; }
		
		if(event.getCause() == DamageCause.FALL && plugin.isIgnoreFallDamage()) {
			return;
		}
		
		if(event.getCause() == DamageCause.FALLING_BLOCK && plugin.isIgnoreFallingBlock()) {
			return;
		}
		
		Player player = (Player)event.getEntity();
		plugin.schedulePlayer(player);
	}
}
