package edu.virginia.cs.sgd.game.controller;	

import java.util.LinkedList;
import java.util.Queue;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.util.Point;

public class Controller {
	
	private Queue<Player> players;

	public Controller(Player[] p) {

		players = new LinkedList<Player>();

		for(int i = 0; i < p.length; i++) {
			players.add(p[i]);
		}

//		active = players[0];

	}
	
	public Player getActivePlayer() {
		return players.peek();
	}
	public void startTurn(Level level) {
		Player active = getActivePlayer();
		active.processTurn(level, this);
	}

	public void endTurn(Level level) {
		Player p = players.poll();
		players.add(p);
		
		level.endTurn();
	}

	public void onTouch(Point p) {
		
		if(getActivePlayer() instanceof TestPlayer) {
			((TestPlayer) getActivePlayer()).select(p);
		}
		
	}

}
