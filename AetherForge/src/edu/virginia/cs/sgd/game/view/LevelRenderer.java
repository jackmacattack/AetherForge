package edu.virginia.cs.sgd.game.view;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Mapper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.util.TextureRegionManager;


public class LevelRenderer {

	@Mapper
	ComponentMapper<MapPosition> mapper;
	
//	private Level level;

	private float zoomMin = .2f;
	private float zoomMax = 2f;
	private float zoomDelta = .2f;

	private int size;
	private float scale;
	
//	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;

	private RenderSystem renderer;
	
	public LevelRenderer() {

		size = 32;
		scale = 1f;
			
		m_Camera = new OrthographicCamera();
		
	}

	private void updateCamera() {
		m_Camera.update();
		renderer.renderMap(m_Camera);
	}
	
	public void renderUI() {

	}
	
	public void resize(int width, int height) {

		m_Camera.setToOrtho(false, width * scale, height * scale);

//		m_Camera.setToOrtho(true, 15, 10);
		
		updateCamera();
		
	}

	public MapPosition getCoord(int screenX, int screenY) {

		Vector3 pos = new Vector3(screenX, screenY, 0);
		m_Camera.unproject(pos);
		String str = (int)(pos.x * scale / size) + "," + (int)(pos.y * scale / size);
		System.out.println(str);
		return new MapPosition((int)(pos.x * scale / size), (int)(pos.y * scale / size));
	}

	public void zoomMap(boolean in) {
		float zoom = 0;
		
		if(in) {
			zoom = m_Camera.zoom * (1 + zoomDelta);
		}
		else {
			zoom = m_Camera.zoom * (1 - zoomDelta);
		}
		
		if(zoom > zoomMin && zoom < zoomMax) {
			m_Camera.zoom = zoom;
		}
		
		updateCamera();
		
	}

	public void moveMap(int deltaX, int deltaY) {
		
		Vector3 delta = new Vector3(-deltaX * m_Camera.zoom, deltaY * m_Camera.zoom, 0);
		
		//m_Camera.unproject(delta);
		
//		System.out.println(deltaX + "," + deltaY + "->" + delta.x + ", " + delta.y);
		
		m_Camera.translate(delta);
		
		updateCamera();
	}
	
	public void setLevel(Level level) {
		renderer = new RenderSystem(level.getMap(), size, scale);
		HighlightSystem highlighter = new HighlightSystem(size, renderer.getSpriteBatch());
		
		level.addSystem(renderer);
		level.addSystem(highlighter);
	}
	
}
