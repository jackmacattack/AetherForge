package edu.virginia.cs.sgd.game;

import java.util.ArrayList;
import java.util.LinkedList;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import edu.virginia.cs.sgd.GameOfSwords;
import edu.virginia.cs.sgd.controller.Battle;
import edu.virginia.cs.sgd.controller.DeathSystem;
import edu.virginia.cs.sgd.game.model.EntityFactory;
import edu.virginia.cs.sgd.game.model.PositionManager;
import edu.virginia.cs.sgd.game.model.components.Damage;
import edu.virginia.cs.sgd.game.model.components.HP;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.game.model.systems.DamageSystem;
import edu.virginia.cs.sgd.game.view.SpriteMaker;
import edu.virginia.cs.sgd.util.Triple;

public class Level {

	private World world;
	private TiledMap m_Map;

	private int selectedId;

	private ArrayList<SpriteMaker> addList;
	private ArrayList<Integer> removeList;
	private LinkedList<Triple> pathlist;

	private DamageSystem damageSystem;

	public Level() {

		m_Map = GameOfSwords.getManager().get("data/sample_map.tmx");
		pathlist = new LinkedList<Triple>();
		addList = new ArrayList<SpriteMaker>();
		removeList = new ArrayList<Integer>();

		initialize_world();

		add(1,3,"berserker");
		add(1,5,"cleric");
		add(3,4,"archer");

		world.process();
		
		selectedId = -1;

		//		testDamage();
	}

	public TiledMap getMap() {
		// TODO Auto-generated method stub
		return m_Map;
	}

	public Vector2 getPosition(int modelId) {
		// TODO Auto-generated method stub
		Entity e = world.getEntity(modelId);
		MapPosition m = e.getComponent(MapPosition.class);

		return new Vector2(m.getX(), m.getY());
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

	public LinkedList<Triple> getPathList() {
		return pathlist;
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

	public int getMapWidth() {
		// TODO Auto-generated method stub
		MapProperties prop = m_Map.getProperties();
		int mapWidth = prop.get("width", Integer.class);
		return mapWidth;
	}

	public int getMapHeight() {
		// TODO Auto-generated method stub
		MapProperties prop = m_Map.getProperties();
		int mapHeight = prop.get("height", Integer.class);
		return mapHeight;
	}

	private void initialize_world() {
		world = new World();
		//damageSystem = world.setSystem(new DamageSystem(), true);

		PositionManager pos = new PositionManager();
		pos.setWorld(getMapWidth(), getMapHeight());
		world.setManager(pos);
		
		world.setSystem(new DeathSystem(this));
		
		world.initialize();
		System.out.println("The world is initialized");

	}

	public void dispose() {

		//		world.deleteSystem(damageSystem);
	}

	public void processSystems()
	{
		world.process();
		//System.out.println("process");
		//		damageSystem.process();
	}

	public void addComponent(Component component, int entityId)
	{
		if (entityId < 0) return;
		Entity e = world.getEntity(entityId);
		e.addComponent(component);
		e.changedInWorld();
	}

	public Entity getEntityAt(int x, int y) {
		PositionManager pos = world.getManager(PositionManager.class);
		int id = pos.getEntityAt(x, y);

		if(id == -1) {
			return null;
		}

		return world.getEntity(id);
	}

	public void highlightTiles(int mv, int x, int y){
		Triple start = new Triple(0, x, y);
		pathlist = new LinkedList<Triple>();
		pathlist.add(start);
		while (true) {
			//System.out.println("loop");
			Triple t = pathlist.pop();
			if(t.getMvn()+1 > mv){
				pathlist.add(t);
				break;
			}
			Triple tl = new Triple(t.getMvn() + 1, t.getX() - 1, t.getY());
			if (!pathlist.contains(tl)) {
				pathlist.add(tl);
			}

			Triple tr = new Triple(t.getMvn() + 1, t.getX() + 1, t.getY());
			if (!pathlist.contains(tr)) {
				pathlist.add(tr);
			}
			Triple tu = new Triple(t.getMvn() + 1, t.getX(), t.getY() + 1);
			if (!pathlist.contains(tu)) {
				pathlist.add(tu);
			}

			Triple td = new Triple(t.getMvn() + 1, t.getX(), t.getY() - 1);
			if (!pathlist.contains(td)) {
				pathlist.add(td);
			}
			pathlist.add(t);
		}
		System.out.println(pathlist);

	}

	public void moveSelected(int x, int y) {

		Entity e = world.getEntity(selectedId);

		MapPosition m = e.getComponent(MapPosition.class);
		
		if(pathlist.contains(new Triple(0, x, y))) {

			m.setX(x);
			m.setY(y);
			
			Entity e2 = getEntityAt(x+1,y);
			if(e2 != null && e2 != e) {
				Battle.OneOnOneFight(e, e2);
			}
			
			e2 = getEntityAt(x,y+1);
			if(e2 != null && e2 != e) {
				Battle.OneOnOneFight(e, e2);
			}
			
			e2 = getEntityAt(x-1,y);
			if(e2 != null && e2 != e) {
				Battle.OneOnOneFight(e, e2);
			}
			
			e2 = getEntityAt(x,y-1);
			if(e2 != null && e2 != e) {
				Battle.OneOnOneFight(e, e2);
			}

			e.changedInWorld();
			
		}
	}
	public void select(int x, int y) {
		Entity e = getEntityAt(x, y);
		System.out.println(e);
		if(e == null) {
			if(selectedId != -1) {
				moveSelected(x, y);
			}
			
			selectedId = -1;
			pathlist.clear();
		}
		else {
			selectedId = e.getId();

			MapPosition m = e.getComponent(MapPosition.class);
			Stats s = e.getComponent(Stats.class);
			highlightTiles(s.getMovement(), m.getX(), m.getY());
		}
	}
	public void add(int x, int y, String name) {
		Entity e = world.createEntity();
		e.addComponent(new MapPosition(x,y));
		e.addComponent(new Stats());
		e.addComponent(new HP());
		e.addComponent(new Weapon());
		e.addToWorld();
		addList.add(new SpriteMaker(e.getId(), name));
	}
	public void remove(Entity e) {
		world.deleteEntity(e);
		removeList.add(e.getId());
	}
}
