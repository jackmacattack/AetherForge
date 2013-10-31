package edu.virginia.cs.sgd.controller;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;

import edu.virginia.cs.sgd.menu.MapScreen;
import edu.virginia.cs.sgd.model.MapPosition;

public class MovementSystem extends EntityProcessingSystem{
	
	MapScreen map;

	public MovementSystem(MapScreen map) {
		super(Aspect.getAspectForAll(MapPosition.class));
		this.map = map;
	}

	@Override
	protected void process(Entity e) {
		
		
	}


}
