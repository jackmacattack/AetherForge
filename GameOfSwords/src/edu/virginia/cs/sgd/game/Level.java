package edu.virginia.cs.sgd.game;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.GameOfSwords;
import edu.virginia.cs.sgd.game.view.SpriteMaker;

public class Level {

	private TiledMap m_Map;

	private ArrayList<SpriteMaker> addList;
	private ArrayList<Integer> removeList;
	
	public Level() {

		m_Map = GameOfSwords.manager.get("data/sample_map.tmx");
		
		addList = new ArrayList<SpriteMaker>();
		removeList = new ArrayList<Integer>();
	}

	public TiledMap getMap() {
		// TODO Auto-generated method stub
		return m_Map;
	}

	public Point getPosition(int modelId) {
		// TODO Auto-generated method stub
		return new Point(0, 0);
	}
	
	public ArrayList<SpriteMaker> getAddList() {
		return addList;
	}
	
	public ArrayList<Integer> getRemoveList() {
		return removeList;
	}

	public void clearSpriteUpdates() {
		addList.clear();
		removeList.clear();
	}
}
