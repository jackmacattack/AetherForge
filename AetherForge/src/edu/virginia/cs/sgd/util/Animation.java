package edu.virginia.cs.sgd.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Animation {

	public static final int NORMAL = 0;
	public static final int REVERSED = 1;
	public static final int LOOP = 2;
	public static final int LOOP_REVERSED = 3;
	public static final int LOOP_PINGPONG = 4;
	public static final int LOOP_RANDOM = 5;
    
	public TextureRegion[] keyFrames;

	public float frameDuration;
	public float animationDuration;

	private int playMode = NORMAL;
	private boolean animated = true;

	/**
	 * Constructor, storing the frame duration and key frames.
	 * 
	 * @param frameDuration
	 *            the time between frames in seconds.
	 * @param keyFrames
	 *            the {@link TextureRegion}s representing the frames.
	 */
	public Animation(float frameDuration, Array keyFrames) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.size * frameDuration;
		this.keyFrames = new TextureRegion[keyFrames.size];
		for (int i = 0, n = keyFrames.size; i < n; i++) {
			this.keyFrames[i] = (TextureRegion) keyFrames.get(i);
		}

		this.playMode = NORMAL;
	}

	/**
	 * Constructor, storing the frame duration, key frames and play type.
	 * 
	 * @param frameDuration
	 *            the time between frames in seconds.
	 * @param keyFrames
	 *            the {@link TextureRegion}s representing the frames.
	 * @param playType
	 *            the type of animation play (NORMAL, REVERSED, LOOP,
	 *            LOOP_REVERSED, LOOP_PINGPONG, LOOP_RANDOM)
	 */
	public Animation(float frameDuration, Array keyFrames, int playType) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.size * frameDuration;
		this.keyFrames = new TextureRegion[keyFrames.size];
		for (int i = 0, n = keyFrames.size; i < n; i++) {
			this.keyFrames[i] = (TextureRegion) keyFrames.get(i);
		}

		this.playMode = playType;
	}

	/**
	 * Constructor, storing the frame duration and key frames.
	 * 
	 * @param frameDuration
	 *            the time between frames in seconds.
	 * @param keyFrames
	 *            the {@link TextureRegion}s representing the frames.
	 */
	public Animation(float frameDuration, TextureRegion... keyFrames) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.length * frameDuration;
		this.keyFrames = keyFrames;
		this.playMode = NORMAL;
	}

	/**
	 * Returns a {@link TextureRegion} based on the so called state time. This
	 * is the amount of seconds an object has spent in the state this Animation
	 * instance represents, e.g. running, jumping and so on. The mode specifies
	 * whether the animation is looping or not.
	 * 
	 * @param stateTime
	 *            the time spent in the state represented by this animation.
	 * @param looping
	 *            whether the animation is looping or not.
	 * @return the TextureRegion representing the frame of animation for the
	 *         given state time.
	 */
//	public TextureRegion getKeyFrame(float stateTime, boolean looping) {
//		// we set the play mode by overriding the previous mode based on looping
//		// parameter value
//		if (looping && (playMode == NORMAL || playMode == REVERSED)) {
//			if (playMode == NORMAL)
//				playMode = LOOP;
//			else
//				playMode = LOOP_REVERSED;
//		} else if (!looping && !(playMode == NORMAL || playMode == REVERSED)) {
//			if (playMode == LOOP_REVERSED)
//				playMode = REVERSED;
//			else
//				playMode = LOOP;
//		}
//
//		return getKeyFrame(stateTime);
//	}
	/**
	 * Returns a {@link TextureRegion} based on the so called state time. This
	 * is the amount of seconds an object has spent in the state this Animation
	 * instance represents, e.g. running, jumping and so on using the mode
	 * specified by {@link #setPlayMode(int)} method.
	 * 
	 * @param stateTime
	 * @return the TextureRegion representing the frame of animation for the
	 *         given state time.
	 */
	public TextureRegion getKeyFrame(float stateTime, boolean animated) {
		if(animated){
		int frameNumber = getKeyFrameIndex(stateTime);
		return keyFrames[frameNumber];
		}
		return keyFrames[0];
	}

	/**
	 * Returns the current frame number.
	 * 
	 * @param stateTime
	 * @return current frame number
	 */
	public int getKeyFrameIndex(float stateTime) {
		int frameNumber = (int) (stateTime / frameDuration);

		if (keyFrames.length == 1)
			return 0;

		switch (playMode) {
		case NORMAL:
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
			break;
		case LOOP:
			frameNumber = frameNumber % keyFrames.length;
			break;
		case LOOP_PINGPONG:
			frameNumber = frameNumber % ((keyFrames.length * 2) - 2);
			// if (frameNumber >= keyFrames.length)
			frameNumber = keyFrames.length - 1
					- Math.abs(frameNumber - keyFrames.length + 1);// keyFrames.length
																	// - 2 -
																	// (frameNumber
																	// -
																	// keyFrames.length);
			break;
		case LOOP_RANDOM:
			frameNumber = MathUtils.random(keyFrames.length - 1);
			break;
		case REVERSED:
			frameNumber = Math.max(keyFrames.length - frameNumber - 1, 0);
			break;
		case LOOP_REVERSED:
			frameNumber = frameNumber % keyFrames.length;
			frameNumber = keyFrames.length - frameNumber - 1;
			break;

		default:
			// play normal otherwise
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
			break;
		}

		return frameNumber;
	}

	/**
	 * Sets the animation play mode.
	 * 
	 * @param playMode
	 *            can be one of the following: Animation.NORMAL,
	 *            Animation.REVERSED, Animation.LOOP, Animation.LOOP_REVERSED,
	 *            Animation.LOOP_PINGPONG, Animation.LOOP_RANDOM
	 */
	public void setPlayMode(int playMode) {
		this.playMode = playMode;
	}

	/**
	 * Whether the animation would be finished if played without looping
	 * (PlayMode Animation#NORMAL), given the state time.
	 * 
	 * @param stateTime
	 * @return whether the animation is finished.
	 */
	public boolean isAnimationFinished(float stateTime) {
		if (playMode != NORMAL && playMode != REVERSED)
			return false;
		int frameNumber = (int) (stateTime / frameDuration);
		return keyFrames.length - 1 < frameNumber;
	}
	
	public boolean isAnimated(){
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}
}
