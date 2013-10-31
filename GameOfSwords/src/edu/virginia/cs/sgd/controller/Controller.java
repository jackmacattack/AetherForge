package edu.virginia.cs.sgd.controller;	

import com.badlogic.gdx.utils.Array;

import edu.virginia.cs.sgd.menu.MapScreen;

public class Controller {
	private TurnManagementSystem tms;
	public Array<Integer> units;
	
	public Controller(MapScreen map){
		units = new Array<Integer>();
		tms = new TurnManagementSystem(units);
	}
	
	public void processTurn(){
		tms.process();
		
	}
	

}
