package edu.virginia.cs.sgd.game.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.virginia.cs.sgd.util.Point;

public class InputPlayer extends Player implements UserInput {

	private BlockingQueue<Point> q;
	private boolean end;
	
	public InputPlayer(String name, String[] enemies) {
		super(name, enemies);
		
		q = new LinkedBlockingQueue<Point>();
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
