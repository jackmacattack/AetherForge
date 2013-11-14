package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import edu.virginia.cs.sgd.GameOfSwords;



public class Menu {
	private MenuScreen menuscreen;
	private SplashScreen splashscreen;
	private MapScreen mapscreen;
	private GameOfSwords game;
	
	public MenuScreen getMenuscreen() {
		return menuscreen;
	}
	public void setMenuscreen(MenuScreen menuscreen) {
		this.menuscreen = menuscreen;
	}
	public SplashScreen getSplashscreen() {
		return splashscreen;
	}
	public void setSplashscreen(SplashScreen splashscreen) {
		this.splashscreen = splashscreen;
	}
	public MapScreen getMapscreen() {
		return mapscreen;
	}
	public void setMapscreen(MapScreen mapscreen) {
		this.mapscreen = mapscreen;
	}

	public Menu(GameOfSwords game){
		menuscreen = new MenuScreen(this);
		splashscreen = new SplashScreen(this);
		mapscreen = new MapScreen(this, game);
		this.game = game;
		game.manager.load("data/GoS+Main+Theme.mp3", Music.class);
		game.manager.finishLoading();
		Music theme = game.manager.get("data/GoS+Main+Theme.mp3", Music.class);	
		theme.play();
		theme.setLooping(true);
	}
	
	public void setScreen(Screen screen){
		game.setScreen(screen);
		
	}
}
