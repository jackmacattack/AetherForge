package edu.virginia.cs.sgd.util;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.virginia.cs.sgd.GameOfSwords;

public class TextureRegionManager {

	private String sheetName;
	
	private int width;
	private int height;
	
	private Map<String, Point> bounds;
	
	public TextureRegionManager(String sheetName, int width, int height) {
		this.sheetName = sheetName;
		this.width = width;
		this.height = height;
		
		this.bounds = new HashMap<String, Point>();
	}
	
	public void addRegion(String name, Point p) {
		bounds.put(name, p);
	}
	
	public TextureRegion getRegion(String name) {
		Point p = bounds.get(name);
		
		Texture tex = GameOfSwords.getManager().get(sheetName, Texture.class);
		
		return new TextureRegion(tex, p.x, p.y, p.x + width, p.y + height);
	}
}
