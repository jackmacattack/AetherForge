package edu.virginia.cs.sgd.game;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.controller.Controller;
import edu.virginia.cs.sgd.game.controller.InputPlayer;
import edu.virginia.cs.sgd.game.controller.MapOperator;
import edu.virginia.cs.sgd.game.controller.Player;
import edu.virginia.cs.sgd.game.controller.RandomWalkPlayer;
import edu.virginia.cs.sgd.game.model.Map;
import edu.virginia.cs.sgd.game.view.RenderSystem;
import edu.virginia.cs.sgd.util.Point;

public class Level {

	private Map map;
	private MapOperator o;
	
	private Controller c;
	
	public Level(TiledMap tileMap, RenderSystem renderer) {
		
		map = new Map(tileMap, renderer);
		
		Player[] arr = {new InputPlayer("Human"), new RandomWalkPlayer("Enemy")};
		c = new Controller(arr);

	}

	public void initialize() {
		map.initialize();

		o = new MapOperator(map, c.getActivePlayer().getName());
		c.startTurn(o);
	}
	
	public void onTouch(Point p) {
		c.onTouch(p);
	}
	
	public void dispose() {
		map.dispose();
	}
	
	public void update() {
		if(!c.checkTurn() || !o.checkTurn()) {
			c.endTurn();
			o = new MapOperator(map, c.getActivePlayer().getName());
			c.startTurn(o);
		}
		map.update();
	}

	public MapOperator getCurrentOperator() {
		return o;
	}
}
