package edu.virginia.cs.sgd.game.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.util.TextureRegionManager;
import edu.virginia.cs.sgd.util.Triple;


public class LevelRenderer {

	private Level level;

	private float zoomMin = .2f;
	private float zoomMax = 2f;
	private float zoomDelta = .2f;
	
	private TextureRegion[][] tr;

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
		
		texManager = new TextureRegionManager("data/charactersheet.png", size, size);
		texManager.addRegion("swordsman", texManager.getTr()[0][0]);
		texManager.addRegion("spearman", texManager.getTr()[0][1]);
		texManager.addRegion("gunner", texManager.getTr()[0][2]);
		texManager.addRegion("cleric", texManager.getTr()[0][3]);
		texManager.addRegion("archer", texManager.getTr()[0][4]);
		texManager.addRegion("berserker", texManager.getTr()[0][5]);
		texManager.addRegion("sorc", texManager.getTr()[0][6]);
		texManager.addRegion("sample", texManager.getTr()[0][7]);
//		texManager.addRegion("swordsman", new Vector2(0,0));
//		texManager.addRegion("spearman", new Vector2(32,0));
//		texManager.addRegion("gunner", new Vector2(64,0));
//		texManager.addRegion("cleric", new Vector2(96,0));
//		texManager.addRegion("archer", new Vector2(128,0));
//		texManager.addRegion("berserker", new Vector2(160,0));
//		texManager.addRegion("sorc", new Vector2(192,0));
//		texManager.addRegion("sample", new Vector2(224,0));
	}
	
	public void render() {
	    Gdx.gl.glClearColor(0, 0, 0, 0); 
	    Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT  );
	    Gdx.gl.glEnable(GL20.GL_BLEND);
	    
		m_Camera.update();
		m_Renderer.setView(m_Camera);
		//m_Camera.setToOrtho(true, m_Camera.viewportWidth, m_Camera.viewportHeight);
//		m_Camera.direction.y *= -1;
		m_Renderer.render();
//		m_Camera.direction.y *= -1;
		//m_Camera.setToOrtho(false, m_Camera.viewportWidth, m_Camera.viewportHeight);
		
		updateSprites();

		manager.draw(level);
		highlight(level.getPathList(), new Color(0,0,1,.5f), m_Renderer.getSpriteBatch());
		highlight(level.getAttackList(), new Color(1,0,0,.5f), m_Renderer.getSpriteBatch());
	}

	public void highlight(List<Triple> tiles, Color c,  SpriteBatch batch) {
		for(Triple t : tiles) {
//			ShapeRenderer s = new ShapeRenderer();
//			s.begin(ShapeType.Filled);
//			
//			s.setColor(0, 0, .5f, .5f);
//			s.rect((float) t.getX() * size, (float) (level.getMapHeight() - t.getY()) * size, size, size);
//			s.end();

			Pixmap p = new Pixmap(32,32,Pixmap.Format.RGBA8888);
			p.setColor(c);
			p.fill();
			p.setColor(0,0,0,.5f);
			p.drawRectangle(0, 0, 32, 32);
			
			Texture tex = new Texture(p);
			
			batch.begin();
			batch.draw(tex,(float) t.getX() * size, (float) t.getY() * size);
			batch.end();
		}
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

	public Vector2 getCoord(int screenX, int screenY) {

		Vector3 pos = new Vector3(screenX, screenY, 0);
		m_Camera.unproject(pos);
		
		return new Vector2((int)(pos.x * scale / size), (int)(pos.y * scale / size));
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
