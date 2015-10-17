package com.stpaulsmodding.csplugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public class CSPlugin extends JavaPlugin {
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private File file;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getCommand("airships").setExecutor(new PluginCommands(this));
		getCommand("").setExecutor(new PluginCommands(this));
		try {
			file = new File(getDataFolder(), "cached_blocks.log");
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new ObjectOutputStream(new FileOutputStream(file));
			reader = new ObjectInputStream(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		try {
			writer.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveTo(Map<Location, Block> data) {
		try {
			writer.writeObject(data);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void wipeFile() {
		file.delete();
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<Location, Block> loadFile() {
		try {
			@SuppressWarnings("unchecked")
			Map<Location, Block> obj = (Map<Location, Block>) reader.readObject();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<Location, Block> getBlocksFromSelection(World world, Location loc1, Location loc2) {
		Map<Location, Block> items = new HashMap<Location, Block>();
		for (int x = loc1.getBlockX(); x < loc2.getBlockX(); x++) {
			for (int y = loc1.getBlockY(); y < loc2.getBlockY(); y++) {
				for (int z = loc1.getBlockZ(); z < loc2.getBlockZ(); z++) {
					items.put(new Location(world, x, y, z), world.getBlockAt(x, y, z));
				}
			}
		}
		return items;
	}
	
	@SuppressWarnings("deprecation")
	public void loadBlocksFromHashMap(Map<Location, Block> map, Location loc) {
		for (Location blockloc : map.keySet()) {
			Block toChange = loc.getWorld().getBlockAt(loc.getBlockX()+blockloc.getBlockX(), loc.getBlockY()+blockloc.getBlockY(), loc.getBlockZ()+blockloc.getBlockZ());
			toChange.setData(map.get(blockloc).getData());
		}
	}
}
