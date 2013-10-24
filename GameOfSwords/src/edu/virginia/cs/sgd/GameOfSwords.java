package edu.virginia.cs.sgd;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.virginia.cs.sgd.menu.MapScreen;
import edu.virginia.cs.sgd.menu.MenuScreen;
import edu.virginia.cs.sgd.menu.SplashScreen;
import edu.virginia.cs.sgd.game.model.*;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;


public class GameOfSwords extends Game implements ApplicationListener {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	public World world;
	public static AssetManager manager = new AssetManager();

	public static final String LOG = GameOfSwords.class.getSimpleName();

	public GameOfSwords() {

	}
	
	public SplashScreen getSplashScreen()
    {
        return new SplashScreen( this );
    }
	
	public MenuScreen getMenuScreen()
    {
        return new MenuScreen( this );
    }
	
	public MapScreen getMapScreen()
    {
        return new MapScreen( this );
    }

	@Override
	public void create() {
		int[][] map = new int[5][5];
		initialize_world();
		Entity e = EntityFactory.createActor(world,0,0, map);
		
		System.out.println(map[0][0]);
		
		Stats s = e.getComponent(Stats.class);
		
		System.out.println(s.getDefense());

	}

	private void initialize_world() {
		world = new World();
		
		
		world.initialize();
		System.out.println("The world is initialized");
		
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
			setScreen(new SplashScreen(this));

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
	
	
    public void addComponent(Component component, int entityId) {
        if (entityId < 0) return;
        Entity e = world.getEntity(entityId);
        e.addComponent(component);
        e.changedInWorld();
}
}
