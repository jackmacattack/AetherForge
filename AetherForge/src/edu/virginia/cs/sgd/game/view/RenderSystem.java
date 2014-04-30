package edu.virginia.cs.sgd.game.view;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Sprite;
import edu.virginia.cs.sgd.game.model.components.SpriteAnimation;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.util.Animation;
import edu.virginia.cs.sgd.util.TextureRegionManager;

public class RenderSystem extends EntityProcessingSystem {

	private boolean isNew;
	
	@Mapper
	ComponentMapper<MapPosition> mapper;

	@Mapper
	ComponentMapper<TextureName> texMapper;
	@Mapper
	ComponentMapper<Sprite> sm;
	@Mapper
	ComponentMapper<SpriteAnimation> sam;
	private int width;
	private int height;
	private int countIf = 0;
	private int countProcess = 0;
	private int changeCount = 0;
	private String prevName = "";

	private OrthogonalTiledMapRenderer renderer;
	private TextureRegionManager texManager;
	// A test for animation
	private TextureRegionManager texAnimationManager;
	private TextureAtlas atlas;
	private String[] animationNames = { "idleman", "attackman", "walkmanS", "walkmanW", "walkmanE" };

	@SuppressWarnings("unchecked")
	public RenderSystem(TiledMap map, float scale) {

		super(Aspect.getAspectForAll(MapPosition.class, TextureName.class));

		setMap(map, scale);
	}
	
	public void setMap(TiledMap map, float scale) {
		this.renderer = new OrthogonalTiledMapRenderer(map, scale);

		width = map.getProperties().get("tilewidth", Integer.class);
		height = (map.getProperties().get("tileheight", Integer.class));
		texAnimationManager = new TextureRegionManager("HumanSprites", width,
				height, animationNames);
		
		isNew = true;
	}

	public float getScale() {
		return renderer.getUnitScale();
	}

	public void renderMap(OrthographicCamera camera) {

		renderer.setView(camera);

	}

	@Override
	protected void begin() {
		super.begin();

		SpriteBatch batch = getSpriteBatch();

		renderer.render();
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		MapPosition pos = mapper.get(e);
		TextureName name = texMapper.get(e);
		Sprite sprite = sm.get(e);
		SpriteAnimation anim = sam.get(e);
		anim.stateTime += 1;
			anim.animation.keyFrames = texAnimationManager.getRegion(sprite.name);
			prevName = sprite.name;
		SpriteBatch batch = getSpriteBatch();
		float scale = renderer.getUnitScale();
		// batch.draw(tex, (float) pos.getX() * width, (float) pos.getY() *
		// height,
		// 0, 0, width, height, scale, scale, 0);
		batch.draw(anim.getFrame(), (float) pos.getX() * width,
				(float) pos.getY() * height, 0, 0, width, height, scale, scale,
				0);

	}

	@Override
	protected void inserted(Entity e) {
		Sprite sprite = sm.get(e);
		SpriteAnimation anim = sam.get(e);
		Array<? extends TextureRegion> listAnimTex = new Array(
				texAnimationManager.getRegion(sprite.name));
		anim.animation = new Animation(anim.frameDuration, listAnimTex,
				anim.playType);
		TextureRegion reg = anim.getFrame();
		sprite.region = reg;
		sprite.x = reg.getRegionX();
		sprite.y = reg.getRegionY();
		sprite.width = reg.getRegionWidth();
		sprite.height = reg.getRegionHeight();
	}

	@Override
	protected void end() {
		super.end();

		SpriteBatch batch = getSpriteBatch();

		batch.end();
	}

	public SpriteBatch getSpriteBatch() {
		return renderer.getSpriteBatch();
	}

	public boolean isNew() {
		if(isNew) {
			isNew = false;
			
			return true;
		}
		
		return false;
	}

}
