package edu.virginia.cs.sgd.game.view;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Mapper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.model.components.MapPosition;


public class LevelRenderer {

	@Mapper
	ComponentMapper<MapPosition> mapper;

	private float zoomMin;
	private float zoomMax;
	private float zoomDelta;

	private int width;
	private int height;
	
	private float scale;
	
	private OrthographicCamera camera;

	private RenderSystem renderer;
	
	public LevelRenderer() {
		
		zoomMin = .2f;
		zoomMax = 2f;
		zoomDelta = .2f;
		
		scale = 1f;
			
		camera = new OrthographicCamera();
		
	}

	private void updateCamera() {
		camera.update();
		renderer.renderMap(camera);
	}
	
	public void renderUI() {

	}
	
	public void resize(int width, int height) {

		camera.setToOrtho(false, width * scale, height * scale);
		
		updateCamera();
		
	}

	public MapPosition getCoord(int screenX, int screenY) {

		Vector3 pos = new Vector3(screenX, screenY, 0);
		camera.unproject(pos);
		
		int x = (int)(pos.x * scale / width);
		int y = (int)(pos.y * scale / height);
		
		return new MapPosition(x, y);
	}

	public void zoomMap(boolean in) {
		float zoom = 0;
		
		if(in) {
			zoom = camera.zoom * (1 + zoomDelta);
		}
		else {
			zoom = camera.zoom * (1 - zoomDelta);
		}
		
		if(zoom > zoomMin && zoom < zoomMax) {
			camera.zoom = zoom;
		}
		
		updateCamera();
		
	}

	public void moveMap(int deltaX, int deltaY) {
		
		Vector3 delta = new Vector3(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
		
		camera.translate(delta);
		
		updateCamera();
	}
	
	public void setLevel(Level level) {
		TiledMap map = level.getMap();

		width = map.getProperties().get("tilewidth", Integer.class);
		height = map.getProperties().get("tileheight", Integer.class);
		
		renderer = new RenderSystem(map, scale);
		HighlightSystem highlighter = new HighlightSystem(width, height, renderer.getSpriteBatch());
		
		level.addSystem(renderer);
		level.addSystem(highlighter);
	}
	
}
