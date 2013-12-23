package edu.virginia.cs.sgd.game.model.components;

import java.util.ArrayList;

import com.artemis.Component;

import edu.virginia.cs.sgd.game.view.HighlightType;

public class Selection extends Component {

	ArrayList<MapPosition> highlightPos;
	ArrayList<HighlightType> highlightType;
	
	public Selection() {
		highlightPos = new ArrayList<MapPosition>();
		highlightType = new ArrayList<HighlightType>();
	}
	
	public void addTile(MapPosition pos, HighlightType type) {
		highlightPos.add(pos);
		highlightType.add(type);
	}

	public ArrayList<MapPosition> getHighlightPos() {
		return highlightPos;
	}

	public ArrayList<HighlightType> getHighlightType() {
		return highlightType;
	}
	
}
