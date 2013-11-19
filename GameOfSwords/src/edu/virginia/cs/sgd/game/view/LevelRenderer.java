package edu.virginia.cs.sgd.game.view;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.util.TextureRegionManager;


public class LevelRenderer {

	private Level level;

	private float zoomMin = .2f;
	private float zoomMax = 2f;
	private float zoomDelta = .2f;

	private int size;
	private float scale;
	
	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;
	
	private SpriteManager manager;

	private TextureRegionManager texManager;
	
	public LevelRenderer(Level level) {
		this.level = level;

		size = 32;
		scale = 1f;

		m_Renderer = new OrthogonalTiledMapRenderer(level.getMap(), scale);		
		m_Camera = new OrthographicCamera();
		
		manager = new SpriteManager(size, scale, m_Renderer.getSpriteBatch());
		
		texManager = new TextureRegionManager("data/samplesprite.png", size, size);
		texManager.addRegion("sample", new Point(0,0));
	}
	
	public void render() {

		m_Camera.update();
		m_Renderer.setView(m_Camera);
		m_Renderer.render();

		updateSprites();

		manager.draw(level);
	}

	public void resize(int width, int height) {

		m_Camera.setToOrtho(true, width * scale, height * scale);

//		m_Camera.setToOrtho(true, 15, 10);
		
		m_Camera.update();
		
	}

	public void show() {
		
	}
	
	public void updateSprites() {
		
		ArrayList<Integer> removeList = level.getRemoveList();
		
		for(Integer remove : removeList) {
			removeSprite(remove);
		}
		
		ArrayList<SpriteMaker> addList = level.getAddList();
		
		for(SpriteMaker add : addList) {
			addSprite(add.getId(), add.getImgSource());
		}
		
		level.clearSpriteUpdates();
	}
	
	public void addSprite(int id, String img) {
		Sprite sprite = new Sprite(texManager.getRegion(img), id);
		
		manager.addSprite(sprite);
	}
	
	public void removeSprite(int id) {
		manager.removeSprite(id);
	}

	public Point getCoord(int screenX, int screenY) {

		Vector3 pos = new Vector3(screenX, screenY, 0);
		m_Camera.unproject(pos);
		
		return new Point((int)(pos.x * scale / size), (int)(pos.y * scale / size));
	}

	public void zoomMap(boolean in) {
		// TODO Auto-generated method stub
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
		
		Vector3 delta = new Vector3(-deltaX * m_Camera.zoom, -deltaY * m_Camera.zoom, 0);
		
		//m_Camera.unproject(delta);
		
//		System.out.println(deltaX + "," + deltaY + "->" + delta.x + ", " + delta.y);
		
		m_Camera.translate(delta);
		
	}
}
