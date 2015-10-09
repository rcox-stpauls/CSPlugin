package com.stpaulsmodding.csplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class CSPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventListener(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
