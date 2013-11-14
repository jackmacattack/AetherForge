package edu.virginia.cs.sgd.game;

import java.awt.Point;
import java.util.ArrayList;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.GameOfSwords;
import edu.virginia.cs.sgd.game.model.EntityFactory;
import edu.virginia.cs.sgd.game.model.components.Damage;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.systems.DamageSystem;
import edu.virginia.cs.sgd.game.view.SpriteMaker;

public class Level {

	public World world;
	private TiledMap m_Map;

	private ArrayList<SpriteMaker> addList;
	private ArrayList<Integer> removeList;

	private DamageSystem damageSystem;
	
	public Level() {

		m_Map = GameOfSwords.getManager().get("data/sample_map.tmx");
		
		addList = new ArrayList<SpriteMaker>();
		removeList = new ArrayList<Integer>();
		
		addList.add(new SpriteMaker(0, "sample"));
		
		testDamage();
	}

	public TiledMap getMap() {
		// TODO Auto-generated method stub
		return m_Map;
	}

	public Point getPosition(int modelId) {
		// TODO Auto-generated method stub
		return new Point(0, 0);
	}
	
	public ArrayList<SpriteMaker> getAddList() {
		return addList;
	}
	
	public ArrayList<Integer> getRemoveList() {
		return removeList;
	}

	public void clearSpriteUpdates() {
		addList.clear();
		removeList.clear();
	}
	
	public void testDamage() {

		int[][] actorMap = new int[5][5];
		initialize_world();
		Entity e = EntityFactory.createActor(world,0,0, actorMap);
		
		System.out.println(actorMap[0][0]);
		
		Stats s = e.getComponent(Stats.class);
		
		System.out.println(s);
		
		s.setDefense(s.getDefense()+1);
		
		System.out.println(s.getDefense());
		s = e.getComponent(Stats.class);
		System.out.println(s.getDefense());
		
		
		MapPosition m = e.getComponent(MapPosition.class);
		e.addComponent(new Damage(30));
		System.out.println(m);

	}

	public void update() {

		processSystems();
		
	}
	
	private void initialize_world() {
		world = new World();
		damageSystem = world.setSystem(new DamageSystem(), true);
		
		world.initialize();
		System.out.println("The world is initialized");

	}
	
	public void dispose() {

		world.deleteSystem(damageSystem);
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
