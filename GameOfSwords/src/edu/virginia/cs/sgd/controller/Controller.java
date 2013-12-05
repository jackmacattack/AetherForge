package edu.virginia.cs.sgd.controller;	

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.utils.Array;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.model.EntityFactory;
import edu.virginia.cs.sgd.menu.MapScreen;

public class Controller {
	private TurnManagementSystem tms;
	private MovementSystem ms;
	private EntityFactory ef;
	private boolean playerTurn;
	private int activeEntity;
	
	public Array<Integer> units;
	public Array<Integer> enemies;
	public World zawarudo;
	public Level level;
	
	
	public Controller(MapScreen map, Level lv){
		units = new Array<Integer>();
		tms = new TurnManagementSystem(units);
		ms = new MovementSystem(map, lv);
		zawarudo = new World();
		ef = new EntityFactory();
		level = lv;
	}
	
	public void run(){
			System.out.println("Am I running?");
			int[][] map = new int[10][10];
			Entity e = ef.createActor(zawarudo, 0, 0, map);
			Entity e1 = ef.createActor(zawarudo, 0, 1, map);
			zawarudo.setSystem(tms);
			zawarudo.setSystem(ms);
			zawarudo.process();
			processTurn();
		
	}
	
	public void processTurn(){
		tms.process();
		activeEntity = units.get(0);
		ms.process(zawarudo.getEntity(activeEntity));
		
	}

}



