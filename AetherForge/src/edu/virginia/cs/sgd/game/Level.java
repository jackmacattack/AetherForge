package edu.virginia.cs.sgd.game;

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
	private LinkedList<Triple> pathlist;
	private LinkedList<Triple> attacklist;

	private DamageSystem damageSystem;
	
	public Level(MapScreen mp) {

		m_Map = Entry.getManager().get("data/map1.tmx");
		pathlist = new LinkedList<Triple>();
		attacklist = new LinkedList<Triple>();
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
		//System.out.println(pathlist);

	}

	public void highlightAttackTiles(int r, int x, int y){
		Triple start = new Triple(0, x, y);
		attacklist = new LinkedList<Triple>();
		attacklist.add(start);
		while (true) {
			//System.out.println("loop");
			Triple t = attacklist.pop();
			if(t.getMvn()+1 > r){
				attacklist.add(t);
				break;
			}
			Triple tl = new Triple(t.getMvn() + 1, t.getX() - 1, t.getY());
			if (!attacklist.contains(tl)) {
				attacklist.add(tl);
			}

			Triple tr = new Triple(t.getMvn() + 1, t.getX() + 1, t.getY());
			if (!attacklist.contains(tr)) {
				attacklist.add(tr);
			}
			Triple tu = new Triple(t.getMvn() + 1, t.getX(), t.getY() + 1);
			if (!attacklist.contains(tu)) {
				attacklist.add(tu);
			}

			Triple td = new Triple(t.getMvn() + 1, t.getX(), t.getY() - 1);
			if (!attacklist.contains(td)) {
				attacklist.add(td);
			}

			if(!t.equals(start)) {
				attacklist.add(t);
			}
		}
		//System.out.println(attacklist);

	}

	public void moveSelected(int x, int y) {

		Entity e = world.getEntity(selectedId);

		MapPosition m = e.getComponent(MapPosition.class);

		if(pathlist.contains(new Triple(0, x, y))) {

			m.setX(x);
			m.setY(y);

			selectedMoved = true;

			e.changedInWorld();
			
		}
	}
	public void select(int x, int y) {
//		c.processTurn();
		Entity e = getEntityAt(x, y);
			
		if(selectedMoved) {
			Entity sel = world.getEntity(selectedId);
			if(e != null) {
				if(inRange(sel, e)) {
					Battle.OneOnOneFight(sel, e);
				}

			}
			selectedId = -1;
			selectedMoved = false;
			attacklist.clear();
//			world.getEntity(selectedId).getComponent(Stats.class).setHasTakenTurn(true);
			
			sel.removeComponent(Selection.class);
			sel.changedInWorld();
		}
		else if(selectedId != -1) {
			moveSelected(x, y);
			pathlist.clear();
			
			Entity sel = world.getEntity(selectedId);
			Weapon w = sel.getComponent(Weapon.class);
			MapPosition m = sel.getComponent(MapPosition.class);
			
			highlightAttackTiles(w.getMaxRange(), m.getX(), m.getY());

			Selection sele = new Selection();
			
			for(Triple move : attacklist) {
				MapPosition pos = new MapPosition(move.getX(), move.getY());
				sele.addTile(pos, HighlightType.ATTACK);
			}
			sel.addComponent(sele);
			sel.changedInWorld();
		}
		else if (e != null) {
			if(units.contains(e.getId(), true)){
				selectedId = e.getId();

				MapPosition m = e.getComponent(MapPosition.class);
				Stats s = e.getComponent(Stats.class);
				highlightTiles(s.getMovement(), m.getX(), m.getY());

				Selection sel = new Selection();
				
				for(Triple move : pathlist) {
					MapPosition pos = new MapPosition(move.getX(), move.getY());
					sel.addTile(pos, HighlightType.MOVE);
				}
				e.addComponent(sel);
				e.changedInWorld();
				
			}else{
				System.out.println("That is not your unit!");
			}
			
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
