package edu.virginia.cs.sgd.game.view;

import java.util.Map;
import java.util.TreeMap;

public class SpriteManager {

	private Map<Integer, Sprite> sprites;
	
	public SpriteManager() {
		sprites = new TreeMap<Integer, Sprite>();
	}

	public void addSprite(Sprite sprite) {
		sprites.put(sprite.getModelId(), sprite);
	}
	
	public void removeSprite(int id) {
		sprites.remove(id);
	}
	
	public void update() {
		
	}
	
	public void draw() {
	
		for(Sprite s : sprites.values()) {
			
		}
		
	}
}
