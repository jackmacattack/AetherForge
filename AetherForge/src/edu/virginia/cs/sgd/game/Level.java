package edu.virginia.cs.sgd.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
import edu.virginia.cs.sgd.game.model.EntityFactory;
import edu.virginia.cs.sgd.game.model.PositionManager;
import edu.virginia.cs.sgd.game.model.components.Damage;
import edu.virginia.cs.sgd.game.model.components.HP;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Selection;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.game.model.systems.DamageSystem;
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
	private ArrayList<MapPosition> pathlist;
	private ArrayList<MapPosition> attacklist;

	private DamageSystem damageSystem;
	
	public Level(MapScreen mp) {

		m_Map = Entry.getManager().get("data/map1.tmx");
		pathlist = new ArrayList<MapPosition>();
		attacklist = new ArrayList<MapPosition>();
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
		// TODO Auto-generated method stub
		return m_Map;
	}

	public void testDamage() {

		int[][] actorMap = new int[5][5];
//		initialize_world();
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

	public void highlightTiles(int mv, int x, int y){
		
		pathlist = selectTiles(0, mv, x, y, true);
		 
	}

	public void highlightAttackTiles(int min, int max, int x, int y){

		attacklist = selectTiles(min, max, x, y, false);
		
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
			if(attacklist.contains(new MapPosition(x, y))) {
				Battle.OneOnOneFight(e, def);
			}

		}
		selectedId = -1;
		selectedMoved = false;
		attacklist.clear();
//		world.getEntity(selectedId).getComponent(Stats.class).setHasTakenTurn(true);
		
		e.removeComponent(Selection.class);
		e.changedInWorld();
		
	}
	public void moveEntity(Entity e, int x, int y) {

		MapPosition m = e.getComponent(MapPosition.class);

		if(pathlist.contains(new MapPosition(x, y))) {

			m.setX(x);
			m.setY(y);

			selectedMoved = true;

			e.changedInWorld();
			
		}
		
		pathlist.clear();
		
		Weapon w = e.getComponent(Weapon.class);
		
		highlightAttackTiles(w.getMinRange(), w.getMaxRange(), m.getX(), m.getY());

		e.removeComponent(Selection.class);
		Selection sele = new Selection();
		
		for(MapPosition pos : attacklist) {
			sele.addTile(pos, HighlightType.ATTACK);
		}
		e.addComponent(sele);
		e.changedInWorld();
	}
	
	public void select(Entity e) {

		if(units.contains(e.getId(), true)){
			
			selectedId = e.getId();
			
			MapPosition m = e.getComponent(MapPosition.class);
			Stats s = e.getComponent(Stats.class);
			highlightTiles(s.getMovement(), m.getX(), m.getY());

			Selection sel = new Selection();
			
			for(MapPosition pos : pathlist) {
//				MapPosition pos = new MapPosition(move.getX(), move.getY());
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
	
	private boolean inRange(Entity e, Entity e2) {
		MapPosition m1 = e.getComponent(MapPosition.class);
		MapPosition m2 = e2.getComponent(MapPosition.class);

		Weapon w = e.getComponent(Weapon.class);
		int dist = Math.abs(m1.getX() - m2.getX()) + Math.abs(m1.getY() - m2.getY());
		return dist <= w.getMaxRange() && dist >= w.getMaxRange();
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
