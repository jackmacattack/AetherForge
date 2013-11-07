package edu.virginia.cs.sgd.game;

import java.awt.Point;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.GameOfSwords;

public class Level {

	private TiledMap m_Map;
	
	public Level() {

		m_Map = GameOfSwords.manager.get("data/sample_map.tmx");
		
	}

	public TiledMap getMap() {
		// TODO Auto-generated method stub
		return m_Map;
	}

	public Point getPosition(int modelId) {
		// TODO Auto-generated method stub
		return new Point(0, 0);
	}
}
