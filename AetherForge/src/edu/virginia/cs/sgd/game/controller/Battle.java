package edu.virginia.cs.sgd.game.controller;

import com.artemis.Entity;

import edu.virginia.cs.sgd.game.model.components.Expires;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.game.model.components.Weapon;

public class Battle {
	public static boolean OneOnOneFight(Entity attacker, Entity defender) {

		String aName = attacker.getComponent(TextureName.class).getName();
		String dName = defender.getComponent(TextureName.class).getName();
		
		boolean attackLands = calculateFightDoesHit(attacker, defender);
//		SpriteAnimation att = sam.get(attacker);
//		//start attack animation
//		att.animation.setAnimated(true);
		if(attackLands) {
			int damage = calculateFightDamage(attacker, defender);
			Stats defenderHP = defender.getComponent(Stats.class);
			int newHP = defenderHP.getHealth() - damage;
			if(newHP <= 0) {
				newHP = 0;
				defender.addComponent(new Expires());
			}
			defenderHP.setHealth(newHP);
			System.out.println("Attack of " + aName + " hit for " + damage + " damage. " + dName + " has " + newHP + " health.");
		}
		else {
			System.out.println(aName + "'s attack missed.");
		}
		//end attack animation
		//att.animation.setAnimated(false);
		attacker.changedInWorld();
		defender.changedInWorld();
		return attackLands;
	}
	
	private static boolean calculateFightDoesHit(Entity attacker, Entity defender) {

		Weapon attackerWeapon = attacker.getComponent(Weapon.class);
		Stats defenderStats = defender.getComponent(Stats.class);	
		
		int chance = attackerWeapon.getAccuracy() - defenderStats.getAgility();
		
		System.out.println("Chance of attack landing: " + chance);
		
		return (Math.random()*(100-0)) < chance;
	}
	
	private static int calculateFightDamage(Entity attacker, Entity defender) {
		Stats attackerStats = attacker.getComponent(Stats.class);
		Weapon attackerWeapon = attacker.getComponent(Weapon.class);
		
		Stats defenderStats = defender.getComponent(Stats.class);	

		int strength = attackerWeapon.isRanged() ? attackerStats.getMeleeAtk() : attackerStats.getMeleeAtk();
		
		int damage  = strength + attackerWeapon.getPower() - defenderStats.getDefense();
		return damage;
	}

	
}
