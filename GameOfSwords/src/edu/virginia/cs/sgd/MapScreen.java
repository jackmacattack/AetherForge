package edu.virginia.cs.sgd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;


public class MapScreen implements Screen {

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

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
