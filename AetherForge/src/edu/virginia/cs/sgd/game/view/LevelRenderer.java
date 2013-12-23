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
	
	private Level level;

	private float zoomMin = .2f;
	private float zoomMax = 2f;
	private float zoomDelta = .2f;

	private int size;
	private float scale;
	
	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;

	private TextureRegionManager texManager;
	
	public LevelRenderer(Level level) {
		this.level = level;

		size = 32;
		scale = 1f;
		
		m_Renderer = new OrthogonalTiledMapRenderer(level.getMap(), scale);		
		m_Camera = new OrthographicCamera();
		
		texManager = new TextureRegionManager("data/charactersheet.png", size, size);
		texManager.addRegion("swordsman", texManager.getTr()[0][0]);
		texManager.addRegion("spearman", texManager.getTr()[0][1]);
		texManager.addRegion("gunner", texManager.getTr()[0][2]);
		texManager.addRegion("cleric", texManager.getTr()[0][3]);
		texManager.addRegion("archer", texManager.getTr()[0][4]);
		texManager.addRegion("berserker", texManager.getTr()[0][5]);
		texManager.addRegion("sorc", texManager.getTr()[0][6]);
		texManager.addRegion("sample", texManager.getTr()[0][7]);
	}
	
	public void renderUI() {
	    
//		m_Camera.update();
//		m_Renderer.setView(m_Camera);
		//m_Camera.setToOrtho(true, m_Camera.viewportWidth, m_Camera.viewportHeight);
//		m_Camera.direction.y *= -1;
//		m_Renderer.render();
//		m_Camera.direction.y *= -1;
		//m_Camera.setToOrtho(false, m_Camera.viewportWidth, m_Camera.viewportHeight);

	}
	
	public void resize(int width, int height) {

		m_Camera.setToOrtho(false, width * scale, height * scale);

//		m_Camera.setToOrtho(true, 15, 10);
		
		m_Camera.update();
		
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
		
	}

	public void moveMap(int deltaX, int deltaY) {
		
		Vector3 delta = new Vector3(-deltaX * m_Camera.zoom, deltaY * m_Camera.zoom, 0);
		
		//m_Camera.unproject(delta);
		
//		System.out.println(deltaX + "," + deltaY + "->" + delta.x + ", " + delta.y);
		
		m_Camera.translate(delta);
		
	}
	
	public RenderSystem getRenderSystem() {
		RenderSystem sys = new RenderSystem(size, m_Renderer, m_Camera, texManager);
		return sys;
	}
	
	public HighlightSystem getHighlightSystem() {
		HighlightSystem sys = new HighlightSystem(size, m_Renderer.getSpriteBatch());
		return sys;
	}
}
