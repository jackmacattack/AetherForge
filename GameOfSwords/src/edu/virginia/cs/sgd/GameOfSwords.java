package edu.virginia.cs.sgd;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.virginia.cs.sgd.menu.Menu;
import edu.virginia.cs.sgd.menu.SplashScreen;



public class GameOfSwords extends Game implements ApplicationListener {
	
	public Menu menu;
	public static AssetManager manager = new AssetManager();

	public static final String LOG = GameOfSwords.class.getSimpleName();

	public GameOfSwords() {

	}

	@Override
	public void create() {
		menu = new Menu(this);

	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (getScreen() == null) {
			setScreen(menu.getSplashscreen());

		}
	}

	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
