package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.game.view.Viewer;
import edu.virginia.cs.sgd.util.Audio;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.SingletonAssetManager;


public class MapScreen extends AbstractScreen {

	private Viewer viewer;
	private Level level;
	
	//Setup map screen
	public MapScreen() {
		super();
		
		
		SingletonAssetManager.getInstance().finishLoading();

		TiledMap map = SingletonAssetManager.getInstance().get("Map 1");

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
		Audio.getInstance().stop();
		Audio.getInstance().play("Main Theme");
		changeScreen(CreditsScreen.class);
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    Gdx.gl.glEnable(GL20.GL_BLEND);
	    
		if(level.update()) {
			gameOver();
		}

		viewer.renderUI(level);
	}

	@Override
	public void resize(int width, int height) {

		viewer.resize(width, height);
	}

	@Override
	public void show() {
		Texture.setEnforcePotImages(false);
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
		
	}

	@Override
	public void touchUp(int screenX, int screenY, int pointer, int button, boolean dragging) {
		
		if(dragging) {
			return;
		}
		
		Point coords = viewer.getCoord(screenX, screenY);

		if(button == Buttons.LEFT) {
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
