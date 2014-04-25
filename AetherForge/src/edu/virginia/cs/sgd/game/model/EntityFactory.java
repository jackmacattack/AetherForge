
package edu.virginia.cs.sgd.game.model;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.PlayerManager;

import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Sprite;
import edu.virginia.cs.sgd.game.model.components.SpriteAnimation;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.util.Animation;
import edu.virginia.cs.sgd.util.Point;

public class EntityFactory {
	

	public static Entity createCharacter(World world, Point p, String name, String player) {
		
		Entity e = world.createEntity();
		float defaultDuration = 15f;
		e.addComponent(new Sprite(name));
		e.addComponent(new SpriteAnimation(0, defaultDuration, Animation.LOOP));
		e.addComponent(new MapPosition(p));
		e.addComponent(new Stats());
		
		boolean ranged = name.equals("sorc") || name.equals("archer");
		e.addComponent(new Weapon(ranged));
		
		e.addComponent(new TextureName(name));
		e.addToWorld();

		PlayerManager teams = world.getManager(PlayerManager.class);
		teams.setPlayer(e, player);

		return e;
	}

}
