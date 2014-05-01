package edu.virginia.cs.sgd.game.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.artemis.Component;

import edu.virginia.cs.sgd.game.model.Map;
import edu.virginia.cs.sgd.game.model.MapState;
import edu.virginia.cs.sgd.game.model.Selection;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.TextureName;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.game.view.SelectionType;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.StateMachine;
import edu.virginia.cs.sgd.util.Triple;

public class MapOperator {

	private Map map;

	private List<Integer> activeUnits;
	
	private int selectedId;
	private Selection selTiles;
	private StateMachine state;

	public MapOperator(Map map, String player) {

		this.map = map;

		activeUnits = getUnits(player);
		
		selectedId = -1;
		selTiles = null;

		int[][] arr = {{0, 1}, {0, 2}, {0, 0}};
		state = new StateMachine(arr, 0);
	}

	public List<Integer> getUnits(String player) {
		return map.getUnits(player);
	}
	
	public List<Point> getUnitComponents(String player) {
		return map.getUnitComponents(player);
	}

	public int getEntityAt(Point p) {
		return map.getEntityAt(p);
	}

	private List<Point> selectTiles(int min, int max, Point s, boolean collision) {

		List<Point> res = new ArrayList<Point>();

		Collection<Point> mem = new ArrayList<Point>();

		Triple start = new Triple(0, s.getX(), s.getY());
		Queue<Triple> q = new LinkedList<Triple>();
		q.add(start);

		while (!q.isEmpty()) {
			Triple t = q.poll();

			Point p = new Point(t.getX(), t.getY());

			if(mem.contains(p)) {
				continue;
			}

			mem.add(p);

			boolean collide = false;
			if (!map.pointFree(p, collision)) {
				if (t != start)
					collide = true;
			}
			
			if(collide || t.getMvn() > max) {
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
		}

		return res;
	}

	private void setAttackTiles(int id) {

		MapPosition m = map.getComponent(id, MapPosition.class);
		Weapon w = map.getComponent(id, Weapon.class);
		Stats s = map.getComponent(id, Stats.class);
		TextureName t = map.getComponent(id, TextureName.class);
		List<Point> tiles = selectTiles(w.getMinRange(), w.getMaxRange(), m.getPoint(), false);

		Selection sel = new Selection();
		sel.setHealth(s.getPercentHealth());
		sel.setMana(s.getPercentMana());
		sel.setTex(t.getName());
		
		for(Point tile : tiles) {
			sel.addTile(tile, SelectionType.ATTACK);
		}
		selTiles = sel;
	}

	private void setMoveTiles(Selection sel, Stats s, MapPosition m) {

		List<Point> tiles = selectTiles(0, s.getMovement(), m.getPoint(), true);


		for(Point pos : tiles) {
			sel.addTile(pos, SelectionType.MOVE);
		}
		selTiles = sel;

	}

	private boolean select(int id, String player) {

		System.out.println("Love");
		
		String owner = map.getPlayer(id);

		MapPosition m = map.getComponent(id, MapPosition.class);
		Stats s = map.getComponent(id, Stats.class);
		TextureName t = map.getComponent(id, TextureName.class);
		Selection sel = new Selection();
		sel.setHealth(s.getPercentHealth());
		sel.setMana(s.getPercentMana());
		sel.setTex(t.getName());
		
		if(owner.equals(player)) {

			if(activeUnits.contains(id)) {

				selectedId = id;

				setMoveTiles(sel, s, m);
				
				return true;
			}
			else {
				System.out.println("This unit has taken its turn!");
			}
		}
		else {
			System.out.println("That is not your unit!");
		}
		
		selTiles = sel;
		return false;

	}

	private void deselect() {
		selectedId =  -1;
		selTiles = null;
	}

	public void onTouch(Point p, String player) {

		int event = 0;
		
		switch(state.getState()) {
		case MapState.MOVED:
			if(selTiles.getType(p) == SelectionType.ATTACK) {
				int defId = getEntityAt(p);
				if(defId != -1 && !map.getPlayer(defId).equals(player)) {
					map.attack(selectedId, defId);
				}
			}
			activeUnits.remove(Integer.valueOf(selectedId));
			deselect();
			event = 1;
			break;
		case MapState.SELECT:
			if(selTiles.getType(p) == SelectionType.MOVE) {
				selTiles = null;
				map.move(selectedId, p);
				setAttackTiles(selectedId);
				event = 1;
			}
			break;
		case MapState.NORMAL:
			int id = getEntityAt(p);
			if(id != -1) {
				if(select(id, player)) {
					event = 1;
				}
			}
			break;
		}
		
		state.transition(event);
	}

	public Selection getSelection() {

		return selTiles;
	}

	public boolean checkTurn() {
		return !activeUnits.isEmpty();
	}

	public <T extends Component> T getComponent(int e, Class<T> t) {
		return map.getComponent(e, t);
	}

}
