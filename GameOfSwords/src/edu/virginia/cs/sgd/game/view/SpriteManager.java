package edu.virginia.cs.sgd.game.view;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import edu.virginia.cs.sgd.game.Level;

public class SpriteManager {

	private int size;
	private float scale;
	
	private SpriteBatch batch;
	private Map<Integer, Sprite> sprites;

	public SpriteManager(int size, float scale, SpriteBatch batch) {
		this.size = size;
		this.scale = scale;
		
		this.batch = batch;
		sprites = new TreeMap<Integer, Sprite>();
	}

	public void addSprite(Sprite sprite) {
		sprites.put(sprite.getModelId(), sprite);
	}
	
	public void removeSprite(int id) {
		sprites.remove(id);
	}
	
	public void draw(Level l) {
	
		batch.begin();
		
		for(Sprite s : sprites.values()) {
			Vector2 pos = l.getPosition(s.getModelId());
			
			batch.draw(s.getImage(), (float) pos.x * size, (float) pos.y * size, 
					0, 0, size, size, scale, scale, 0);
		}
		
		batch.end();
	}
}
