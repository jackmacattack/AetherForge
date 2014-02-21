package edu.virginia.cs.sgd.game;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.controller.Controller;
import edu.virginia.cs.sgd.game.controller.InputPlayer;
import edu.virginia.cs.sgd.game.controller.MapOperator;
import edu.virginia.cs.sgd.game.controller.Player;
import edu.virginia.cs.sgd.game.controller.RandomWalkPlayer;
import edu.virginia.cs.sgd.game.controller.TestPlayer;
import edu.virginia.cs.sgd.game.model.KillAllMap;
import edu.virginia.cs.sgd.game.model.Map;
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.util.Point;

public class Level {

	private Map map;
	private MapOperator o;
	
	private Controller c;
	
	public Level(TiledMap tileMap, RenderSystem renderer) {
		
		String[] arr = {"Human", "Enemy"};
		map = new KillAllMap(tileMap, renderer, arr);
		
		Player[] arr2 = {new InputPlayer("Human"), new TestPlayer("Enemy")};
		c = new Controller(arr2);

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
		map.dispose();
	}
	
	public boolean update() {
		int end = map.checkEnd();
		if(end != -1) {
			return true;
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
