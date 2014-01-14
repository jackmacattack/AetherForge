package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import edu.virginia.cs.sgd.util.SingletonAssetManager;


public class CreditsScreen extends AbstractScreen {
	private Image splashImage;

	public CreditsScreen() {
		super();
	}

	@Override
	public void show() {

		super.show();
		
		SingletonAssetManager.getInstance().finishLoading();

		// load the splash image and create the texture region
		Texture splashTexture = SingletonAssetManager.getInstance().get("Credits");
		TextureRegion tr = new TextureRegion(splashTexture);
		Drawable splashDrawable = new TextureRegionDrawable(tr);

		splashImage = new Image(splashDrawable, Scaling.stretch);
		splashImage.setFillParent(true);

		stage.addActor(splashImage);

	}

	@Override
	public void touchUp(int screenX, int screenY, int pointer, int button, boolean dragging) {
		changeScreen(MenuScreen.class);
	}
}
