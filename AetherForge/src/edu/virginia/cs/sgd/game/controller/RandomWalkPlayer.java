package edu.virginia.cs.sgd.game.controller;

import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.math.MathUtils;

import edu.virginia.cs.sgd.game.model.MapOperator;
import edu.virginia.cs.sgd.game.model.components.Selection;
import edu.virginia.cs.sgd.game.view.HighlightType;
import edu.virginia.cs.sgd.util.Point;

public class RandomWalkPlayer extends Player {

	public RandomWalkPlayer(String name) {
		super(name);
	}

	@Override
	protected void takeTurn(MapOperator map) {

		List<Point> units = map.getUnits(name);
		for(int i = 0; i < units.size(); i++) {
			Point p = units.get(i);

			map.onTouch(p, name);
			Entity e = map.getEntityAt(p);

			Selection sel = e.getComponent(Selection.class);

			Point newP = null;
			do {
				List<Point> move = sel.getHighlightPos();
				int rand = MathUtils.random(move.size()-1);
				newP = move.get(rand);
			}
			while(sel.getType(newP) != HighlightType.MOVE);

			map.onTouch(newP, name);
			map.onTouch(newP, name);
			
		}

	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub

	}

}
