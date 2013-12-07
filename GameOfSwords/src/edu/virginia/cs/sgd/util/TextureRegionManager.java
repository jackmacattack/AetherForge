package edu.virginia.cs.sgd.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.virginia.cs.sgd.GameOfSwords;

public class TextureRegionManager {

	private String sheetName;
	
	private int width;
	private int height;
	
	private TextureRegion[][] tr;
	
	private Map<String, TextureRegion> bounds;
	
	public TextureRegionManager(String sheetName, int width, int height) {
		this.sheetName = sheetName;
		this.width = width;
		this.height = height;
		TextureRegion tre = new TextureRegion(GameOfSwords.getManager().get(sheetName, Texture.class));
		tr = tre.split(width, height);
		
		this.bounds = new HashMap<String, TextureRegion>();
	}
	
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public TextureRegion[][] getTr() {
		return tr;
	}

	public void addRegion(String name, TextureRegion tg) {
		bounds.put(name, tg);
	}
	
	public TextureRegion getRegion(String name) {

		return bounds.get(name);
	
//		Texture tex = GameOfSwords.getManager().get(sheetName, Texture.class);
//		
//		return new TextureRegion(tex, (int)p.x, (int)p.y, (int)p.x + width, (int)p.y + height);
	}
}
