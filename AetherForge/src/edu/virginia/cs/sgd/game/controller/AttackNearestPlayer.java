package edu.virginia.cs.sgd.game.controller;

import java.util.List;

import edu.virginia.cs.sgd.game.model.Selection;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.util.Point;

public class AttackNearestPlayer extends Player {

	public AttackNearestPlayer(String name, String[] enemies) {
		super(name, enemies);
	}

	@Override
	protected void takeTurn(MapOperator map) {

		List<Point> units = map.getUnitComponents(name);
		
		for(Point unit : units) {

			int e = map.getEntityAt(unit);
			System.out.println(e + ": " + unit);
			Weapon w = map.getComponent(e, Weapon.class);
			
			int maxRange = w.getMaxRange();
			int dist = Integer.MAX_VALUE;
			Point target = null;
			for(String en : enemies) {
				List<Point> enemyUnits = map.getUnitComponents(en);

				for(Point p : enemyUnits) {
					int newDist = Math.abs(unit.getX() - p.getX()) + Math.abs(unit.getY() - p.getY());
					if(newDist < dist) {
						dist = newDist;
						target = p;
					}
				}
			}
			if(target != null) {

				map.onTouch(unit, name);
				
				Selection sel = map.getSelection();
				List<Point> move = sel.getSelPos();

				Point bestPlace = unit;

				if(dist != maxRange) {
					for(Point place : move) {
						int newDist = Math.abs(place.getX() - target.getX()) + Math.abs(place.getY() - target.getY());

						if(Math.abs(newDist - maxRange) < Math.abs(dist - maxRange)) {
							dist = newDist;
							bestPlace = place;
						}
					}
				}

				map.onTouch(bestPlace, name);
				map.onTouch(target, name);
			}


		}

	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub

	}

}
