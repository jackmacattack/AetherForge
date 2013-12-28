package edu.virginia.cs.sgd.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.virginia.cs.sgd.Entry;

public class TextureRegionManager {

	private Map<String, TextureRegion> map;
	
	public TextureRegionManager(String sheetName, int width, int height, String[] names) {
		
		map = new HashMap<String, TextureRegion>();
		
		TextureRegion tre = new TextureRegion(Entry.getManager().get(sheetName, Texture.class));
		TextureRegion[][] tr = tre.split(width, height);
		
		if(tr.length == 0 || names.length != tr.length * tr[0].length) {
			throw new IllegalArgumentException("Number of names does not match number of regions.");
		}
		
		for(int i = 0; i < tr.length; i++) {
			TextureRegion[] row = tr[i];
			
			for(int j = 0; j < row.length; j++) {
				TextureRegion tex = row[j];
				String name = names[i * tr.length + j];
				
				map.put(name, tex);
			}
		}
	}
	
	public TextureRegion getRegion(String name) {

		return map.get(name);
		
	}
}
