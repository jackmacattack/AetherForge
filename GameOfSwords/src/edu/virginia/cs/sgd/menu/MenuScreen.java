package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.virginia.cs.sgd.GameOfSwords;



public class MenuScreen extends AbstractScreen {

	private static final float BUTTON_WIDTH = 300f;
	private static final float BUTTON_HEIGHT = 60f;
	private static final float BUTTON_SPACING = 10f;

	public MenuScreen(GameOfSwords game) {
		super(game);
	}
	
	@Override
	public void show() {
		super.show();
		
		// retrieve the default table actor aa
        Table table = super.getTable();
        table.add( "Welcome to GameOfSwords (WIP)" ).spaceBottom( 50 );
        table.row();
        
        //start game
        TextButton startGameButton = new TextButton( "Start game", getSkin() );
        startGameButton.addListener( new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
            	game.setScreen( game.getMapScreen() );
            }
        } );
        
        table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();

        // register the button "options"
        TextButton optionsButton = new TextButton( "Options", getSkin() );
        table.add( optionsButton ).uniform().fill().spaceBottom( 10 );
        table.row();
	}

}
