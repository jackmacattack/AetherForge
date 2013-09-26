package edu.virginia.cs.sgd.game;
import java.awt.Point;
import java.util.UUID;
import java.util.ArrayList;

/**
 * @author Mitchell
 *
 */

public class Entity {
	
	
	private static ArrayList<UUID> idList = new ArrayList<UUID>();

	//the position of the Entity
	private Point p;
	private UUID id;
	
	
	//the image that is displayed
	//sprite Sprite
	public static double distance(Entity a, Entity b)
	{
		double one = Math.pow((double)(a.p.x - b.p.x), 2);
		double two = Math.pow((double)(a.p.y - b.p.y), 2);
		return Math.sqrt(one+two);
	}
	
	public UUID getID()
	{
		return id;
	}
	public int getXpos() {
		return p.x;
	}
	public void setXpos(int xpos) {
		this.p.x = xpos;
	}
	public int getYpos() {
		return p.y;
	}
	public void setYpos(int ypos) {
		this.p.y = ypos;
	}
	public Point getPos()
	{
		return p;
	}
	public void setPos(Point p)
	{
		this.p = p;
	}

	public void setup()
	{
		id = UUID.randomUUID();
		idList.add(id);
	}
	public Entity()
	{
		p = new Point(0,0);
		setup();
	}
	public Entity(int x, int y)
	{
		p = new Point(x,y);
		setup();
	}	
	public Entity(Point po)
	{
		p = po;
		setup();
	}
}