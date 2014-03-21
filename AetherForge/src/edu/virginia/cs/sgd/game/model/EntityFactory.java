package edu.virginia.cs.sgd.game.model;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.PlayerManager;

import edu.virginia.cs.sgd.game.model.components.HP;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.util.Point;

public class EntityFactory {


	public static Entity createCharacter(World world, Point p, String name, String player, boolean ranged) {
		
		Entity e = world.createEntity();
		e.addComponent(new MapPosition(p));
		e.addComponent(new Stats());
		e.addComponent(new HP());
		e.addComponent(new Weapon(ranged));
		e.addComponent(new TextureName(name));
		e.addToWorld();

		PlayerManager teams = world.getManager(PlayerManager.class);
		teams.setPlayer(e, player);

		return e;
	}

}
