package edu.virginia.cs.sgd.game.view;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.util.TextureRegionManager;


public class RenderSystem extends EntityProcessingSystem {

	@Mapper
	ComponentMapper<MapPosition> mapper;

	@Mapper
	ComponentMapper<TextureName> texMapper;

	private int size;
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private TextureRegionManager texManager;
	
	@SuppressWarnings("unchecked")
	public RenderSystem(int size, OrthogonalTiledMapRenderer renderer, 
			OrthographicCamera camera, TextureRegionManager texManager) {
		super(Aspect.getAspectForAll(MapPosition.class, TextureName.class));

		this.size = size;
		
		this.renderer = renderer;		
		this.camera = camera;
//		manager = new SpriteManager(size, scale, renderer.getSpriteBatch());
		
		this.texManager = texManager;
	}
	
	public void renderMap() {
	    
		camera.update();
		renderer.setView(camera);
		renderer.render();
	}
	
	@Override
	protected void begin() {
		super.begin();
		
		SpriteBatch batch = renderer.getSpriteBatch();
		renderMap();
		
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		MapPosition pos = mapper.get(e);
		TextureName name = texMapper.get(e);
		TextureRegion tex = texManager.getRegion(name.getName());
		SpriteBatch batch = renderer.getSpriteBatch();
		float scale = renderer.getUnitScale();
		
		batch.draw(tex, (float) pos.getX() * size, (float) pos.getY() * size, 
				0, 0, size, size, scale, scale, 0);
	}
	
	@Override
	protected void end() {
		super.end();
		
		SpriteBatch batch = renderer.getSpriteBatch();
		
		batch.end();
	}
	
}
