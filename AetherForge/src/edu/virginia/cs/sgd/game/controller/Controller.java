package edu.virginia.cs.sgd.game.controller;	

import java.util.LinkedList;
import java.util.Queue;

import edu.virginia.cs.sgd.util.Point;

public class Controller {
	
	private Queue<Player> players;

	public Controller(Player[] p) {

		players = new LinkedList<Player>();

		for(int i = 0; i < p.length; i++) {
			players.add(p[i]);
		}

	}
	
	public Player getActivePlayer() {
		return players.peek();
	}
	public void startTurn(MapOperator map) {
		Player active = getActivePlayer();
		active.processTurn(map);

		System.out.println(getActivePlayer().getName());
	}

	public void endTurn() {
		Player active = players.poll();
		active.endTurn();
		players.add(active);
		
	}
	
	public boolean checkTurn() {
		Player active = getActivePlayer();
		
		return active.takingTurn();
	}

	public void onTouch(Point p) {
		Player active = getActivePlayer();
		
		if(active instanceof UserInput) {
			((UserInput) active).onTouch(p);
		}
		
	}

}
