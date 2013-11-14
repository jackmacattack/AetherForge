package edu.virginia.cs.sgd.game.model.components;

import com.artemis.Component;

public class Passable extends Component {
	boolean pass = true;
	public Passable(){
		;
	}
	public Passable(boolean pass){
		this.pass = pass;
	}
	
	public boolean isPassable()
	{
		return pass;
	}
	public boolean isImpassable()
	{
		return pass == false;
	}

}
