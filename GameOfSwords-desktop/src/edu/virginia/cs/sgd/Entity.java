package edu.virginia.cs.sgd;
import java.awt.Point;
/**
 * @author Mitchell
 *
 */

public class Entity {

	//the position of the Entity
	Point p;
	
	
	//probably not accessed, just set to some standard width and height of the grid
	int width;
	int height;
	
	//the image that is displayed
	//sprite Sprite
	public static double distance(Entity a, Entity b)
	{
		double one = Math.pow((double)(a.p.x - b.p.x), 2);
		double two = Math.pow((double)(a.p.y - b.p.y), 2);
		return Math.sqrt(one+two);
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
	public Point getCenter()
	{
		Point t = new Point(p);
		t.translate(width/2, height/2);
		return t;
	}
	public Entity()
	{
		p = new Point(0,0);
	}
	public Entity(int x, int y)
	{
		p = new Point(x,y);
	}	
}