package com.stpaulsmodding.csplugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import net.minecraft.server.v1_8_R3.IBlockData;

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
		for (Integer[] key : ship.blocks.keySet()) {
			Block block = loc.getWorld().getBlockAt(key[0], key[1], key[2]);
			block.setType((Material) ship.blocks.get(key)[0]);
			block.setData((Byte) ship.blocks.get(key)[1]);
		}
	}
	
	public void convertToEntities(SerializableShip ship) {
		for (Block block : ship.rawBlocks) {
			EntityBat bat = new EntityBat(ship.getWorld());
			bat.setPosition(block.getX()+0.5D, block.getY()+0.5D, block.getZ()+0.5D);
			bat.setInvisible(true);
			
			CraftBlock block1 = (CraftBlock) block;
			EntityFallingBlock fall = new EntityFallingBlock(ship.getWorld(), block.getX(), block.getY(), block.getZ(), block1, block1.getData());
			
			ship.getWorld().addEntity(bat);
			ship.getWorld().addEntity(fall);
			
			fall.mount(bat);
		}
	}
}
