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
				sender.sendMessage("§c§o");
				return true;
			}
			if (args.length < 1) {
				return true;
			}
			Player player = (Player) sender;
			
			switch (args[0].toLowerCase()) {
				case "setfirst":
					firstSelection = player.getLocation();
					player.sendMessage("§a§oFirst location set!");
					break;
				case "setsecond":
					secondSelection = player.getLocation();
					player.sendMessage("§a§oSecond location set!");
					break;
				case "saveship":
					if (firstSelection == null || secondSelection == null) {
						player.sendMessage("§c§oSet both corners of the region before saving a ship. Do this with:");
						player.sendMessage("§c§o/airships setfirst, and /airships setsecond.");
						break;
					}
					plugin.wipeFile();
					plugin.saveTo(plugin.getBlocksFromSelection(player.getWorld(), firstSelection, secondSelection));
					player.sendMessage("§a§oShip saved!");
					break;
				case "loadship":
					plugin.loadBlocksFromHashMap(plugin.loadFile(), player.getLocation());
					player.sendMessage("§a§oShip loaded!");
					break;
				default:
					player.sendMessage("§c§oIncorrect argument. Type '/airships' to show valid arguments.");
			}
		}
		
		return true;
	}
}
