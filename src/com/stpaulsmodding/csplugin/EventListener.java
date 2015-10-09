package com.stpaulsmodding.csplugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getCause().equals(DamageCause.FIRE)) {
				for (ItemStack set : player.getInventory().getArmorContents()) {
					switch(set.getType()) {
						case LEATHER_BOOTS:
							set.setDurability((short) (set.getDurability() - 5));
							break;
						case LEATHER_LEGGINGS:
							set.setDurability((short) (set.getDurability() - 6));
							break;
						case LEATHER_CHESTPLATE:
							set.setDurability((short) (set.getDurability() - 6));
							break;
						case LEATHER_HELMET:
							set.setDurability((short) (set.getDurability() - 4));
							break;
						default:
							break;
					}	
				}
			}
		}
	}
}
