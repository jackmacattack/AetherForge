package edu.virginia.cs.sgd.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class SingletonAssetManager extends AssetManager {

	private SingletonAssetManager() {
		super();

		setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}
	
	private static SingletonAssetManager instance;
	public static SingletonAssetManager getInstance() {
		if(instance == null) {
			instance = new SingletonAssetManager();
		}
		
		return instance;
	}
}
