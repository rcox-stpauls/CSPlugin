package com.stpaulsmodding.csplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PluginCommands implements CommandExecutor {
	@SuppressWarnings("unused")
	private final CSPlugin plugin;
	
	public PluginCommands(CSPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("airships")) {
			
		}
		
		return false;
	}
}
