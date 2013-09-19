package edu.virginia.cs.sgd;

/**
 * @author Mitchell
 *
 */

public class Entity {

	//the x position of an Entity
	int xpos;
	//the y position of an Entity
	int ypos;
	
	
	//probably not accessed, just set to some standard width and height of the grid
	int width;
	int height;
	
	//the image that is displayed
	//sprite Sprite
	
	public int getXpos() {
		return xpos;
	}
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}
	public int getYpos() {
		return ypos;
	}
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}


	public Entity()
	{
		xpos = 0;
		ypos = 0;
	}
	public Entity(int x, int y)
	{
		xpos = x;
		ypos = y;
	}
	
	
}
