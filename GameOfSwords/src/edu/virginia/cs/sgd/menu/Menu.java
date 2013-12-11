package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.audio.Music;

import edu.virginia.cs.sgd.Entry;
import edu.virginia.cs.sgd.input.Input;



public class Menu {
	private MenuScreen menuscreen;
	private SplashScreen splashscreen;
	private MapScreen mapscreen;
	private CreditsScreen creditsscreen;
	private Entry game;
	
	private Music music;
	
	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	private Input input;
	
	public Menu() {
		input = new Input();
	}
	
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
	public CreditsScreen getCreditsscreen() {
		return creditsscreen;
	}

	public Menu(Entry game){
		this();
		
		menuscreen = new MenuScreen(this);
		splashscreen = new SplashScreen(this);
		creditsscreen = new CreditsScreen(this);
		mapscreen = new MapScreen(this, game);
		this.game = game;
		Entry.getManager().load("data/GoS+Main+Theme.mp3", Music.class);
		Entry.getManager().finishLoading();
		music = Entry.getManager().get("data/GoS+Main+Theme.mp3", Music.class);	
		music.play();
		music.setLooping(true);
	}
	
	public void setScreen(AbstractScreen screen){
		
		input.setListener(screen);
		game.setScreen(screen);
		
	}
	
	public void dispose(AbstractScreen screen){
		screen.dispose();
	}
}
