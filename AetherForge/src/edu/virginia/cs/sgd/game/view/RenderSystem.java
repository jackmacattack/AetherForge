package edu.virginia.cs.sgd.game.view;

import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.util.TextureRegionManager;
import edu.virginia.cs.sgd.util.Triple;


public class RenderSystem extends EntityProcessingSystem {

	@Mapper
	ComponentMapper<MapPosition> mapper;

	@Mapper
	ComponentMapper<TextureName> texMapper;

	private int size;
	private float scale;
	
	private OrthogonalTiledMapRenderer m_Renderer;
	private OrthographicCamera m_Camera;
	
	private TextureRegionManager texManager;
	
	@SuppressWarnings("unchecked")
	public RenderSystem(Level level) {
		super(Aspect.getAspectForAll(MapPosition.class, TextureName.class));

		size = 32;
		scale = 1f;
		
		m_Renderer = new OrthogonalTiledMapRenderer(level.getMap(), scale);		
		m_Camera = new OrthographicCamera();
//		manager = new SpriteManager(size, scale, m_Renderer.getSpriteBatch());
		
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
		
//		updateSprites();
//
//		manager.draw(level);
//		highlight(level.getPathList(), new Color(0,0,1,.5f), m_Renderer.getSpriteBatch());
//		highlight(level.getAttackList(), new Color(1,0,0,.5f), m_Renderer.getSpriteBatch());
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
	
	@Override
	protected void begin() {
		super.begin();
		
		SpriteBatch batch = m_Renderer.getSpriteBatch();
		
		
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		MapPosition pos = mapper.get(e);
		TextureName name = texMapper.get(e);
		TextureRegion tex = texManager.getRegion(name.getName());
		SpriteBatch batch = m_Renderer.getSpriteBatch();
		
		batch.draw(tex, (float) pos.getX() * size, (float) (pos.getY() + 1) * size, 
				0, 0, size, size, scale, -scale, 0);
	}
	
	@Override
	protected void end() {
		super.end();
		
		SpriteBatch batch = m_Renderer.getSpriteBatch();
		
		batch.end();
	}
	
}
