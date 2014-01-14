package edu.virginia.cs.sgd.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRegionManager {

	private Map<String, TextureRegion> map;
	
	public TextureRegionManager(String sheetName, int width, int height, String[] names) {
		
		map = new HashMap<String, TextureRegion>();
		
		Texture tex = SingletonAssetManager.getInstance().get(sheetName);
		TextureRegion texRe = new TextureRegion(tex);
		
		TextureRegion[][] texArr = texRe.split(width, height);
		
		if(texArr.length == 0 || names.length != texArr.length * texArr[0].length) {
			throw new IllegalArgumentException("Number of names does not match number of regions.");
		}
		
		for(int i = 0; i < texArr.length; i++) {
			TextureRegion[] row = texArr[i];
			
			for(int j = 0; j < row.length; j++) {
				TextureRegion t = row[j];
				String name = names[i * texArr.length + j];
				
				map.put(name, t);
			}
		}
	}
	
	public TextureRegion getRegion(String name) {

		return map.get(name);
		
	}
}
