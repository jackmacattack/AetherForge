package edu.virginia.cs.sgd.game.controller;

import edu.virginia.cs.sgd.game.model.Map;


public abstract class Player {
	
	private Thread t;
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

	protected abstract void takeTurn(Map map);
	
	public void processTurn(final Map map) {
		t = new Thread() {
			public void run() {
				takeTurn(map);
			}
		};
		
		t.start();
	}

	public boolean takingTurn() {
		if(t != null) {
			return t.isAlive();
		}
		return false;
	}

	public abstract void endTurn();
}
