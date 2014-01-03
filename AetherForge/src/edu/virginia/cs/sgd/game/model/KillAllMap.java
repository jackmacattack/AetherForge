package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.artemis.Entity;
import com.artemis.managers.PlayerManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.view.RenderSystem;

public class KillAllMap extends Map {

	private HashMap<String, Integer> index;
	private List<String> players;
	
	public KillAllMap(TiledMap map, RenderSystem renderer, String[] players) {
		super(map, renderer);
		
		index = new HashMap<String, Integer>();
		this.players = new ArrayList<String>();
		for(int i = 0; i < players.length; i++) {
			this.players.add(players[i]);
			this.index.put(players[i], i);
		}
	}

	@Override
	public int checkEnd() {
		PlayerManager teams = world.getManager(PlayerManager.class);
		
		for(int i = 0; i < players.size(); i++) {
			String player = players.get(i);
			ImmutableBag<Entity> units = teams.getEntitiesOfPlayer(player);
			
			if(units.size() == 0) {
				players.remove(i);
				i--;
			}
		}
		
		if(players.size() == 0) {
			return -2;
		}
		else if(players.size() == 1) {
			return index.get(players.get(0));
		}
		else {
			return -1;
		}
	}

}
