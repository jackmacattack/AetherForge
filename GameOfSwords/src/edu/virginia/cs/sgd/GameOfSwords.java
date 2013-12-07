package edu.virginia.cs.sgd;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import edu.virginia.cs.sgd.menu.Menu;


public class GameOfSwords extends Game implements ApplicationListener {
	
	public Menu menu;

	private static AssetManager manager = new AssetManager();

	public static AssetManager getManager() {
		
		if(manager == null) {
			manager = new AssetManager();
		}
		
		return manager;
	}
	
	public static final String LOG = GameOfSwords.class.getName(); //GameOfSwords.class.getSimpleName();
	
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
			menu.setScreen(menu.getSplashscreen());

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
