package edu.virginia.cs.sgd.controller;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Array;

import edu.virginia.cs.sgd.model.Stats;

public class TurnManagementSystem extends EntitySystem{
	@Mapper ComponentMapper<Stats> sm;

	private Array<Integer> units;

	@SuppressWarnings("unchecked")
	public TurnManagementSystem(Array<Integer> unitOrder) {
        super(Aspect.getAspectForAll(Stats.class));
        
        this.units= unitOrder;
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		// Get the ID of the last entity to move - because they will get reset to 0
        // To be safe, first assume that this is the first turn, so that oldEntity = -1
        int oldEntity = -1;
        // Then, if there is a list for units, the first entity was the one that
        // moved last turn
        //If not, create a list for the units in no particular order
        if (units.size > 0) {
        	oldEntity = units.get(0);
        	Entity en = entities.get(oldEntity);
        	en.getComponent(Stats.class).setHasTakenTurn(true);
        	units.removeIndex(0);
        }else{ 
        	for (int i = 0; i < entities.size(); i++){
        		Entity e = entities.get(i);
        		System.out.println(e);
        		Stats stats = e.getComponent(Stats.class);
        		if(stats.hasTakenTurn == false)
        			units.add(e.getId());
        	}
        }
		
	}
        

}
