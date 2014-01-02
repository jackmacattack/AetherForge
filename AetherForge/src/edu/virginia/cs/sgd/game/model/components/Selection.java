package edu.virginia.cs.sgd.game.model.components;

import java.util.ArrayList;

import edu.virginia.cs.sgd.game.view.SelectionType;
import edu.virginia.cs.sgd.util.Point;

public class Selection {

	private ArrayList<Point> selPos;
	private ArrayList<SelectionType> selType;
	
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
	
}
