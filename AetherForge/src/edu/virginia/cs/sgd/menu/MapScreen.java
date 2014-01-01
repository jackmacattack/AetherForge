package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import edu.virginia.cs.sgd.Entry;
import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.view.Viewer;
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.util.Point;


public class MapScreen extends AbstractScreen {

	private Viewer viewer;
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

		TiledMap map = Entry.getManager().get("data/map1.tmx", TiledMap.class);

		MapProperties prop = map.getProperties();
		int mapWidth = prop.get("tilewidth", Integer.class);
		int mapHeight = prop.get("tileheight", Integer.class);
		
		float scale = 1f;
		RenderSystem renderer = new RenderSystem(map, scale);
		this.viewer = new Viewer(mapWidth, mapHeight, renderer);

		level = new Level(map, renderer);
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

		viewer.renderUI(level);
	}

	@Override
	public void resize(int width, int height) {

		viewer.resize(width, height);
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
		
		Point coords = viewer.getCoord(screenX, screenY);

		if(button == Buttons.LEFT) {
//			level.select((int)coords.getX(), (int)coords.getY());
			level.onTouch(coords);
		}
	}

	@Override
	public void scrolled(int amount) {
		viewer.zoomMap(amount == 1);
	}
	
	@Override
	public void touchDragged(int screenX, int screenY, int pointer, int deltaX, int deltaY) {
		viewer.moveMap(deltaX, deltaY);
	}
}
