package edu.virginia.cs.sgd.game.model;

import com.artemis.Entity;
import com.artemis.Manager;

import edu.virginia.cs.sgd.game.model.components.MapPosition;

public class PositionManager extends Manager {

	private boolean worldSet;
	private int[][] entityMap;
	
	public PositionManager() {
		worldSet = false;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		//worldSet = false;
	}

	public void setWorld(int width, int height) {
		
		entityMap = new int[width][height];
		
		for(int x = 0; x < entityMap.length; x++) {
			for(int y = 0; y < entityMap[x].length; y++) {
				entityMap[x][y] = -1;
			}
		}
		
		worldSet = true;
		System.out.println("World is " + worldSet);
	}
	
	public void added(Entity e) {
		System.out.println("World is " + worldSet);
		if(worldSet) {
			MapPosition m = e.getComponent(MapPosition.class);
			entityMap[m.getX()][m.getY()] = e.getId();

			System.out.println("Added to position");
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
	
	public int getEntityAt(int x, int y) {
		if(x < 0 || y < 0) {
			return -1;
		}
		return entityMap[x][y];
	}
	
	public boolean spaceOpen(int x, int y) {
		return getEntityAt(x, y) != -1;
	}
}
