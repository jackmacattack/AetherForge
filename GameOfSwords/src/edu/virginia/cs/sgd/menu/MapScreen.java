package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import edu.virginia.cs.sgd.GameOfSwords;
import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.view.LevelRenderer;


public class MapScreen extends AbstractScreen {

	private LevelRenderer renderer;
	
	public MapScreen(GameOfSwords game) {
		super(game);

		GameOfSwords.manager.load("data/samplesprite.png", Texture.class);
		GameOfSwords.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		GameOfSwords.manager.load("data/sample_map.tmx", TiledMap.class);
		Texture.setEnforcePotImages(false);
		GameOfSwords.manager.finishLoading();
		
		renderer = new LevelRenderer(new Level());
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

		renderer.show();
	}
	
}
