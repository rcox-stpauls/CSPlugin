package com.stpaulsmodding.csplugin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.World;

public class SerializableShip implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Map<Integer[], Object[]> blocks = new HashMap<Integer[], Object[]>();
	public transient List<Block> rawBlocks;
	
	public SerializableShip(List<Block> ls) {
		rawBlocks = ls;
		for (Block block : ls) {
			Integer[] coords = {block.getX(), block.getY(), block.getZ()};
			@SuppressWarnings("deprecation")
			Object[] data = {block.getType(), block.getData()};
			
			blocks.put(coords, data);
		}
	}
	
	public World getWorld() {
		return ((CraftWorld) rawBlocks.get(0).getWorld()).getHandle();
	}
}
