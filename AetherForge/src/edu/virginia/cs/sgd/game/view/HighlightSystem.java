package edu.virginia.cs.sgd.game.view;

import java.util.ArrayList;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.game.model.components.Selection;


public class HighlightSystem extends EntityProcessingSystem {

	@Mapper
	ComponentMapper<Selection> mapper;

	private int size;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public HighlightSystem(int size, SpriteBatch batch) {
		super(Aspect.getAspectForAll(Selection.class));
		
		this.size = size;
		this.batch = batch;
	}

	private Color getColor(HighlightType type) {
		switch(type) {
		case MOVE:
			return new Color(0, 0, 1, .5f);
		case ATTACK:
			return new Color(1, 0, 0, .5f);
		default:
			return new Color(0, 0, 0, 1);
		}
	}

	@Override
	protected void process(Entity e) {

		Selection sel = e.getComponent(Selection.class);//mapper.get(e);

		ArrayList<MapPosition> highlightPos = sel.getHighlightPos();
		ArrayList<HighlightType> highlightType = sel.getHighlightType();

		batch.begin();
		
		for(int i = 0; i < highlightPos.size(); i++) {

			MapPosition pos = highlightPos.get(i);
			HighlightType type = highlightType.get(i);
			
			Pixmap p = new Pixmap(size, size, Pixmap.Format.RGBA8888);
			p.setColor(getColor(type));
			p.fill();
			p.setColor(0, 0, 0, .5f);
			p.drawRectangle(0, 0, size, size);

			Texture tex = new Texture(p);

			batch.draw(tex, (float) pos.getX() * size, (float) pos.getY() * size);
		}
		
		batch.end();
	}

}
