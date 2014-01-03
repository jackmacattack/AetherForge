package edu.virginia.cs.sgd.game.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.virginia.cs.sgd.game.model.Map;
import edu.virginia.cs.sgd.game.model.MapState;
import edu.virginia.cs.sgd.game.model.Selection;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Stats;
import edu.virginia.cs.sgd.game.model.components.Weapon;
import edu.virginia.cs.sgd.game.view.SelectionType;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.StateMachine;
import edu.virginia.cs.sgd.util.Triple;

public class MapOperator {

	private Map map;

	private int selectedId;
	private Selection selTiles;
	private StateMachine state;

	public MapOperator(Map map) {

		this.map = map;

		selectedId = -1;
		selTiles = null;

		int[][] arr = {{0, 1}, {0, 2}, {0, 0}};
		state = new StateMachine(arr, 0);
	}

	public List<Point> getUnits(String player) {
		return map.getUnits(player);
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
		//		mem.add(start);

		while (!q.isEmpty()) {
			//System.out.println("loop");
			Triple t = q.poll();

			Point p = new Point(t.getX(), t.getY());

			if(mem.contains(p)) {
				continue;
			}

			mem.add(p);

			boolean collide = collision ? !map.pointFree(p) && t != start : false;
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
			//			q.add(t);
		}
		//System.out.println(q);

		return res;
	}

	private void setAttackTiles(int id) {

		MapPosition m = map.getComponent(id, MapPosition.class);
		Weapon w = map.getComponent(id, Weapon.class);
		List<Point> tiles = selectTiles(w.getMinRange(), w.getMaxRange(), m.getPoint(), false);

		Selection sel = new Selection();

		for(Point tile : tiles) {
			sel.addTile(tile, SelectionType.ATTACK);
		}
		selTiles = sel;
	}

	private void setMoveTiles(int id) {

		MapPosition m = map.getComponent(id, MapPosition.class);
		Stats s = map.getComponent(id, Stats.class);

		List<Point> tiles = selectTiles(0, s.getMovement(), m.getPoint(), true);

		Selection sel = new Selection();

		for(Point pos : tiles) {
			sel.addTile(pos, SelectionType.MOVE);
		}
		selTiles = sel;

	}

	private void select(int id, String player) {

		String owner = map.getPlayer(id);

		if(owner.equals(player)) {

			selectedId = id;

			setMoveTiles(id);
		}
		else {
			System.out.println("That is not your unit!");
		}

	}

	private void deselect() {
		selectedId =  -1;
		selTiles = null;
	}

	public void onTouch(Point p, String player) {

		switch(state.getState()) {
		case MapState.MOVED:
			if(selTiles.getType(p) == SelectionType.ATTACK) {
				map.attack(selectedId, p);
				deselect();
			}
			break;
		case MapState.SELECT:
			if(selTiles.getType(p) == SelectionType.MOVE) {
				map.move(selectedId, p);
				setAttackTiles(selectedId);//(getEntity(selectedId));
			}
			break;
		case MapState.NORMAL:
			int id = getEntityAt(p);
			if(id != -1) {
				select(id, player);
			}
			break;
		}

		state.transition(1);
	}

	public Selection getSelection() {

		return selTiles;
	}

}
