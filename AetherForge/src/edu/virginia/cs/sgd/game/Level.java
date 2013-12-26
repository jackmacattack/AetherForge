package edu.virginia.cs.sgd.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

import edu.virginia.cs.sgd.Entry;
import edu.virginia.cs.sgd.game.controller.Battle;
import edu.virginia.cs.sgd.game.controller.Controller;
import edu.virginia.cs.sgd.game.controller.DeathSystem;
import edu.virginia.cs.sgd.game.model.PositionManager;
import edu.virginia.cs.sgd.game.model.components.HP;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Selection;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.game.view.HighlightType;
import edu.virginia.cs.sgd.menu.MapScreen;
import edu.virginia.cs.sgd.util.Triple;

public class Level {

	private World world;
	
	public Array<Integer> units;
	public Array<Integer> getUnits() {
		return units;
	}

	public Array<Integer> getEnemies() {
		return enemies;
	}

	public Array<Integer> enemies;
	
	private TiledMap m_Map;

	private boolean selectedMoved = false;
	private int selectedId;
	private Controller c;
	
	public Level(MapScreen mp) {

		m_Map = Entry.getManager().get("data/map1.tmx");
		c = new Controller(mp, this);
		units = new Array<Integer>();
		enemies = new Array<Integer>();

		initialize_world();

		add(1,3,"berserker", false);
		add(1,5,"cleric", true);
		add(3,4,"archer", true);

		selectedId = -1;

		//		testDamage();
	}

	public void initialize() {
		world.initialize();
		world.process();
		System.out.println("The world is initialized");
	}
	
	public TiledMap getMap() {
		return m_Map;
	}

	public void update() {

		world.process();

	}

	public int getMapWidth() {
		MapProperties prop = m_Map.getProperties();
		int mapWidth = prop.get("width", Integer.class);
		return mapWidth;
	}

	public int getMapHeight() {
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

//		world.initialize();

	}

	public void dispose() {

		//		world.deleteSystem(damageSystem);
	}

	public void addComponent(Component component, int entityId)
	{
		if (entityId < 0) return;
		Entity e = world.getEntity(entityId);
		e.addComponent(component);
		e.changedInWorld();
	}

	public Entity getEntityAt(int x, int y) {
		return getEntityAt(new MapPosition(x, y));
	}
	
	public Entity getEntityAt(MapPosition m) {
		PositionManager pos = world.getManager(PositionManager.class);
		int id = pos.getEntityAt(m);

		if(id == -1) {
			return null;
		}

		return world.getEntity(id);
	}
	
	public ArrayList<MapPosition> selectTiles(int min, int max, int x, int y, boolean collision) {

		ArrayList<MapPosition> res = new ArrayList<MapPosition>();
		
		Collection<MapPosition> mem = new ArrayList<MapPosition>();
		
		Triple start = new Triple(0, x, y);
		LinkedList<Triple> q = new LinkedList<Triple>();
		q.add(start);
//		mem.add(start);
		
		while (!q.isEmpty()) {
			//System.out.println("loop");
			Triple t = q.pop();

			MapPosition pos = new MapPosition(t.getX(), t.getY());

			if(mem.contains(pos)) {
				continue;
			}
			
			mem.add(pos);
			Entity e = getEntityAt(pos);

			if((collision && e != null && t != start) || t.getMvn() > max) {
				continue;
			}
			
			if(t.getMvn() >= min) {
				res.add(pos);
			}
			
			Triple tl = new Triple(t.getMvn() + 1, t.getX() - 1, t.getY());
			if (!q.contains(tl)) {
				q.add(tl);
			}

			Triple tr = new Triple(t.getMvn() + 1, t.getX() + 1, t.getY());
			if (!q.contains(tr)) {
				q.add(tr);
			}

			Triple tu = new Triple(t.getMvn() + 1, t.getX(), t.getY() + 1);
			if (!q.contains(tu)) {
				q.add(tu);
			}

			Triple td = new Triple(t.getMvn() + 1, t.getX(), t.getY() - 1);
			if (!q.contains(td)) {
				q.add(td);
			}
//			q.add(t);
		}
		//System.out.println(q);

		return res;
	}
	
	public void attack(Entity e, int x, int y) {

		Entity def = getEntityAt(x, y);
		if(def != null) {
			MapPosition pos = new MapPosition(x, y);
			Selection sel = e.getComponent(Selection.class);
			if(sel.getType(pos) == HighlightType.ATTACK) {
				Battle.OneOnOneFight(e, def);
			}
		}
		
		selectedId = -1;
		selectedMoved = false;
		
		e.removeComponent(Selection.class);
		e.changedInWorld();
		
	}
	
	public void moveEntity(Entity e, int x, int y) {

		MapPosition pos = new MapPosition(x, y);
		Selection sel = e.getComponent(Selection.class);

		if(sel.getType(pos) == HighlightType.MOVE) {

			MapPosition m = e.getComponent(MapPosition.class);
			m.setX(x);
			m.setY(y);

			selectedMoved = true;

			e.changedInWorld();
			Weapon w = e.getComponent(Weapon.class);
			
			ArrayList<MapPosition> tiles = selectTiles(w.getMinRange(), w.getMaxRange(), m.getX(), m.getY(), false);
			
			Selection sele = new Selection();
			
			for(MapPosition tile : tiles) {
				sele.addTile(tile, HighlightType.ATTACK);
			}
			e.addComponent(sele);
			
		}
		else {

			selectedId = -1;
			e.removeComponent(Selection.class);
			
		}

		e.changedInWorld();
	}
	
	public void select(Entity e) {

		if(units.contains(e.getId(), true)){
			
			selectedId = e.getId();
			
			MapPosition m = e.getComponent(MapPosition.class);
			Stats s = e.getComponent(Stats.class);
			ArrayList<MapPosition> tiles = selectTiles(0, s.getMovement(), m.getX(), m.getY(), true);

			Selection sel = new Selection();
			
			for(MapPosition pos : tiles) {
				sel.addTile(pos, HighlightType.MOVE);
			}
			e.addComponent(sel);
			e.changedInWorld();
			
		}else{
			System.out.println("That is not your unit!");
		}
		
	}
	
	public void select(int x, int y) {
//		c.processTurn();
		Entity e = getEntityAt(x, y);
		
		if(selectedMoved) {
			attack(world.getEntity(selectedId), x, y);
		}
		else if(selectedId != -1) {
			moveEntity(world.getEntity(selectedId), x, y);
		}
		else if (e != null) {
			select(e);
			
		}
			
	}

	public void add(int x, int y, String name, boolean enemy) {
		Entity e = world.createEntity();
		e.addComponent(new MapPosition(x,y));
		e.addComponent(new Stats());
		e.addComponent(new HP());
		e.addComponent(new Weapon());
		e.addComponent(new TextureName(name));
		e.addToWorld();
//		addList.add(new SpriteMaker(e.getId(), name));
		if(enemy){
			enemies.add(e.getId());
		}else{
			units.add(e.getId());
		}
		
	}
	public void remove(Entity e) {
		world.deleteEntity(e);
		if(enemies.contains(e.getId(),false)){
			enemies.removeValue(e.getId(), false);
		}else{
			units.removeValue(e.getId(),false);
		}
	}
	
	public void addSystem(EntitySystem sys) {
		
		world.setSystem(sys);
	}
}
