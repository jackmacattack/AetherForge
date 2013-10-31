package edu.virginia.cs.sgd.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Level {

	private TiledMap m_Map;
	
	public Level() {

		m_Map = new TmxMapLoader().load("data/sample_map.tmx");
		
	}

	public TiledMap getMap() {
		// TODO Auto-generated method stub
		return m_Map;
	}
}
