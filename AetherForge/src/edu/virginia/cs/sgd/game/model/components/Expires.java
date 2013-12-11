package edu.virginia.cs.sgd.game.model.components;

import com.artemis.Component;

public class Expires extends Component {
    public int delay;
    
    public Expires(int delay) {
            this.delay = delay;
    }
    
    public Expires() {
            this(0);
    }
}
