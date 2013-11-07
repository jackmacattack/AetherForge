package edu.virginia.cs.sgd.controller;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;

import edu.virginia.cs.sgd.menu.MapScreen;
import edu.virginia.cs.sgd.model.MapPosition;

public class MovementSystem extends EntityProcessingSystem{
	@Mapper ComponentMapper<MapPosition> pos;
	
	MapScreen map;

	@SuppressWarnings("unchecked")
	public MovementSystem(MapScreen map) {
		super(Aspect.getAspectForAll(MapPosition.class));
		this.map = map;
	}

	@Override
	protected void process(Entity e) {
		MapPosition	mp = e.getComponent(MapPosition.class);
		int move = 3;
		System.out.println(mp);
		System.out.println("Yay I can move");
	}


}
