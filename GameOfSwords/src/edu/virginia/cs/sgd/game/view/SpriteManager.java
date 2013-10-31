package edu.virginia.cs.sgd.game.view;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteManager {

	private SpriteBatch batch;
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
	
	public void draw() {//Map<Integer, Point> positions) {
	
		batch.begin();
		
		for(Sprite s : sprites.values()) {
			Point pos = new Point(40, 40); //positions.get(s.getModelId());
			batch.draw(s.getImage(), (float) pos.getX(), (float) pos.getY());
		}
		
		batch.end();
	}
}
