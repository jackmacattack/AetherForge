package edu.virginia.cs.sgd.game.view;

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
	
	public void onTouch(int x, int y) {
		
	}
}