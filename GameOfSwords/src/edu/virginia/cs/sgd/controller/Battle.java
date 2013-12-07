package edu.virginia.cs.sgd.controller;

import java.util.ArrayList;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

import edu.virginia.cs.sgd.game.model.components.Expires;
import edu.virginia.cs.sgd.game.model.components.HP;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.Weapon;

public class Battle {

	@Mapper static ComponentMapper<Stats> statsMapper;
	@Mapper static ComponentMapper<Weapon> weaponMapper;
	@Mapper static ComponentMapper<MapPosition> positionMapper;
	@Mapper static ComponentMapper<HP> hpMapper;
	
	public static boolean OneOnOneFight(Entity attacker, Entity defender) {	
		boolean attackLands = calculateFightDoesHit(attacker, defender);
		boolean killedTarget = false;
		if(attackLands) {
			int damage = calculateFightDamage(attacker, defender);
			HP defenderHP = defender.getComponent(HP.class);//hpMapper.get(defender);
			int newHP = defenderHP.getHP() - damage;
			if(newHP <= 0) {
				newHP = 0;
				defender.addComponent(new Expires());
				killedTarget = true;
			}
			defenderHP.setHP(newHP);
			System.out.println("Attack hit for " + damage + " damage. Target has " + newHP + " health.");
		}
		attacker.changedInWorld();
		defender.changedInWorld();
		return attackLands;
	}
	
	public static void AreaOfEffectFight(Entity attacker, ArrayList<Entity> defenders) {
		
	}
	
	private static boolean calculateFightDoesHit(Entity attacker, Entity defender) {
		Stats attackerStats = attacker.getComponent(Stats.class);//statsMapper.get(attacker);
		Weapon attackerWeapon = attacker.getComponent(Weapon.class);//weaponMapper.get(attacker);
		MapPosition attackerPosition = attacker.getComponent(MapPosition.class);//positionMapper.get(attacker);		
		
		Stats defenderStats = defender.getComponent(Stats.class);//statsMapper.get(defender);	
		MapPosition defenderPosition = defender.getComponent(MapPosition.class);//positionMapper.get(defender);
		
		int range = MapPosition.calculateDistance(attackerPosition, defenderPosition);
		
		int chance = attackerWeapon.getAccuracy() + 10 * range - defenderStats.getAgility();
		System.out.println("Chance of attack landing: " + chance);
		return (Math.random()*(100-0)) < chance;
	}
	
	private static int calculateFightDamage(Entity attacker, Entity defender) {
		Stats attackerStats = attacker.getComponent(Stats.class);//statsMapper.get(attacker);
		Weapon attackerWeapon = attacker.getComponent(Weapon.class);//weaponMapper.get(attacker);
		
		Stats defenderStats = defender.getComponent(Stats.class);//statsMapper.get(defender);	

		int strength = attackerWeapon.isRanged() ? attackerStats.getMeleeAtk() : attackerStats.getMeleeAtk();
		
		int damage  = strength + attackerWeapon.getPower() - defenderStats.getDefense();
		return damage;
	}

	
}
