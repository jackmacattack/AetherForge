package edu.virginia.cs.sgd.game.controller;	

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;

import edu.virginia.cs.sgd.util.Point;

public class Controller {
	
	private Queue<Player> players;

	public Controller(Player[] p) {

		players = new LinkedList<Player>();

		for(int i = 0; i < p.length; i++) {
			players.add(p[i]);
		}

	}
	
	public String getActivePlayer() {
		Player active = players.peek();
		return active.getName();
	}
	public void startTurn(MapOperator map) {
		Player active = players.peek();
		active.processTurn(map);
	}

	public void endTurn() {
		Player active = players.poll();
		active.endTurn();
		players.add(active);
		
	}
	
	public boolean checkTurn() {
		Player active = players.peek();
		
		return active.takingTurn();
	}

	public void onTouch(Point p) {
		Player active = players.peek();
		
		if(active instanceof UserInput) {
			((UserInput) active).onTouch(p);
		}
		
	}

}
