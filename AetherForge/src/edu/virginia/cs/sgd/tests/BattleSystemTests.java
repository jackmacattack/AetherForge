package edu.virginia.cs.sgd.tests;

//import static org.junit.Assert.*;
import org.junit.Test;
import com.artemis.*;

import edu.virginia.cs.sgd.game.controller.Battle;
import edu.virginia.cs.sgd.game.model.components.*;

public class BattleSystemTests {

	@Test
	public void oneOnOneBattleTest() {
		World theWorld = new World();
		Entity attacker = theWorld.createEntity();
		attacker.addComponent(new Stats());
		attacker.addComponent(new HP());
		attacker.addComponent(new Weapon(false));
		attacker.addComponent(new MapPosition(0,0));
		attacker.addToWorld();
		
		Entity defender = theWorld.createEntity();
		defender.addComponent(new Stats());
		defender.addComponent(new HP());
		defender.addComponent(new Weapon(false));
		defender.addComponent(new MapPosition(1,0));
		defender.addToWorld();
		
		int hpBeforeAttack = defender.getComponent(HP.class).getHP();
		assert(MapPosition.calculateDistance(attacker.getComponent(MapPosition.class), defender.getComponent(MapPosition.class)) == 1);
				
		boolean didHit = Battle.OneOnOneFight(attacker, defender);
		int hpAfterAttack = defender.getComponent(HP.class).getHP();
		
		if(didHit)
			assert(hpBeforeAttack > hpAfterAttack);
		else
			assert(hpBeforeAttack == hpAfterAttack);
		
		System.out.println("HP before: " + hpBeforeAttack + "\nHP after: " + hpAfterAttack);

		}
	

}
