package edu.virginia.cs.sgd.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.virginia.cs.sgd.game.Level;

public class LevelRenderer {

	private Level level;
	
	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;
	
	private SpriteManager manager;
	
	public LevelRenderer(Level level) {
		this.level = level;
		manager = new SpriteManager();
	}
	
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
		
		m_Renderer = new OrthogonalTiledMapRenderer(level.getMap(), 1);		
		m_Camera = new OrthographicCamera();
		
	}
	
	public void onTouch(int x, int y) {
		
	}
}
