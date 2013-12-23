package edu.virginia.cs.sgd.game.model.components;

import com.artemis.Component;

public class TextureName extends Component {

	private String name;

	public TextureName(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
