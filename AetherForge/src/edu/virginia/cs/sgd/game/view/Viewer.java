package edu.virginia.cs.sgd.game.view;

import java.util.ArrayList;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Mapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.controller.MapOperator;
import edu.virginia.cs.sgd.game.model.Selection;
import edu.virginia.cs.sgd.game.model.components.MapPosition;
import edu.virginia.cs.sgd.util.Point;


public class Viewer {

	@Mapper
	ComponentMapper<MapPosition> mapper;

	private float zoomMin;
	private float zoomMax;
	private float zoomDelta;

	private int width;
	private int height;

	private OrthographicCamera camera;

	private RenderSystem renderer;

	public Viewer(int width, int height, RenderSystem renderer) {

		this.width = width;
		this.height = height;
		this.renderer = renderer;

		zoomMin = .2f;
		zoomMax = 2f;
		zoomDelta = .2f;

		camera = new OrthographicCamera();

	}

	private void updateCamera() {
		camera.update();
		renderer.renderMap(camera);
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

	public void renderUI(Level level) {
		MapOperator o = level.getCurrentOperator();
		SpriteBatch batch = renderer.getSpriteBatch();
		Skin paneSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		ScrollPane pane = new ScrollPane(new Actor(), paneSkin);
		//OutputNinePatch nine = OutputNinePatch.getInstance();
		// Create a new TextButtonStyle
	//	TextButtonStyle style = new TextButton.TextButtonStyle();
		// Instantiate the Button itself.
		//TextButton button = new TextButton("hello world", null);
		batch.begin();
		//nine.draw(batch, 250, 0, 500, 100);
		//pane.draw(batch, 1);
		if(o != null) {
			Selection sel = o.getSelection();

			if(sel != null) {
				ArrayList<Point> highlightPos = sel.getSelPos();
				ArrayList<SelectionType> highlightType = sel.getSelType();

				//batch.begin();
				//BitmapFont font = new BitmapFont(Gdx.files.internal("skins/default.fnt"), Gdx.files.internal("skin/default.png"),false);
//				BitmapFont font = new BitmapFont();
//				CharSequence output = "Selected Warrior";
//				//batch.begin();
//				font.setColor(0, 0, 0, 1);
//				font.draw(batch, output, 400, 100);
//				nine.draw(batch, 0, 0, Gdx.graphics.getWidth(), 100);
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

				//batch.end();
			}
		}
		batch.end();
	}

	public void resize(int width, int height) {

		float scale = renderer.getScale();
		camera.setToOrtho(false, width * scale, height * scale);

		updateCamera();

	}

	public Point getCoord(int screenX, int screenY) {

		float scale = renderer.getScale();
		Vector3 pos = new Vector3(screenX, screenY, 0);
		camera.unproject(pos);

		int x = (int)(pos.x * scale / width);
		int y = (int)(pos.y * scale / height);

		return new Point(x, y);
	}

	public void zoomMap(boolean in) {
		float zoom = 0;

		if(in) {
			zoom = camera.zoom * (1 + zoomDelta);
		}
		else {
			zoom = camera.zoom * (1 - zoomDelta);
		}

		if(zoom > zoomMin && zoom < zoomMax) {
			camera.zoom = zoom;
		}

		updateCamera();

	}

	public void moveMap(int deltaX, int deltaY) {

		Vector3 delta = new Vector3(-deltaX * camera.zoom, deltaY * camera.zoom, 0);

		camera.translate(delta);

		updateCamera();
	}

}
