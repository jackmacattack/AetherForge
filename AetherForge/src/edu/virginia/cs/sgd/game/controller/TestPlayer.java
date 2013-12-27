package edu.virginia.cs.sgd.game.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.util.Point;

public class TestPlayer extends Player {

	private BlockingQueue<Point> q;
	private boolean end;
	
	public TestPlayer(String name) {
		super(name);
		
		q = new LinkedBlockingQueue<Point>();
	}
	
	public void select(Point p) {
		try {
			q.put(p);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void takeTurn(Level level) {
		while(!end) {
			Point p = q.poll();
			
			if(p != null) {
				level.select(p.getX(), p.getY());
			}
		}
	}

}
