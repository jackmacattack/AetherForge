package edu.virginia.cs.sgd.game.model;

import com.artemis.World;
import com.artemis.Entity;
import edu.virginia.cs.sgd.game.model.Stats;
public class EntityFactory {

	
	public static Entity createActor(World world, int x, int y, int[][] map)
	{
		
		Entity e = world.createEntity();
		
		
		map[x][y] = e.getId();
		
		e.addComponent(new Stats());
		e.addComponent(new MapPosition(x,y));
		
		e.addToWorld();
		
		return e;
	}
	public static Entity createTile(World world, int x, int y, boolean pass, int [][]map)
	{
		
		
		Entity e = world.createEntity();
		
		
		map[x][y] = e.getId();
		
		e.addComponent(new Passable(pass));
		e.addComponent(new MapPosition(x,y));
		
		e.addToWorld();
		
		return e;
	}
	public static Entity createDestrutable(World world, int x, int y, int [][]map)
	{
		
		Entity e = world.createEntity();
		
		
		map[x][y] = e.getId();
		
		e.addComponent(new Passable(false));
		e.addComponent(new MapPosition(x,y));
		
		e.addToWorld();
		
		return e;
	}
}