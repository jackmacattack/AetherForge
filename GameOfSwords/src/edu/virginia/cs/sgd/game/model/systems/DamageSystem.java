package edu.virginia.cs.sgd.game.model.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import edu.virginia.cs.sgd.game.model.components.Damage;
import edu.virginia.cs.sgd.game.model.components.HP;
import edu.virginia.cs.sgd.game.model.components.Expires;



public class DamageSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Damage> dm;
	@Mapper ComponentMapper<HP> hp;
	
	@SuppressWarnings("unchecked")
	public DamageSystem()
	{
		super(Aspect.getAspectForAll(Damage.class));
	}
	@Override
	protected void process(Entity e) {
		Damage d = dm.get(e);
		HP health = hp.get(e);
		if (health == null)
		{
			System.out.println("Entity "+e.getId() +" had no health");
			return;
		}
		health.loseHealth(d.damage);
		if (health.isOutOfHealth())
		{
			e.addComponent(new Expires());
		}
	}

}
