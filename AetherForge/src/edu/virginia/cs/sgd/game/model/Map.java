package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.List;

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
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.game.view.SelectionType;
import edu.virginia.cs.sgd.util.Point;

public class Map {

	private World world;
	
	private TiledMap map;
	
	public Map(TiledMap map, RenderSystem renderer) {

		this.map = map;

		world = new World();
		//damageSystem = world.setSystem(new DamageSystem(), true);

		PositionManager pos = new PositionManager();
		pos.setWorld(getMapWidth(), getMapHeight());
		world.setManager(pos);

		world.setSystem(new DeathSystem(this));
		world.setSystem(renderer);
		world.setManager(new PlayerManager());

		add(1,3,"berserker", false);
		add(1,5,"cleric", true);
		add(3,4,"archer", true);

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
	
	public void attack(int id, Point p) {
		Entity e = world.getEntity(id);
		
		Entity def = getEntityAt(p);
		if(def != null) {
			Selection sel = e.getComponent(Selection.class);
			if(sel.getType(p) == SelectionType.ATTACK) {
				Battle.OneOnOneFight(e, def);
			}
		}
		
		e.removeComponent(Selection.class);
		e.changedInWorld();
		
	}
	
	public void move(int id, Point p) {
		Entity e = world.getEntity(id);
		Selection sel = e.getComponent(Selection.class);

		if(sel.getType(p) == SelectionType.MOVE) {

			MapPosition m = e.getComponent(MapPosition.class);
			m.setX(p.getX());
			m.setY(p.getY());

			e.changedInWorld();
			
		}
		else {

			e.removeComponent(Selection.class);
			
		}

		e.changedInWorld();
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

	public String getPlayer(Entity e) {
		PlayerManager teams = world.getManager(PlayerManager.class);
		return teams.getPlayer(e);
	}
	
	public MapOperator getOperator() {
		return new MapOperator(this);
	}

	public Entity getEntity(int id) {
		return world.getEntity(id);
	}
}
