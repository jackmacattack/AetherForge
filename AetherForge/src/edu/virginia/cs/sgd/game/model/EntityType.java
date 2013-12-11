package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.math.MathUtils;

import edu.virginia.cs.sgd.game.model.components.Stats;

public class EntityType {

	private int[] health, mana, meleeAtk, rangeAtk, defense, resistance, accuracy, agility;
	private int movement;
	private TreeMap<String, Integer> skillTree;
	private String sprite;
	
	public Stats createEntity(int level){
		
		Stats entityStat = new Stats();
		
		// set the non-variable attributes
		entityStat.setLevel(level);
		entityStat.setMovement(movement);
		
		// set the variable attributes
		entityStat.setHealth(choose(health));
		entityStat.setMana(choose(mana));
		entityStat.setMeleeAtk(choose(meleeAtk));
		entityStat.setRangeAtk(choose(rangeAtk));
		entityStat.setDefense(choose(defense));
		entityStat.setResistance(choose(resistance));
		entityStat.setAccuracy(choose(accuracy));
		entityStat.setAgility(choose(agility));
		
		// determine all the skills that should be available to the entity
		for(String i : skillTree.keySet()){
			ArrayList<String> skillList = null;
			if(skillTree.get(i)<=level){
				// add the skill to the entity's Skill array
				skillList.add(i);
			}
			// Set the entity's skill
			entityStat.setSkills(skillList);
		}
		
		return entityStat;
	}
	
// methods beyond this line are just to simplify the coding and math ----------------------------------
	
	private int choose(int[] twoNumbers){
	// this method returns a random integer whose value is between the given two integers (inclusive).
	// the two integers are to be passed to this method as an int array where the first element has a lower value.
		int a = twoNumbers[0];
		int b = twoNumbers[1];
		if(a==b){
			return a;
		}
		else if(a<b){
			return MathUtils.random(a, b);
		}
		else{
			return 0;
		}
	}
	
}

