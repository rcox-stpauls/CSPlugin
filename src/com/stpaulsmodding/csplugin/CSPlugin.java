package com.stpaulsmodding.csplugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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
		try {
			File f = new File(getDataFolder() + File.separator);
			if (!f.exists()) f.mkdir();
			file = new File(getDataFolder(), "cached_blocks.ser");
			writer = new ObjectOutputStream(new FileOutputStream(file, true));
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
	
	public void saveTo(List<Block> data) {
		SerializableShip ship = new SerializableShip(data);
		try {
			writer.writeObject(ship);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void wipeFile() {
		try {
			writer.close();
			writer = new ObjectOutputStream(new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SerializableShip loadFile() {
		try {
			SerializableShip ship = (SerializableShip) reader.readObject();
			return ship;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Block> getBlocksFromSelection(World world, Location loc1, Location loc2) {
		List<Block> items = new ArrayList<Block>();
		
		for (int x = Math.min(loc1.getBlockX(), loc2.getBlockX()); x <= Math.max(loc2.getBlockX(), loc1.getBlockX()); x++) {
			for (int y = Math.min(loc1.getBlockY(), loc2.getBlockY()); y <= Math.max(loc2.getBlockY(), loc1.getBlockY()); y++) {
				for (int z = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); z <= Math.max(loc2.getBlockZ(), loc1.getBlockZ()); z++) {
					items.add(world.getBlockAt(x, y, z));
				}
			}
		}
		return items;
	}
	
	@SuppressWarnings("deprecation")
	public void loadBlocksFromHashMap(SerializableShip ship, Location loc) {
		
		int shipXLength = Math.abs(ship.end.get(0)-ship.start.get(0));
		int shipYLength = Math.abs(ship.end.get(1)-ship.start.get(1));
		int shipZLength = Math.abs(ship.end.get(2)-ship.start.get(2));
		int byteNumber = 0;
		
		for (int x = loc.getBlockX(); x <= shipXLength + loc.getBlockX(); x++) {
			for (int y = loc.getBlockY(); y <= shipYLength + loc.getBlockY(); y++) {
				for (int z = loc.getBlockZ(); z <= shipZLength + loc.getBlockZ(); z++) {
					loc.getWorld().getBlockAt(x, y, z).setType(ship.lsM.get(byteNumber));
					loc.getWorld().getBlockAt(x, y, z).setData(ship.ls.get(byteNumber));
					byteNumber++;
				}
			}
		}
	}
}
