package edu.virginia.cs.sgd.game.model.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite extends Component {

	public TextureRegion region;
	public String name;
	public float r, g, b, a, scaleX, scaleY, rotation;
	public int x, y, width, height;
	public boolean changed = false;
	public Sprite(String name) {
		this.name = name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
