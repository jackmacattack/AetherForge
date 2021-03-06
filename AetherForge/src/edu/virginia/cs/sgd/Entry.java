package edu.virginia.cs.sgd;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import edu.virginia.cs.sgd.input.Input;
import edu.virginia.cs.sgd.menu.AbstractScreen;
import edu.virginia.cs.sgd.menu.CreditsScreen;
import edu.virginia.cs.sgd.menu.MapScreen;
import edu.virginia.cs.sgd.menu.MenuScreen;
import edu.virginia.cs.sgd.menu.SplashScreen;
import edu.virginia.cs.sgd.util.SingletonAssetManager;


public class Entry extends Game implements ApplicationListener {
	
	public static final String LOG = Entry.class.getName(); //GameOfSwords.class.getSimpleName();

	private Input input;
	private AbstractScreen screen;
	
	@Override
	public void create() {
		input = new Input();
		Texture.setEnforcePotImages(false);
		
		loadImmediateAssets();
		loadAssets();
		createScreen(SplashScreen.class);
//		createScreen(MapScreen.class);
	}

	@Override
	public void render() {
		Class<? extends AbstractScreen> newScreen = screen.checkScreenChange();
		if(newScreen != null) {
			createScreen(newScreen);
		}
		super.render();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	private void createScreen(Class<? extends AbstractScreen> type) {
//		screen = null;
//		try {
//			screen = type.newInstance();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//			return;
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//			return;
//		}
		
		if(type.equals(SplashScreen.class)) {
			screen = new SplashScreen();
		}
		else if(type.equals(MenuScreen.class)) {
			screen = new MenuScreen();
		}
		else if(type.equals(MapScreen.class)) {
			screen = new MapScreen();
		}
		else if(type.equals(CreditsScreen.class)) {
			screen = new CreditsScreen();
		}
		
		input.setListener(screen);
		setScreen(screen);
	}
	
	private void loadImmediateAssets() {

		SingletonAssetManager m = SingletonAssetManager.getInstance();
		m.load("UISkin", "skins/uiskin.json", Skin.class);
		m.load("Logo", "screens/logo.png", Texture.class);
		m.load("Main Theme", "music/Main Theme.mp3", Music.class);
		m.finishLoading();
	}
	
	private void loadAssets() {
		SingletonAssetManager m = SingletonAssetManager.getInstance();
		m.load("Characters", "sprites/charactersheet.png", Texture.class);
		m.load("Credits", "screens/Credits.png", Texture.class);
		m.load("Intro", "maps/IntroMap.tmx", TiledMap.class);
		m.load("Forest", "maps/ForestMap.tmx", TiledMap.class);
		m.load("Battle Theme", "music/Battle Theme.mp3", Music.class);
		m.load("HumanSprites", "sprites/8x8sprites.gif", Texture.class);
		m.load("FullSpriteSheet", "sprites/fullspritesheet.gif", Texture.class);
	}
}
