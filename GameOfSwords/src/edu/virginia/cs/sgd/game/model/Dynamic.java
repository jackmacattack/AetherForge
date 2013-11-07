package edu.virginia.cs.sgd.game.model;
import java.awt.Point;
public class Dynamic extends Entity_old {
	
	private int health;
	private int max_health;
	
	boolean dead = false;
	public void update()
	{
		if (health < 0)
			dead = true;
	}
	public int getHealth()
	{
		return health;
	}
	public int getMaxHealth()
	{
		return max_health;
	}
	public void setHealth(int h)
	{
		health = h;
	}
	public void setMaxHealth(int h)
	{
		max_health = h;
	}
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
	
	public Dynamic(Point p, int h)
	{
		super(p);
		health = h;
		max_health=health;
	}
	public Dynamic(Point p)
	{
		super(p);
		health = 100;
		max_health=health;
	}

}
