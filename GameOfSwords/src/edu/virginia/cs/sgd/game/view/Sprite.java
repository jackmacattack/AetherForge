package edu.virginia.cs.sgd.game.view;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite {

	private TextureRegion image;
	private int modelId;
	
	public Sprite(TextureRegion image, int modelId) {
		super();
		this.image = image;
		this.modelId = modelId;
		
		this.image.flip(false, true);
	}
	
	public TextureRegion getImage() {
		return image;
	}
	
	public void setImage(TextureRegion image) {
		this.image = image;
		
		this.image.flip(false, true);
	}
	
	public int getModelId() {
		return modelId;
	}
	
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	
}