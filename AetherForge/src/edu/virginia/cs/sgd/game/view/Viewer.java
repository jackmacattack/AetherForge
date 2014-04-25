package edu.virginia.cs.sgd.game.view;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.game.controller.MapOperator;
import edu.virginia.cs.sgd.game.model.Selection;
import edu.virginia.cs.sgd.util.Point;


public class Viewer {

	private int screenWidth;
	private int screenHeight;
	
	private float zoomMin;
	private float zoomMax;
	private float zoomDelta;

	private int width;
	private int height;

	private OrthographicCamera camera;

	private RenderSystem renderer;

	private SpriteBatch uiBatch;
	
	public Viewer(int screenWidth, int screenHeight, int width, int height, RenderSystem renderer) {

		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.width = width;
		this.height = height;
		this.renderer = renderer;

		zoomMin = .2f;
		zoomMax = 2f;
		zoomDelta = .2f;

		camera = new OrthographicCamera();

		uiBatch = new SpriteBatch();
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

	public void drawTiles(Selection sel) {

		ArrayList<Point> highlightPos = sel.getSelPos();
		ArrayList<SelectionType> highlightType = sel.getSelType();

		SpriteBatch batch = renderer.getSpriteBatch();
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
	
	public void drawProfile(String profile, float health, float mana) {

		int canvasWidth = 150;
		int canvasHeight = 74;
		int canvasSpacing = 20;
		int spacing = 5;
		
		int headWidth = 64;
		int headHeight = 64;
		int headX = canvasSpacing + spacing;
		int headY = screenHeight - headHeight - canvasSpacing - spacing;
		
		int barWidth = 70;
		int barHeight = 15;
		int barX = canvasWidth + canvasSpacing - barWidth - spacing;
		int barY = screenHeight - canvasSpacing - barHeight - spacing;
		int mBarY = barY - barHeight - spacing;
		
		Pixmap p = new Pixmap(canvasWidth, canvasHeight, Pixmap.Format.RGBA8888);
		p.setColor(0, 0, 0, .5f);
		p.fill();

		Texture bg = new Texture(p);
		
		p = new Pixmap(headWidth, headHeight, Pixmap.Format.RGBA8888);
		p.setColor(0, 0, 0, 1);
		p.fill();

		Texture headShot = new Texture(p);
		
		p = new Pixmap(barWidth, barHeight, Pixmap.Format.RGBA8888);
		p.setColor(1, 1, 1, .5f);
		p.fill();

		Texture healthBase = new Texture(p);

		p = new Pixmap((int) (barWidth * health), barHeight, Pixmap.Format.RGBA8888);
		p.setColor(1, 0, 0, 1f);
		p.fill();

		Texture healthLeft = new Texture(p);
		
		p = new Pixmap(barWidth, barHeight, Pixmap.Format.RGBA8888);
		p.setColor(1, 1, 1, .5f);
		p.fill();

		Texture manaBase = new Texture(p);

		p = new Pixmap((int) (barWidth * mana), barHeight, Pixmap.Format.RGBA8888);
		p.setColor(0, 0, 1, 1f);
		p.fill();

		Texture manaLeft = new Texture(p);
		
		uiBatch.begin();
		uiBatch.draw(bg, canvasSpacing, screenHeight - canvasHeight - canvasSpacing);
		uiBatch.draw(headShot, headX, headY);
		uiBatch.draw(healthBase, barX, barY);
		uiBatch.draw(healthLeft, barX, barY);
		uiBatch.draw(manaBase, barX, mBarY);
		uiBatch.draw(manaLeft, barX, mBarY);
		uiBatch.end();	
	}
	
	public void renderUI(Level level) {
		MapOperator o = level.getCurrentOperator();

		if(o != null) {
			Selection sel = o.getSelection();

			if(sel != null) {
				drawTiles(sel);
				
				drawProfile(sel.getTex(), sel.getHealth(), sel.getMana());
			}
		}

		
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
