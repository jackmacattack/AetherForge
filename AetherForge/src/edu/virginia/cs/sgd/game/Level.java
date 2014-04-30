package edu.virginia.cs.sgd.game;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.controller.AttackNearestPlayer;
import edu.virginia.cs.sgd.game.controller.Controller;
import edu.virginia.cs.sgd.game.controller.InputPlayer;
import edu.virginia.cs.sgd.game.controller.MapOperator;
import edu.virginia.cs.sgd.game.controller.Player;
import edu.virginia.cs.sgd.game.model.KillAllMap;
import edu.virginia.cs.sgd.game.model.Map;
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.util.Point;

public class Level {

	private Queue<Map> futureMaps;
	private RenderSystem renderer;
	
	private Map map;
	private MapOperator o;

	private Controller c;

	public Level(TiledMap[] tileMap, RenderSystem renderer) {

		this.renderer = renderer;
		
		String[] arr = {"Human", "Enemy"};
		futureMaps = new LinkedList<Map>();

		for(int i = 0; i < tileMap.length; i++) {
			futureMaps.add(new KillAllMap(tileMap[i], renderer, arr));
		}
		
		newMap();
	}
	
	private boolean newMap() {
		map = futureMaps.poll();
		
		if(map == null) {
			return true;
		}
		
		String[] h = {"Human"};
		String[] e = {"Enemy"};

		Player[] arr2 = {new InputPlayer("Human", e), new AttackNearestPlayer("Enemy", h)};
		c = new Controller(arr2);

		renderer.setMap(map.getMap(), 1f);
		
		return false;
	}

	private void startTurn() {

		o = new MapOperator(map, c.getActivePlayer());
		c.startTurn(o);

	}

	public void initialize() {
		map.initialize();
		startTurn();
	}

	public void onTouch(Point p) {
		c.onTouch(p);
	}

	public void dispose() {
		if(map != null) {
			map.dispose();
		}
	}

	public boolean update() {
		int end = map.checkEnd();
		if(end != -1) {

//			if(end == 0) {
				if(newMap()) {
					return true;
				}
				else {
					initialize();
					return false;
				}
//			}
//			else {
//				return true;
//			}
		}

		if(!c.checkTurn() || !o.checkTurn()) {
			c.endTurn();
			startTurn();
		}
		map.update();

		return false;
	}

	public MapOperator getCurrentOperator() {
		return o;
	}
}
