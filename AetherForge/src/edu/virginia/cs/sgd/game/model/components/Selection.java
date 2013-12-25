package edu.virginia.cs.sgd.game.model.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import com.artemis.Component;

import edu.virginia.cs.sgd.game.view.HighlightType;
import edu.virginia.cs.sgd.util.Triple;

public class Selection extends Component {

	private boolean moved;
	private ArrayList<MapPosition> highlightPos;
	private ArrayList<HighlightType> highlightType;
	
	public Selection() {
		moved = false;
		
		highlightPos = new ArrayList<MapPosition>();
		highlightType = new ArrayList<HighlightType>();
	}
	
	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public ArrayList<MapPosition> getHighlightPos() {
		return highlightPos;
	}

	public ArrayList<HighlightType> getHighlightType() {
		return highlightType;
	}

	public void addTile(MapPosition pos, HighlightType type) {
		highlightPos.add(pos);
		highlightType.add(type);
	}
	
	public void resetTiles() {
		highlightPos.clear();
		highlightType.clear();
	}
	
}
