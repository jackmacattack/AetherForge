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
import edu.virginia.cs.sgd.game.model.components.Damage;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.systems.*;

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
	
	
	
	private DamageSystem damageSystem;

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
		int[][] actorMap = new int[5][5];
		initialize_world();
		Entity e = EntityFactory.createActor(world,0,0, actorMap);
		
		System.out.println(actorMap[0][0]);
		
		Stats s = e.getComponent(Stats.class);
		
		
		System.out.println(s.getDefense());
		
		s.setDefense(s.getDefense()+1);
		
		System.out.println(s.getDefense());
		s = e.getComponent(Stats.class);
		System.out.println(s.getDefense());
		
		
		MapPosition m = e.getComponent(MapPosition.class);
		e.addComponent(new Damage(30));
		System.out.println(m);
		
	}

	private void initialize_world() {
		world = new World();
		damageSystem = world.setSystem(new DamageSystem(), true);
		
		world.initialize();
		System.out.println("The world is initialized");
		
	}

	@Override
	public void dispose() {
		world.deleteSystem(damageSystem);
	}

	@Override
	public void render() {
		super.render();

		processSystems();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (getScreen() == null) {
			//setScreen(new SplashScreen(this));
			setScreen(new MapScreen(this));

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
	
	public void processSystems()
	{
		System.out.println("process");
		damageSystem.process();
	}
    public void addComponent(Component component, int entityId)
    {
        if (entityId < 0) return;
        Entity e = world.getEntity(entityId);
        e.addComponent(component);
        e.changedInWorld();
    }
}
