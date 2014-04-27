package edu.virginia.cs.sgd.game.controller;

import edu.virginia.cs.sgd.util.Threader;

public abstract class Player {
	
	protected String name;
	protected String[] enemies;
	
	public Player(String name, String[] enemies) {
		super();
		this.name = name;
		this.enemies = enemies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected abstract void takeTurn(MapOperator map);
	
	public void processTurn(final MapOperator map) {
		
		Threader.getInstance().execute(new Runnable() {
			public void run() {
				takeTurn(map);
			}
		});
		
	}

	public boolean takingTurn() {
		return Threader.getInstance().isExecuting();
	}

	public abstract void endTurn();
}
