package edu.virginia.cs.sgd.game.view;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.virginia.cs.sgd.GameOfSwords;
import edu.virginia.cs.sgd.game.Level;

public class LevelRenderer {

	private Level level;
	
	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;
	
	private SpriteManager manager;
	
	public LevelRenderer(Level level) {
		this.level = level;

		float scale = 1/32f;

		m_Renderer = new OrthogonalTiledMapRenderer(level.getMap(), scale);		
		m_Camera = new OrthographicCamera();
		 
		manager = new SpriteManager(m_Renderer.getSpriteBatch());
		
		TextureRegion tex = new TextureRegion(GameOfSwords.manager.get("data/samplesprite.png", Texture.class), 0, 0, 32, 32);
		
		manager.addSprite(new Sprite(tex, 0));
	}
	
	public void render() {

		m_Camera.update();
		m_Renderer.setView(m_Camera);
		m_Renderer.render();

		manager.draw(level);
	}

	public void resize(int width, int height) {

//		m_Camera.setToOrtho(true, width, height);

		m_Camera.setToOrtho(true, 15, 10);
		m_Camera.update();
		
		System.out.println("Ortho-ed");
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
		Sprite sprite = new Sprite(GameOfSwords.manager.get(img, TextureRegion.class), id);
		
		manager.addSprite(sprite);
	}
	
	public void removeSprite(int id) {
		manager.removeSprite(id);
	}
	
	public void onTouch(int x, int y) {
		
	}
}
