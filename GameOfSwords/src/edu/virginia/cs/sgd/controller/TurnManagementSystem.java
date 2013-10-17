package edu.virginia.cs.sgd.controller;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Array;

public class TurnManagementSystem extends EntitySystem{

	private Array<Integer> unitOrder;

	public TurnManagementSystem(Array<Integer> unitOrder) {
        super(Aspect.getAspectFor(null, null));
        
        this.unitOrder = unitOrder;
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> arg0) {
		// Get the ID of the last entity to move - because they will get reset to 0
        // To be safe, first assume that this is the first turn, so that oldEntity = -1
        int oldEntity = -1;
        
        // Then, if there is a list for unitOrder, the first entity was the one that
        // moved last turn
        if (unitOrder.size > 0) oldEntity = unitOrder.get(0);
		
	}
        

}
