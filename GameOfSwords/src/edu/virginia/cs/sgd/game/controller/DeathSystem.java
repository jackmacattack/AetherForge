package edu.virginia.cs.sgd.game.controller;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.model.components.Expires;

public class DeathSystem extends EntityProcessingSystem {

	private Level level;
	
	public DeathSystem(Level level) {
		super(Aspect.getAspectForAll(Expires.class));
		
		this.level = level;
	}

	@Override
	protected void process(Entity e) {
		level.remove(e);
	}

}
