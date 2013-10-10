package edu.cs.virginia.sgd.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class LevelRenderer {

	private TiledMap m_Map;
	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;
	
	public void render() {

		m_Camera.update();
		m_Renderer.setView(m_Camera);
		m_Renderer.render();
		
	}

	public void resize(int width, int height) {

		m_Camera.setToOrtho(true, width, height);

		m_Camera.update();
		
	}

	public void show() {
		
		m_Map = new TmxMapLoader().load("data/sample_map.tmx");
		m_Renderer = new OrthogonalTiledMapRenderer(m_Map, 1);		
		m_Camera = new OrthographicCamera();
		
	}
	
	public void onTouch(int x, int y) {
		
	}
}
