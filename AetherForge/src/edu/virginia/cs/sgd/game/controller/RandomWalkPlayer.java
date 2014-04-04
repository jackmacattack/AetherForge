package edu.virginia.cs.sgd.game.controller;

import java.util.List;

import com.badlogic.gdx.math.MathUtils;

import edu.virginia.cs.sgd.game.model.Selection;
import edu.virginia.cs.sgd.game.view.SelectionType;
import edu.virginia.cs.sgd.util.Point;

public class RandomWalkPlayer extends Player {

	public RandomWalkPlayer(String name, String[] enemies) {
		super(name, enemies);
	}

	@Override
	protected void takeTurn(MapOperator map) {

		List<Point> units = map.getUnitComponents(name);
		for(int i = 0; i < units.size(); i++) {
			Point p = units.get(i);

			map.onTouch(p, name);
			
			Selection sel = map.getSelection();
			Point newP = null;
			do {
				List<Point> move = sel.getSelPos();
				int rand = MathUtils.random(move.size()-1);
				newP = move.get(rand);
			}
			while(sel.getType(newP) != SelectionType.MOVE);

			map.onTouch(newP, name);
			map.onTouch(newP, name);
			
		}

	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub

	}

}
