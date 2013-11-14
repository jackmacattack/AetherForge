package edu.virginia.cs.sgd.controller;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.menu.MapScreen;

public class MovementSystem extends EntityProcessingSystem{
	@Mapper ComponentMapper<MapPosition> pos;
	
	public MapScreen map;
	public Level level;
	

	@SuppressWarnings("unchecked")
	public MovementSystem(MapScreen map, Level level) {
		super(Aspect.getAspectForAll(MapPosition.class));
		this.map = map;
		this.level = level;
	}

	@Override
	protected void process(Entity e) {
		MapPosition	mp = e.getComponent(MapPosition.class);
		int move = 3;
		int tileid = getCell(mp.getX(), mp.getY());
		System.out.println("I am at " + tileid);
		System.out.println(mp);
		System.out.println("Yay I can move");
	}

	private int getCell(int x, int y) {
		MapLayer layer = level.getMap().getLayers().get("Tile Layer 1");
		TiledMapTileLayer.Cell cell = ((TiledMapTileLayer)layer).getCell(x,y);
		System.out.println(cell.getTile().getId());
		return cell.getTile().getId();
	}

	//Path algorithm. Start (x,y) and End (x,y) coordinates with move distance
	//Basic tile path algorithm, by checking adjacent tiles.
	public int[][] createPath(int xs, int xe, int ys, int ye, int mv){
		//MapLayer layer = level.getMap().getLayers().get("Tile Layer 1");
		int[][] path = new int[mv][2];
		for(int i = 0; i < mv; i++){
			for(int j = 0; j < path[i].length; j++){
				
			}
		}
		
		return path;
		
		
	}

}
