package edu.virginia.cs.sgd.game.model.components;

import java.util.ArrayList;

import com.artemis.Component;

import edu.virginia.cs.sgd.game.view.HighlightType;
import edu.virginia.cs.sgd.util.Point;

public class Selection extends Component {

	private boolean moved;
	private ArrayList<Point> highlightPos;
	private ArrayList<HighlightType> highlightType;
	
	public Selection() {
		moved = false;
		
		highlightPos = new ArrayList<Point>();
		highlightType = new ArrayList<HighlightType>();
	}
	
	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public ArrayList<Point> getHighlightPos() {
		return highlightPos;
	}

	public ArrayList<HighlightType> getHighlightType() {
		return highlightType;
	}

	public void addTile(Point pos, HighlightType type) {
		highlightPos.add(pos);
		highlightType.add(type);
	}
	
	public void resetTiles() {
		highlightPos.clear();
		highlightType.clear();
	}
	
	public HighlightType getType(Point pos) {
		for(int i = 0; i < highlightPos.size(); i++) {
			Point p = highlightPos.get(i);
			if(p.equals(pos)) {
				return highlightType.get(i);
			}
		}
		
		return HighlightType.NONE;
	}
	
}
