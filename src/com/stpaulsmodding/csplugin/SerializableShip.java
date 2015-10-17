package com.stpaulsmodding.csplugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class SerializableShip implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public List<Byte> ls = new ArrayList<Byte>();
	public List<Material> lsM = new ArrayList<Material>();
	public List<Integer> start = new ArrayList<Integer>();
	public List<Integer> end = new ArrayList<Integer>();
	
	public SerializableShip(List<Block> ls) {
		start.add(ls.get(0).getX());
		start.add(ls.get(0).getY());
		start.add(ls.get(0).getZ());
		
		end.add(ls.get(ls.size()-1).getX());
		end.add(ls.get(ls.size()-1).getY());
		end.add(ls.get(ls.size()-1).getZ());
		
		for (Block block : ls) {
			@SuppressWarnings("deprecation")
			byte data = block.getData();
			this.ls.add(data);
			this.lsM.add(block.getType());
		}
	}
}
