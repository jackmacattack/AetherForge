package edu.virginia.cs.sgd.game;

public class Dynamic extends Entity {
	
	int health;
	
	boolean dead = false;
	
	public boolean kill()
	{
		if (dead)
			return false;
		dead = true;
		return true;
	}
	public boolean resurrect()
	{
		if(!dead)
			return false;
		dead = false;
		return true;	
	}
	
	public boolean isDead()
	{
		return dead;
	}
	

}
