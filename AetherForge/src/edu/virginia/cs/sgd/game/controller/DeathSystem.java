package edu.virginia.cs.sgd.game.controller;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import edu.virginia.cs.sgd.game.model.Map;
import edu.virginia.cs.sgd.game.model.components.Expires;

public class DeathSystem extends EntityProcessingSystem {

	private Map map;
	
	@SuppressWarnings("unchecked")
	public DeathSystem(Map map) {
		super(Aspect.getAspectForAll(Expires.class));
		
		this.map = map;
	}

	@Override
	protected void process(Entity e) {
		map.remove(e);
	}

}
