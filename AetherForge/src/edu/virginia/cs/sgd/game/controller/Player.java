package edu.virginia.cs.sgd.game.controller;



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

	protected abstract void takeTurn(MapOperator map);
	
	public void processTurn(final MapOperator map) {
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
