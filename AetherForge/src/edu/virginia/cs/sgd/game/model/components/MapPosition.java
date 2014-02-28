package edu.virginia.cs.sgd.game.model.components;

import com.artemis.Component;

import edu.virginia.cs.sgd.util.Point;

public class MapPosition extends Component {
	private int oldX, oldY;
	private int x, y;
	
	public MapPosition(int x, int y)
	{
		this.oldX = x;
		this.oldY = y;
		this.x = x; 
		this.y = y;
	}
	
	public MapPosition(Point p) {
		this(p.getX(), p.getY());
	}

	public int getOldX(){
		return oldX;
	}
	
	public int getOldY(){
		return oldY;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public MapPosition getPos(){
		return new MapPosition(this.x, this.y);
	}
	public void setX(int x){
		this.oldX = this.x;
		this.x = x;
	}
	public void setY(int y){
		this.oldY = this.y;
		this.y = y;
	}
	
	public String toString(){
		return "("+x+", "+y+")";
	}
	
	public static int calculateDistance(MapPosition a, MapPosition b) {
		return (int) Math.ceil((Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.x, 2))));
	}
	
	public boolean equals(Object o) {
		if(o instanceof MapPosition) {
			MapPosition m = (MapPosition) o;
			return x == m.getX() && y == m.getY();
		}
		
		return false;
	}

	public Point getPoint() {
		return new Point(x, y);
	}
}
