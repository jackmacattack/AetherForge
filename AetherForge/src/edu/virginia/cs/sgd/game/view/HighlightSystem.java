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

import edu.virginia.cs.sgd.game.model.components.Selection;
import edu.virginia.cs.sgd.util.Point;


public class HighlightSystem extends EntityProcessingSystem {

	@Mapper
	ComponentMapper<Selection> mapper;

	private int width;
	private int height;
	private SpriteBatch batch;

	@SuppressWarnings("unchecked")
	public HighlightSystem(int width, int height, SpriteBatch batch) {
		super(Aspect.getAspectForAll(Selection.class));

		this.width = width;
		this.height = height;

		this.batch = batch;
	}

	private Color getColor(SelectionType type) {
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

		if(sel != null) {
			ArrayList<Point> highlightPos = sel.getSelPos();
			ArrayList<SelectionType> highlightType = sel.getSelType();

			batch.begin();

			for(int i = 0; i < highlightPos.size(); i++) {

				Point pos = highlightPos.get(i);
				SelectionType type = highlightType.get(i);

				Pixmap p = new Pixmap(width, height, Pixmap.Format.RGBA8888);
				p.setColor(getColor(type));
				p.fill();
				p.setColor(0, 0, 0, .5f);
				p.drawRectangle(0, 0, width, height);

				Texture tex = new Texture(p);

				batch.draw(tex, (float) pos.getX() * width, (float) pos.getY() * height);
			}

			batch.end();
		}
	}

}
