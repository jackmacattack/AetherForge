package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.virginia.cs.sgd.GameOfSwords;


public class MapScreen extends AbstractScreen {
	private Menu menu;

	public MapScreen(Menu m) {
		super();
		menu = m;
	}

	private TiledMap m_Map;
	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;
	

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		m_Camera.update();
		m_Renderer.setView(m_Camera);
		m_Renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		m_Camera.setToOrtho(true, width, height);

		m_Camera.update();
		System.out.println(width + "," + height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Texture.setEnforcePotImages(false);

		m_Map = new TmxMapLoader().load("data/sample_map.tmx");
		m_Renderer = new OrthogonalTiledMapRenderer(m_Map, 1);		
		m_Camera = new OrthographicCamera();
	}
}
