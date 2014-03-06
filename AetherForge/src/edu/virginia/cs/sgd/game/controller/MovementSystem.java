package edu.virginia.cs.sgd.game.controller;

import java.util.ArrayList;
import java.util.LinkedList;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.menu.MapScreen;
import edu.virginia.cs.sgd.util.Triple;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<MapPosition> pos;

	private ArrayList<Triple> path;
	public MapScreen map;
	public TiledMap tileMap;

	@SuppressWarnings("unchecked")
	public MovementSystem(MapScreen map, TiledMap tileMap) {
		super(Aspect.getAspectForAll(MapPosition.class));
		this.map = map;
		this.tileMap = tileMap;
		path = new ArrayList<Triple>();
	}

	@Override
	protected void process(Entity e) {
		MapPosition mp = e.getComponent(MapPosition.class);
//		int move = 3;
		int tileid = getCell(mp.getX(), mp.getY());
		System.out.println("I am at " + tileid);
		System.out.println(mp);
		System.out.println("Yay I can move");
	}

	private int getCell(int x, int y) {
		MapLayer layer = tileMap.getLayers().get("Tile Layer 1");
		TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) layer).getCell(x, y);
		System.out.println(cell.getTile().getId());
		return cell.getTile().getId();
	}


	
	// Path algorithm. Start (x,y) and End (x,y) coordinates with move distance
	// Basic tile path algorithm, by checking adjacent tiles.
	// WIP

	public ArrayList<Triple> createPath(int xs, int xe, int ys, int ye, int mv) {
		// MapLayer layer = level.getMap().getLayers().get("Tile Layer 1");
		Triple start = new Triple(0, xe, ye);
		LinkedList<Triple> pathlist = new LinkedList<Triple>();
		pathlist.add(start);
		boolean loop = true;
		while (loop) {
			Triple t = pathlist.pop();
			Triple tl = new Triple(t.getMvn() + 1, t.getX() - 1, t.getY());
			if (!pathlist.contains(tl)) {
				pathlist.add(tl);
				if (tl.getX() == xs && tl.getY() == ys) {
					loop = false;
					break;
				}
			}

			Triple tr = new Triple(t.getMvn() + 1, t.getX() + 1, t.getY());
			if (!pathlist.contains(tr)) {
				pathlist.add(tr);
				if (tr.getX() == xs && tr.getY() == ys) {
					loop = false;
					break;
				}
			}

			Triple tu = new Triple(t.getMvn() + 1, t.getX(), t.getY() + 1);
			if (!pathlist.contains(tu)) {
				pathlist.add(tu);
				if (tu.getX() == xs && tu.getY() == ys) {
					loop = false;
					break;
				}

			}

			Triple td = new Triple(t.getMvn() + 1, t.getX(), t.getY() - 1);
			if (!pathlist.contains(td)) {
				pathlist.add(td);
				if (td.getX() == xs && td.getY() == ys) {
					loop = false;
					break;
				}

			}
			pathlist.add(t);
			//System.out.println(pathlist);
		}
		int m = 42;
		Triple temp = new Triple(0, 0, 0);
		for (int i = mv; i > 0; i--) {
			for (Triple t : pathlist) {
				if (path.isEmpty() && t.getX() == xs && t.getY() == ys) {
					temp = t;
					m = t.getMvn();
					break;
				}
				if (t.getMvn() == m - 1 && temp.isAdjacent(t)) {
					temp = t;
					m = t.getMvn();
					break;
				}

			}
			path.add(temp);
			if (temp.getX() == xe && temp.getY() == ye)
				break;

		}

		return path;

	}
	
	/*public static void main(String[] args) {
		MovementSystem test = new MovementSystem(null, null);
		//ArrayList<Triple> path = test.createPath(1, 5, 3, 8, 11);
		ArrayList<Point> path = test.createPathAStar(new Point(1, 1), new Point(1, 5));
		for (Point x: path)
			System.out.println("(" + x.getX() + ", " + x.getY() + ")");
	}*/

}
