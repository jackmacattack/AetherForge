package edu.virginia.cs.sgd.game.controller;

import com.artemis.Entity;

import edu.virginia.cs.sgd.game.Level;

public abstract class Player {

	private Level level;
	private Entity selected;
	
	public abstract void processTurn();
	
	protected void select(Entity e) {
		selected = e;
		level.select(e);
	}
}
