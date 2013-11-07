package edu.virginia.cs.sgd.game.view;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.virginia.cs.sgd.game.Level;

public class SpriteManager {

	private SpriteBatch batch;
	private Map<Integer, Sprite> sprites;

	public SpriteManager(SpriteBatch batch) {
		this.batch = batch;
		sprites = new TreeMap<Integer, Sprite>();
	}

	public void addSprite(Sprite sprite) {
		sprites.put(sprite.getModelId(), sprite);
	}
	
	public void removeSprite(int id) {
		sprites.remove(id);
	}
	
	public void draw(Level l) {//Map<Integer, Point> positions) {
	
		batch.begin();
		
		for(Sprite s : sprites.values()) {
			Point pos = l.getPosition(s.getModelId());
			
			batch.draw(s.getImage(), (float) pos.getX(), (float) pos.getY(),
					0, 0, 32, 32, 1/32f, 1/32f, 0);
		}
		
		batch.end();
	}
}