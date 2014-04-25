package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;

import edu.virginia.cs.sgd.game.view.SelectionType;
import edu.virginia.cs.sgd.util.Point;

public class Selection {

	private ArrayList<Point> selPos;
	private ArrayList<SelectionType> selType;

	private float health = 0;
	private float mana = 0;
	private String tex = "";
	
	public Selection() {
		
		selPos = new ArrayList<Point>();
		selType = new ArrayList<SelectionType>();
	}

	public ArrayList<Point> getSelPos() {
		return selPos;
	}

	public ArrayList<SelectionType> getSelType() {
		return selType;
	}

	public void addTile(Point pos, SelectionType type) {
		selPos.add(pos);
		selType.add(type);
	}
	
	public void resetTiles() {
		selPos.clear();
		selType.clear();
	}
	
	public SelectionType getType(Point pos) {
		int i = selPos.indexOf(pos);
		
		if(i != -1) {
			return selType.get(i);
		}
		
		return SelectionType.NONE;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health_) {
		health = health_;
	}

	public float getMana() {
		return mana;
	}

	public void setMana(float mana) {
		this.mana = mana;
	}

	public String getTex() {
		return tex;
	}

	public void setTex(String tex) {
		this.tex = tex;
	}
	
}
