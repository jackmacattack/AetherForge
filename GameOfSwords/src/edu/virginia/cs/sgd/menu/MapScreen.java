package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.cs.virginia.sgd.game.view.LevelRenderer;
import edu.virginia.cs.sgd.GameOfSwords;


public class MapScreen extends AbstractScreen {

	private LevelRenderer renderer;
	
	public MapScreen(GameOfSwords game) {
		super(game);
		
		renderer = new LevelRenderer();
	}
	

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
		renderer.resize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Texture.setEnforcePotImages(false);

		renderer.show();
	}
	
}
