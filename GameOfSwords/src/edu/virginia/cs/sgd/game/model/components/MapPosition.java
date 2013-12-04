package edu.virginia.cs.sgd.game.model.components;

import com.artemis.Component;

public class MapPosition extends Component {
	private int x, y;
	
	public MapPosition(int x, int y)
	{
		this.x = x; 
		this.y = y;
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
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public String toString(){
	return "("+x+", "+y+")";
	}
	
	public static int calculateDistance(MapPosition a, MapPosition b) {
		return (int) Math.ceil((Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.x, 2))));
	}
	
	
}
