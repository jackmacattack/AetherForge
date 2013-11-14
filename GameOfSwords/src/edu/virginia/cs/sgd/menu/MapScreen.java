package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import edu.virginia.cs.sgd.GameOfSwords;
import edu.virginia.cs.sgd.controller.Controller;
import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.view.LevelRenderer;


public class MapScreen extends AbstractScreen {

	private LevelRenderer renderer;
	private Level level;
	private Menu m;
	private Controller c;

	public MapScreen(Menu m, GameOfSwords game) {
		super();
		this.m = m;

		GameOfSwords.getManager().load("data/samplesprite.png", Texture.class);
		GameOfSwords.getManager().setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		GameOfSwords.getManager().load("data/sample_map.tmx", TiledMap.class);
		Texture.setEnforcePotImages(false);
		GameOfSwords.getManager().finishLoading();

		level = new Level();
		renderer = new LevelRenderer(level);

		c = new Controller(this, level);
		c.run();

	}


	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		renderer.render();

		level.update();
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

	public void dispose() {
		level.dispose();
	}
}
