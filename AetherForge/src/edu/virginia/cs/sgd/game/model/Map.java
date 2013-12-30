package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.PlayerManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.controller.Battle;
import edu.virginia.cs.sgd.game.controller.DeathSystem;
import edu.virginia.cs.sgd.game.model.components.HP;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Selection;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.game.view.HighlightSystem;
import edu.virginia.cs.sgd.game.view.HighlightType;
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.StateMachine;
import edu.virginia.cs.sgd.util.Triple;

public class Map {

	private World world;
	
	private TiledMap map;

	private int selectedId;
	private StateMachine state;
	
	public Map(TiledMap map, RenderSystem renderer) {

		this.map = map;

		world = new World();
		//damageSystem = world.setSystem(new DamageSystem(), true);

		PositionManager pos = new PositionManager();
		pos.setWorld(getMapWidth(), getMapHeight());
		world.setManager(pos);

		world.setSystem(new DeathSystem(this));
		world.setSystem(renderer);

		MapProperties prop = map.getProperties();
		int mapWidth = prop.get("tilewidth", Integer.class);
		int mapHeight = prop.get("tileheight", Integer.class);
		
		world.setSystem(new HighlightSystem(mapWidth, mapHeight, renderer.getSpriteBatch()));
		
		world.setManager(new PlayerManager());

		add(1,3,"berserker", false);
		add(1,5,"cleric", true);
		add(3,4,"archer", true);

		selectedId = -1;
		
		int[][] arr = {{0, 1}, {0, 2}, {0, 0}};
		state = new StateMachine(arr, 0);
	}

	public void initialize() {
		world.initialize();
		world.process();
		System.out.println("The world is initialized");
	}

	public void update() {

		world.process();

	}

	public int getMapWidth() {
		MapProperties prop = map.getProperties();
		int mapWidth = prop.get("width", Integer.class);
		return mapWidth;
	}

	public int getMapHeight() {
		MapProperties prop = map.getProperties();
		int mapHeight = prop.get("height", Integer.class);
		return mapHeight;
	}

	public void dispose() {

		//		world.deleteSystem(damageSystem);
	}
	
	public List<Point> getUnits(String player) {
		PlayerManager man = world.getManager(PlayerManager.class);
		ComponentMapper<MapPosition> mapper = world.getMapper(MapPosition.class);
		ImmutableBag<Entity> units =  man.getEntitiesOfPlayer(player);

		List<Point> res = new ArrayList<Point>();
		for(int i = 0; i < units.size(); i++) {
			Entity e = units.get(i);
			MapPosition m = mapper.get(e);
			res.add(m.getPoint());
		}
		
		return res;
	}
	
	public Entity getEntityAt(Point p) {
		PositionManager pos = world.getManager(PositionManager.class);
		int id = pos.getEntityAt(p);

		if(id == -1) {
			return null;
		}

		return world.getEntity(id);
	}
	
	public boolean pointFree(Point p) {
	
		boolean env = true;
		boolean entity = getEntityAt(p) == null;

		boolean xBounds = p.getX() > -1 && p.getX() < getMapWidth();
		boolean yBounds = p.getY() > -1 && p.getY() < getMapHeight();
		
		boolean bounds = xBounds && yBounds;
		
		return env && entity && bounds;
	}
	
	private ArrayList<Point> selectTiles(int min, int max, Point s, boolean collision) {

		ArrayList<Point> res = new ArrayList<Point>();
		
		Collection<Point> mem = new ArrayList<Point>();
		
		Triple start = new Triple(0, s.getX(), s.getY());
		Queue<Triple> q = new LinkedList<Triple>();
		q.add(start);
//		mem.add(start);
		
		while (!q.isEmpty()) {
			//System.out.println("loop");
			Triple t = q.poll();

			Point p = new Point(t.getX(), t.getY());

			if(mem.contains(p)) {
				continue;
			}
			
			mem.add(p);

			if((collision && !pointFree(p) && t != start) || t.getMvn() > max) {
				continue;
			}
			
			if(t.getMvn() >= min) {
				res.add(p);
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
	
	private void attack(Entity e, Point p) {

		Entity def = getEntityAt(p);
		if(def != null) {
			Selection sel = e.getComponent(Selection.class);
			if(sel.getType(p) == HighlightType.ATTACK) {
				Battle.OneOnOneFight(e, def);
			}
		}
		
		selectedId = -1;
		
		e.removeComponent(Selection.class);
		e.changedInWorld();
		
	}
	
	private void moveEntity(Entity e, Point p) {

		Selection sel = e.getComponent(Selection.class);

		if(sel.getType(p) == HighlightType.MOVE) {

			MapPosition m = e.getComponent(MapPosition.class);
			m.setX(p.getX());
			m.setY(p.getY());

			e.changedInWorld();
			Weapon w = e.getComponent(Weapon.class);
			
			ArrayList<Point> tiles = selectTiles(w.getMinRange(), w.getMaxRange(), m.getPoint(), false);
			
			Selection sele = new Selection();
			
			for(Point tile : tiles) {
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
	
	private void select(Entity e, String player) {

		PlayerManager teams = world.getManager(PlayerManager.class);
		
		if(teams.getPlayer(e).equals(player)){
			
			selectedId = e.getId();
			
			MapPosition m = e.getComponent(MapPosition.class);
			Stats s = e.getComponent(Stats.class);
			ArrayList<Point> tiles = selectTiles(0, s.getMovement(), m.getPoint(), true);

			Selection sel = new Selection();
			
			for(Point pos : tiles) {
				sel.addTile(pos, HighlightType.MOVE);
			}
			e.addComponent(sel);
			e.changedInWorld();
			
		}
		else{
			System.out.println("That is not your unit!");
		}
		
	}
	
	public void onTouch(Point p, String player) {
		
		switch(state.getState()) {
		case MapState.MOVED:
			attack(world.getEntity(selectedId), p);
			break;
		case MapState.SELECT:
			moveEntity(world.getEntity(selectedId), p);
			break;
		case MapState.NORMAL:
			Entity e = getEntityAt(p);
			if(e != null) {
				select(e, player);
			}
			break;
		}
		
		state.transition(1);
	}
	
	public void add(int x, int y, String name, boolean enemy) {
		Entity e = world.createEntity();
		e.addComponent(new MapPosition(x,y));
		e.addComponent(new Stats());
		e.addComponent(new HP());
		e.addComponent(new Weapon());
		e.addComponent(new TextureName(name));
		e.addToWorld();
		
		PlayerManager teams = world.getManager(PlayerManager.class);
		teams.setPlayer(e, enemy ? "Enemy" : "Human");
		
	}
	public void remove(Entity e) {
		PlayerManager teams = world.getManager(PlayerManager.class);
		teams.removeFromPlayer(e);
		world.deleteEntity(e);
	}
	
	public void addSystem(EntitySystem sys) {
		
		world.setSystem(sys);
	}
	
	public void reset() {
		state.reset();
	}
}
