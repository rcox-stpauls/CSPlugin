package com.stpaulsmodding.csplugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.plugin.java.JavaPlugin;

public class CSPlugin extends JavaPlugin {
	private PrintWriter printwriter;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getCommand("").setExecutor(new PluginCommands(this));
		try {
			File file = new File(getDataFolder(), "cached_blocks.log");
			if (!file.exists()) {
				file.createNewFile();
			}
			printwriter = new PrintWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		printwriter.close();
	}
	
	public void saveBlocks(String data) {
		printwriter.print(data);
		printwriter.flush();
	}
}
