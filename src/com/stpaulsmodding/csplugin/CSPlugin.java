package com.stpaulsmodding.csplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CSPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("smiteall")) {
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				player.getWorld().strikeLightning(player.getLocation());
				player.setHealth(0.0D);
			}
		}
		
		return false;
	}
}
