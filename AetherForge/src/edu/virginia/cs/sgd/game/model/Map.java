package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.ComponentType;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.PlayerManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import edu.virginia.cs.sgd.game.controller.Battle;
import edu.virginia.cs.sgd.game.controller.DeathSystem;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Sprite;
import edu.virginia.cs.sgd.game.model.components.SpriteAnimation;
import edu.virginia.cs.sgd.game.model.managers.PositionManager;
import edu.virginia.cs.sgd.game.view.AnimationSystem;
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.util.Animation;
import edu.virginia.cs.sgd.util.PathfindingPoint;
import edu.virginia.cs.sgd.util.Point;

public abstract class Map {

	protected World world;
	protected TiledMap map;
	protected MapLayer blockLayer = null;

	// private String direction = "";
	// private Stack<String> dStack = new Stack<String>();

	public Map(TiledMap map, RenderSystem renderer) {

		this.map = map;

		world = new World();
		// damageSystem = world.setSystem(new DamageSystem(), true);

		PositionManager pos = new PositionManager();
		pos.setWorld(getMapWidth(), getMapHeight());
		world.setManager(pos);
		world.setSystem(new AnimationSystem());
		world.setSystem(new DeathSystem());
		world.setSystem(renderer);
		world.setManager(new PlayerManager());

		// addEntity(new Point(1, 3), "berserker", "Human");
		// addEntity(new Point(1, 5), "cleric", "Enemy");
		// addEntity(new Point(3, 4), "archer", "Enemy");
		// test animation
		addEntity(new Point(5, 5), "idleman", "Human");
		addEntity(new Point(1, 3), "idleman", "Enemy");
		blockLayer = this.map.getLayers().get("block");
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

		// world.deleteSystem(damageSystem);
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
		ImmutableBag<Entity> units = man.getEntitiesOfPlayer(player);

		List<Integer> res = new ArrayList<Integer>();
		for (int i = 0; i < units.size(); i++) {
			res.add(units.get(i).getId());
		}

		return res;
	}

	public List<Point> getUnitComponents(String player) {
		PlayerManager man = world.getManager(PlayerManager.class);
		ComponentMapper<MapPosition> mapper = world
				.getMapper(MapPosition.class);
		ImmutableBag<Entity> units = man.getEntitiesOfPlayer(player);

		List<Point> res = new ArrayList<Point>();
		for (int i = 0; i < units.size(); i++) {
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

	public boolean pointFree(Point p, boolean collideWithEntities) {

		boolean env = true;
		boolean entity = (getEntityAt(p) == -1);
		if (collideWithEntities == false)
			entity = true;

		boolean notBlocked = true;
		if (blockLayer != null) {
			TiledMapTileLayer.Cell blockCheck = ((TiledMapTileLayer) blockLayer)
					.getCell(p.getX(), p.getY());

			if (blockCheck != null) {
				notBlocked = false;
			}
		}
		boolean xBounds = p.getX() > -1 && p.getX() < getMapWidth();
		boolean yBounds = p.getY() > -1 && p.getY() < getMapHeight();

		boolean bounds = xBounds && yBounds;

		return env && entity && bounds && notBlocked;
	}

	public void attack(int id, int defId) {
		Entity e = world.getEntity(id);
		Sprite sprite = e.getComponent(Sprite.class);
		Entity def = world.getEntity(defId);
		SpriteAnimation sam = e.getComponent(SpriteAnimation.class);
		Animation animator = sam.animation;
		sprite.name = "attackman";
		// start attack animation

		System.out.println("Waiting on Attack Animation...");
		try {
			Thread.sleep((long) animator.animationDuration*7);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		Battle.OneOnOneFight(e, def);
		sprite.name = "idleman";
		e.changedInWorld();
		def.changedInWorld();

	}

	public void move(int id, Point p) {
		Entity e = world.getEntity(id);
		SpriteAnimation sam = e.getComponent(SpriteAnimation.class);
		MapPosition m = e.getComponent(MapPosition.class);
		Sprite s = e.getComponent(Sprite.class);
		Animation animator = sam.animation;
		// m.setX(p.getX());
		// m.setY(p.getY());

		ArrayList<Point> path = createPathAStar(new Point(m.getX(), m.getY()),
				p);
		if (path == null) {
			// Apparently there is no way to get to the point we have selected
			return;
		}

		int prevX = 0;
		int prevY = 0;
		String prevName = "";
		int changedCount = 0;
		for (Point tp : path) {
				if (prevX < tp.getX()) {
					s.changed = true;
					s.name = "walkmanE";
					System.out.println("changed to E");
				} else if (prevX > tp.getX()) {
					s.changed = true;
					s.name = "walkmanW";
					System.out.println("changed to W");
				} else if (prevY > tp.getY()) {
					s.changed = true;
					s.name = "walkmanS";
					System.out.println("changed to S");
				} else if (prevY < tp.getY()) {
					s.changed = true;
					s.name = "walkmanS";
					System.out.println("changed to N");
				}
			
			m.setX(tp.getX());
			m.setY(tp.getY());
			e.changedInWorld();
			try {
				Thread.sleep(200);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			prevX = tp.getX();
			prevY = tp.getY();
			prevName = s.name;
		}
		s.name = "idleman";
	}

	public void addEntity(Point p, String name, String player) {
		if (pointFree(p, true)) {
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

	public abstract int checkEnd();

	// Use the distance formula to determine a heuristic for the distance
	private double pathHeuristic(Point start, Point goal) {
		return Math.sqrt((goal.getX() - start.getX())
				* (goal.getX() - start.getX()) + (goal.getY() - start.getY())
				* (goal.getY() - start.getY()));
	}

	public ArrayList<Point> createPathAStar(Point s, Point g) {

		int maxWidth = getMapWidth();
		int maxHeight = getMapHeight();

		System.out.println("(" + s.getX() + ", " + s.getY() + ") -> ("
				+ g.getX() + ", " + g.getY() + ")");

		ArrayList<PathfindingPoint> open = new ArrayList<PathfindingPoint>();
		ArrayList<PathfindingPoint> closed = new ArrayList<PathfindingPoint>();

		PathfindingPoint start = new PathfindingPoint(s.getX(), s.getY());
		PathfindingPoint goal = new PathfindingPoint(g.getX(), g.getY());

		// Check if the goal is clear
		if (pointFree(goal, true) == false)
			return null;

		// Add the starting node to the list
		open.add((PathfindingPoint) start);

		start.setPast(0);
		start.setFuture(pathHeuristic(start, goal));

		PathfindingPoint current = null;
		while (open.size() > 0) {

			// Find the element with the lowest score
			current = open.get(0);
			for (PathfindingPoint x : open) {
				if (x.getFuture() < current.getFuture())
					current = x;
			}

			if (current.equals(goal)) {
				break;
			}

			open.remove(current);
			closed.add(current);
			if (current.getX() > 0) {
				PathfindingPoint neighbor = new PathfindingPoint(
						current.getX() - 1, current.getY());
				if (pointFree(neighbor, true) == true
						&& closed.contains(neighbor) == false) {
					double tent = current.getPast() + 1;

					if (open.contains(neighbor) == false
							|| tent < open.get(open.indexOf(neighbor))
									.getPast()) {
						neighbor.setParent(current);
						neighbor.setPast(tent);
						neighbor.setFuture(tent + pathHeuristic(neighbor, goal));
						if (open.contains(neighbor) == false) {
							open.add(neighbor);
							// System.out.println("reached west");
							// direction = "W";
							// dStack.push("walkman" + "W");
						}
					}
				}
			}
			if (current.getX() < maxWidth) {
				PathfindingPoint neighbor = new PathfindingPoint(
						current.getX() + 1, current.getY());
				if (pointFree(neighbor, true) == true
						&& closed.contains(neighbor) == false) {
					double tent = current.getPast() + 1;

					if (open.contains(neighbor) == false
							|| tent < open.get(open.indexOf(neighbor))
									.getPast()) {
						neighbor.setParent(current);
						neighbor.setPast(tent);
						neighbor.setFuture(tent + pathHeuristic(neighbor, goal));
						if (open.contains(neighbor) == false) {
							open.add(neighbor);
							// System.out.println("reached east");
							// direction = "E";
							// dStack.push("walkman" + "E");
						}
					}
				}
			}
			if (current.getY() > 0) {
				PathfindingPoint neighbor = new PathfindingPoint(
						current.getX(), current.getY() - 1);
				if (pointFree(neighbor, true) == true
						&& closed.contains(neighbor) == false) {
					double tent = current.getPast() + 1;

					if (open.contains(neighbor) == false
							|| tent < open.get(open.indexOf(neighbor))
									.getPast()) {
						neighbor.setParent(current);
						neighbor.setPast(tent);
						neighbor.setFuture(tent + pathHeuristic(neighbor, goal));
						if (open.contains(neighbor) == false) {
							open.add(neighbor);
							// System.out.println("reached south");
							// direction = "S";
							// dStack.push("walkman" + "S");
						}
					}
				}
			}
			if (current.getY() < maxHeight) {
				PathfindingPoint neighbor = new PathfindingPoint(
						current.getX(), current.getY() + 1);
				if (pointFree(neighbor, true) == true
						&& closed.contains(neighbor) == false) {
					double tent = current.getPast() + 1;

					if (open.contains(neighbor) == false
							|| tent < open.get(open.indexOf(neighbor))
									.getPast()) {
						neighbor.setParent(current);
						neighbor.setPast(tent);
						neighbor.setFuture(tent + pathHeuristic(neighbor, goal));
						if (open.contains(neighbor) == false) {
							open.add(neighbor);
							// System.out.println("reached north");
							// direction = "S";
							// dStack.push("walkman" + "S");
						}
					}
				}
			}
		}
		ArrayList<Point> ret = new ArrayList<Point>();
		while (current != null) {
			ret.add(new Point(current.getX(), current.getY()));
			current = current.getParent();
		}

		Collections.reverse(ret);

		return ret;
	}

}
