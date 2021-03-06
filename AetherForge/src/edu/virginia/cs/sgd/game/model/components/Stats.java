package edu.virginia.cs.sgd.game.model.components;
import java.util.ArrayList;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
public class Stats extends Component {

	private int maxHealth, health, maxMana, mana, meleeAtk, rangeAtk, defense, resistance, accuracy, agility, movement;
	private ArrayList<String> skills;
	public int experience, next, level, carrying_capacity;

	public Stats()
	{
		maxHealth = health = 30;
		maxMana = mana = 30;
		meleeAtk = 10;
		rangeAtk = 10;
		defense = 10;
		resistance = 10;
		accuracy = 10;
		agility = 10;
		movement = 5;
		skills = null;
		
		experience = 0;
		next = 40;
		level = 1;
		carrying_capacity = MathUtils.random(0,2);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getMeleeAtk() {
		return meleeAtk;
	}

	public void setMeleeAtk(int meleeAtk) {
		this.meleeAtk = meleeAtk;
	}

	public int getRangeAtk() {
		return rangeAtk;
	}

	public void setRangeAtk(int rangeAtk) {
		this.rangeAtk = rangeAtk;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getMovement() {
		return movement;
	}

	public void setMovement(int movement) {
		this.movement = movement;
	}

	public ArrayList<String> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<String> skills) {
		this.skills = skills;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCarrying_capacity() {
		return carrying_capacity;
	}

	public void setCarrying_capacity(int carrying_capacity) {
		this.carrying_capacity = carrying_capacity;
	}
	
	public float getPercentHealth() {
		return ((float)health)/maxHealth;
	}
	
	public float getPercentMana() {
		return ((float)mana)/maxMana;
	}
}
