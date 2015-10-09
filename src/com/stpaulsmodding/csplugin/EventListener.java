package com.stpaulsmodding.csplugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EventListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			if () {
				
			}
		}
	}
}
