package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.InputAdapter;


public class MenuScreen extends AbstractScreen {

	private static final float BUTTON_WIDTH = 300f;
	private static final float BUTTON_HEIGHT = 60f;
	private static final float BUTTON_SPACING = 10f;

	public MenuScreen(GameOfSwords game) {
		super(game);
	}

	@Override
	public void resize(int width, int height) {
		final float buttonX = (width - BUTTON_WIDTH) / 2;
		float currentY = 280f;

	}

	@Override
	public void show() {
		super.show();
		// retrieve the default table actor
		Table table = super.getTable();
        table.add( "Welcome to Sword Art Offline" ).spaceBottom( 50 );
        table.row();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
