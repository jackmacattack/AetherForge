package edu.virginia.cs.sgd.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Music;


public class Audio {

	private Audio() {
		library = new HashMap<String, String>();
	}
	
	private static Audio instance;
	
	public static Audio getInstance() {
		if(instance == null) {
			instance = new Audio();
		}
		return instance;
	}
	
	private Map<String, String> library;
	private Music currentSong;
	
	public void loadMusic(String name, String fileName) {
		SingletonAssetManager.getInstance().load("data/GOS+Main+Theme.mp3", Music.class);
		library.put(name, fileName);
	}
	
	public void play(String name) {
		currentSong = SingletonAssetManager.getInstance().get(library.get(name), Music.class);
		currentSong.play();
		currentSong.setLooping(true);
	}
	
	public void stop() {
		currentSong.stop();
	}
	
}
