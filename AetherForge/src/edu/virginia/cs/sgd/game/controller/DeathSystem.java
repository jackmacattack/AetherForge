package edu.virginia.cs.sgd.game.controller;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.managers.PlayerManager;
import com.artemis.systems.EntityProcessingSystem;

import edu.virginia.cs.sgd.game.model.components.Expires;

public class DeathSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public DeathSystem() {
		super(Aspect.getAspectForAll(Expires.class));
	}

	@Override
	protected void process(Entity e) {
		PlayerManager man = world.getManager(PlayerManager.class);
		man.removeFromPlayer(e);
		world.deleteEntity(e);
	}

}
