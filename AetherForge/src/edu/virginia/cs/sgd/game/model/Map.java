package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.ComponentType;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.PlayerManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.controller.Battle;
import edu.virginia.cs.sgd.game.controller.DeathSystem;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.managers.PositionManager;
import edu.virginia.cs.sgd.game.view.RenderSystem;
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

		world.setSystem(new DeathSystem());
		world.setSystem(renderer);
		world.setManager(new PlayerManager());

		addEntity(new Point(1, 3), "berserker", "Human");
		addEntity(new Point(1, 5), "cleric", "Enemy");
		addEntity(new Point(3, 4), "archer", "Enemy");

	}

	public void initialize() {
		world.initialize();
		world.process();
		System.out.println("The world is initialized");
	}

	public void update() {

		world.process();

	}

	public void dispose() {

		//		world.deleteSystem(damageSystem);
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

	public List<Integer> getUnits(String player) {
		PlayerManager man = world.getManager(PlayerManager.class);
		ImmutableBag<Entity> units =  man.getEntitiesOfPlayer(player);
		
		List<Integer> res = new ArrayList<Integer>();
		for(int i = 0; i < units.size(); i++) {
			res.add(units.get(i).getId());
		}
		
		return res;
	}
	
	public List<Point> getUnitComponents(String player) {
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

	public int getEntityAt(Point p) {
		PositionManager pos = world.getManager(PositionManager.class);
		int id = pos.getEntityAt(p);

		return id;
	}

	public boolean pointFree(Point p) {

		boolean env = true;
		boolean entity = getEntityAt(p) == -1;

		boolean xBounds = p.getX() > -1 && p.getX() < getMapWidth();
		boolean yBounds = p.getY() > -1 && p.getY() < getMapHeight();

		boolean bounds = xBounds && yBounds;

		return env && entity && bounds;
	}

	public void attack(int id, int defId) {
		Entity e = world.getEntity(id);

		Entity def = world.getEntity(defId);
		Battle.OneOnOneFight(e, def);

		def.changedInWorld();

	}

	public void move(int id, Point p) {
		Entity e = world.getEntity(id);

		MapPosition m = e.getComponent(MapPosition.class);
		m.setX(p.getX());
		m.setY(p.getY());

		e.changedInWorld();

	}

	public void addEntity(Point p, String name, String player) {
		if(pointFree(p)) {
			EntityFactory.createCharacter(world, p, name, player);
		}
	}

	public String getPlayer(int id) {
		Entity e = world.getEntity(id);
		PlayerManager teams = world.getManager(PlayerManager.class);
		return teams.getPlayer(e);
	}

	public <T extends Component> T getComponent(int id, Class<T> type) {
		Entity e = world.getEntity(id);

		return type.cast(e.getComponent(ComponentType.getTypeFor(type)));

	}

}
