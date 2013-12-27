package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import edu.virginia.cs.sgd.Entry;
import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.view.LevelRenderer;
import edu.virginia.cs.sgd.util.Point;


public class MapScreen extends AbstractScreen {

	private LevelRenderer renderer;
	private Level level;
	private Menu m;
	
	public MapScreen(Menu m, Entry game) {
		super();
		this.m = m;
		
		Entry.getManager().load("data/charactersheet.png", Texture.class);
		Entry.getManager().setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		Entry.getManager().load("data/map1.tmx", TiledMap.class);
		Texture.setEnforcePotImages(false);
		Entry.getManager().finishLoading();

		level = new Level(this);

		renderer = new LevelRenderer();
		renderer.setLevel(level);
		
		level.initialize();
	}
	
	public void gameOver(){
		m.getMenuscreen().dispose();
		m.getMusic().stop();
		Entry.getManager().load("data/GoS+Main+Theme.mp3", Music.class);
		Entry.getManager().finishLoading();
		Music music = Entry.getManager().get("data/GoS+Main+Theme.mp3", Music.class);	
		m.setMusic(music);
		m.getMusic().play();
		m.getMusic().setLooping(true);
		m.setScreen(new CreditsScreen(m));
		this.dispose();
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    Gdx.gl.glEnable(GL20.GL_BLEND);
	    
		level.update();

		renderer.renderUI();
	}

	@Override
	public void resize(int width, int height) {

		renderer.resize(width, height);
	}

	@Override
	public void show() {
		Texture.setEnforcePotImages(false);

//		renderer.show();
	}

	public void dispose() {
		level.dispose();
	}

	@Override
	public void keyDown(int keyCode) {
		
	}

	@Override
	public void keyUp(int keyCode) {
		
	}

	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
//		Point coords = renderer.getCoord(screenX, screenY);
		
		//System.out.println(coords.x + ", " + coords.y);
		
		//level.touchDown(coords, pointer, button);
	}

	@Override
	public void touchUp(int screenX, int screenY, int pointer, int button, boolean dragging) {
		
		if(dragging) {
			return;
		}
		
		Point coords = renderer.getCoord(screenX, screenY);
		
		if(button == Buttons.LEFT) {
			level.select((int)coords.getX(), (int)coords.getY());
		}
	}

	@Override
	public void scrolled(int amount) {
		renderer.zoomMap(amount == 1);
	}
	
	@Override
	public void touchDragged(int screenX, int screenY, int pointer, int deltaX, int deltaY) {
		renderer.moveMap(deltaX, deltaY);
	}
}
