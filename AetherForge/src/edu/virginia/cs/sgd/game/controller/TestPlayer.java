package edu.virginia.cs.sgd.game.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.virginia.cs.sgd.game.model.MapOperator;
import edu.virginia.cs.sgd.util.Point;

public class TestPlayer extends Player implements UserInput {

	private BlockingQueue<Point> q;
	private boolean end;
	
	public TestPlayer(String name) {
		super(name);
		
		q = new LinkedBlockingQueue<Point>();
	}
	
	public void onTouch(Point p) {
		try {
			q.put(p);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void takeTurn(MapOperator map) {
		end = false;
		int count = 0;
		while(!end) {
			Point p = q.poll();
			
			if(p != null) {
//				System.out.println(p);
				map.onTouch(p, name);
				count++;
			}
			
			end = count > 2;
		}
	}

	@Override
	public void endTurn() {
		end = true;
	}

}
