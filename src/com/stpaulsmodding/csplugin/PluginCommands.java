package com.stpaulsmodding.csplugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommands implements CommandExecutor {
	private final CSPlugin plugin;
	private Location firstSelection;
	private Location secondSelection;
	
	public PluginCommands(CSPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("airships")) {
			if (!(sender instanceof Player)) {
				return false;
			}
			if (args.length < 1) {
				return false;
			}
			Player player = (Player) sender;
			
			if (args[0].equalsIgnoreCase("setfirst")) {
				firstSelection = player.getLocation();
				player.sendMessage("First location set.");
			} else if (args[0].equalsIgnoreCase("setsecond")) {
				secondSelection = player.getLocation();
				player.sendMessage("Second location set.");
			} else if (args[0].equalsIgnoreCase("saveship")) {
				plugin.wipeFile();
				plugin.saveTo(plugin.getBlocksFromSelection(player.getWorld(), firstSelection, secondSelection));
			} else if (args[0].equalsIgnoreCase("loadship")) {
				plugin.loadBlocksFromHashMap(plugin.loadFile(), player.getLocation());
			}
		}
		
		return false;
	}
}
