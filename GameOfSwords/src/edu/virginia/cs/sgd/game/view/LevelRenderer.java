package edu.virginia.cs.sgd.game.view;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.virginia.cs.sgd.GameOfSwords;
import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.util.TextureRegionManager;

public class LevelRenderer {

	private Level level;
	
	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;
	
	private SpriteManager manager;
	private TextureRegionManager texManager;
	
	public LevelRenderer(Level level) {
		this.level = level;

		float scale = 1/32f;

		m_Renderer = new OrthogonalTiledMapRenderer(level.getMap(), scale);		
		m_Camera = new OrthographicCamera();
		 
		manager = new SpriteManager(m_Renderer.getSpriteBatch());
		
		texManager = new TextureRegionManager("data/samplesprite.png", 32, 32);
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

//		m_Camera.setToOrtho(true, width, height);

		m_Camera.setToOrtho(true, 15, 10);
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
	
	public void onTouch(int x, int y) {
		
	}
}
