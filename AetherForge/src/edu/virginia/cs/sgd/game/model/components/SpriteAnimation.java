package edu.virginia.cs.sgd.game.model.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.virginia.cs.sgd.util.Animation;

public class SpriteAnimation extends Component {
	public Animation animation;
	public float stateTime;
	public float frameDuration;
	public int playType;

	public SpriteAnimation(float stateTime, float frameDuration, int playType) {
		this.stateTime = stateTime;
		this.frameDuration = frameDuration;
		this.playType = playType;
	}

	public TextureRegion getFrame() {
		return animation.getKeyFrame(stateTime, animation.isAnimated());
	}
}
