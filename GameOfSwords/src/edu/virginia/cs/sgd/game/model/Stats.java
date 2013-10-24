package edu.virginia.cs.sgd.game.model;
import com.badlogic.gdx.math.MathUtils;
public class Stats extends HP {

	private int resolve, defense, strength, intelligence, resistance, dexterity, accuracy;
	public int experience, next, level, carrying_capacity;
	public boolean hasTakenTurn = false;
	
	public Stats()
	{
		resolve = MathUtils.random(3,10);
		defense = MathUtils.random(3,10);
		strength = MathUtils.random(3,10);
		intelligence = MathUtils.random(3,10);
		resistance = MathUtils.random(3,10);
		accuracy = MathUtils.random(3,10);
		dexterity  = MathUtils.random(3,10);
		carrying_capacity = MathUtils.random(0,2);
		level = 1;
		experience = 0;
		next = 40;
	}

	public int getResolve() {
		return resolve;
	}

	public void setResolve(int resolve) {
		this.resolve = resolve;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
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
}
