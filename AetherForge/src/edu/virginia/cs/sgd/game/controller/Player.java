package edu.virginia.cs.sgd.game.controller;

import edu.virginia.cs.sgd.game.Level;


public abstract class Player {
	
	protected String name;
	
	public Player(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected abstract void takeTurn(Level level);
	
	public void processTurn(final Level level, final Controller c) {
		Thread t = new Thread() {
			public void run() {
				takeTurn(level);
				c.endTurn(level);
			}
		};
		
		t.start();
	}
	
}
