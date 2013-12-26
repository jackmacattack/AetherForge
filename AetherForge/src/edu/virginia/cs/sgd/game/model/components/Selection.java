package edu.virginia.cs.sgd.game.model.components;

import java.util.ArrayList;

import com.artemis.Component;

import edu.virginia.cs.sgd.game.view.HighlightType;

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
	
	public HighlightType getType(MapPosition pos) {
		for(int i = 0; i < highlightPos.size(); i++) {
			MapPosition m = highlightPos.get(i);
			if(m.equals(pos)) {
				return highlightType.get(i);
			}
		}
		
		return HighlightType.NONE;
	}
	
}
