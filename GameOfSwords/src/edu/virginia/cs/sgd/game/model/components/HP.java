/**
 * 
 */
package edu.virginia.cs.sgd.game.model.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author Mitchell
 *
 */
public class HP extends Component {
private int hp, max_hp;

public HP()
{
	setHP(MathUtils.random(100,200));
	setMax_HP(getHP());
}

/**
 * @return the hp
 */
public int getHP() {
	return hp;
}

/**
 * @param hp the hp to set
 */
public void setHP(int hp) {
	this.hp = hp;
}

/**
 * @return the max_hp
 */
public int getMax_HP() {
	return max_hp;
}

/**
 * @param max_hp the max_hp to set
 */
public void setMax_HP(int max_hp) {
	this.max_hp = max_hp;
}



}
