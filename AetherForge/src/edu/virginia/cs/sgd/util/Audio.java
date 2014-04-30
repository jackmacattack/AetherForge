package edu.virginia.cs.sgd.util;

import com.badlogic.gdx.audio.Music;


public class Audio {

	private Audio() {
	}
	
	private static Audio instance;
	
	public static Audio getInstance() {
		if(instance == null) {
			instance = new Audio();
		}
		return instance;
	}
	
	private Music currentSong;
	
	public void play(String name) {
		stop();
		currentSong = SingletonAssetManager.getInstance().get(name);
		currentSong.play();
		currentSong.setLooping(true);
	}
	
	public void stop() {
		if(currentSong != null) {
			currentSong.stop();
		}
	}
	
}
