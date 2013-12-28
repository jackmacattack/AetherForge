package edu.virginia.cs.sgd.game;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.controller.Controller;
import edu.virginia.cs.sgd.game.controller.Player;
import edu.virginia.cs.sgd.game.controller.TestPlayer;
import edu.virginia.cs.sgd.game.model.Map;
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.util.Point;

public class Level {

	private Map map;
	private Controller c;
	
	public Level(TiledMap tileMap, RenderSystem renderer) {
		
		map = new Map(tileMap, renderer);
		
		Player[] arr = {new TestPlayer("Fred")};
		c = new Controller(arr);

	}

	public void initialize() {
		map.initialize();
		c.startTurn(map);
	}
	
	public void endTurn() {
		
		System.out.println(c.getActivePlayer().getName());
		c.startTurn(map);
		
	}
	
	public void onTouch(Point p) {
		c.onTouch(p);
	}
	
	public void dispose() {
		map.dispose();
	}
	
	public void update() {
		map.update();
	}
}
