package edu.virginia.cs.sgd.game.controller;

import java.util.LinkedList;
import java.util.Queue;

import edu.virginia.cs.sgd.util.Point;

public class InputPlayer extends Player implements UserInput {

	private Queue<Point> q;
	private boolean end;
	
	public InputPlayer(String name, String[] enemies) {
		super(name, enemies);
		
		q = new LinkedList<Point>();
	}
	
	public void onTouch(Point p) {
		q.add(p);
	}
	
	@Override
	protected void takeTurn(MapOperator map) {
		end = false;
		while(!end) {
			Point p = q.poll();
			
			if(p != null) {
				map.onTouch(p, name);
			}
			
		}
	}

	@Override
	public void endTurn() {
		end = true;
	}

}
