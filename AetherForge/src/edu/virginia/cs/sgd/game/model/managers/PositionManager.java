package edu.virginia.cs.sgd.game.model.managers;

import com.artemis.Entity;
import com.artemis.Manager;

import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.util.Point;

public class PositionManager extends Manager {

	private boolean worldSet;
	private int[][] entityMap;
	
	public PositionManager() {
		worldSet = false;
	}

	@Override
	protected void initialize() {
		
	}
	
	public void setWorld(int width, int height) {
		
		entityMap = new int[width][height];
		
		for(int x = 0; x < entityMap.length; x++) {
			for(int y = 0; y < entityMap[x].length; y++) {
				entityMap[x][y] = -1;
			}
		}
		
		worldSet = true;
	}
	
	public void added(Entity e) {
		if(worldSet) {
			MapPosition m = e.getComponent(MapPosition.class);
			entityMap[m.getX()][m.getY()] = e.getId();
		}
		
	}
	
	public void changed(Entity e) {

		if(worldSet) {
			MapPosition m = e.getComponent(MapPosition.class);
			entityMap[m.getOldX()][m.getOldY()] = -1;
			entityMap[m.getX()][m.getY()] = e.getId();
		}
		
	}
	
	public void deleted(Entity e) {

		if(worldSet) {
			MapPosition m = e.getComponent(MapPosition.class);
			entityMap[m.getX()][m.getY()] = -1;
		}
		
	}
	
	public int getEntityAt(Point p) {
		
		int x = p.getX();
		int y = p.getY();
		
		if(x < 0 || y < 0) {
			return -1;
		}
		return entityMap[x][y];
	}
	
	public boolean spaceOpen(Point p) {
		return getEntityAt(p) != -1;
	}

}
