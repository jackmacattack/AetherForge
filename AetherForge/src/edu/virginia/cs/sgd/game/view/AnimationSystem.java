package edu.virginia.cs.sgd.game.view;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.virginia.cs.sgd.game.model.components.Sprite;
import edu.virginia.cs.sgd.game.model.components.SpriteAnimation;
import edu.virginia.cs.sgd.game.model.components.TextureName;

public class AnimationSystem extends EntityProcessingSystem{
	 @Mapper ComponentMapper<Sprite> sp;
	 @Mapper ComponentMapper<SpriteAnimation> sam;
	  
	 @SuppressWarnings("unchecked")
	 public AnimationSystem() {
	  super(Aspect.getAspectForAll(Sprite.class, SpriteAnimation.class));
	 }

	@Override
	protected void process(Entity e) {
		  Sprite sprite = sp.get(e);
		  SpriteAnimation anim = sam.get(e);
		  anim.stateTime += 1;
		  TextureRegion region = anim.getFrame();
		  sprite.x = region.getRegionX();
		  sprite.y = region.getRegionY();
		  sprite.width = region.getRegionWidth();
		  sprite.height = region.getRegionHeight();
		
	}
}
