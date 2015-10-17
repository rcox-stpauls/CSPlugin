package com.stpaulsmodding.csplugin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;

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
	
	public List<Block> getBlocks() {
		return rawBlocks;
	}
}
