package edu.virginia.cs.sgd.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;

public class Input {

	private InputListener listener;

	private boolean dragging = false;

	private int prevX;
	private int prevY;

	public Input(){

		InputProcessor p = null;
		switch(Gdx.app.getType()) {
		case Android:
			p = new GestureDetector(new AndroidInputProcessor(this));
			break;
		case Desktop:
			p = new MyInputProcessor(this);
			break;
		case WebGL:
			p = new MyInputProcessor(this);
			break;
		case Applet:
			break;
		case iOS:
			break;
		default:
			break;
		}
		Gdx.input.setInputProcessor(p);

	}

	public void setListener(InputListener listener) {
		this.listener = listener;
	}

	public void keyDown(int keyCode){

		if(listener != null) {
			listener.keyDown(keyCode);
		}
	}

	public void keyUp(int keyCode){

		if(listener != null) {
			listener.keyUp(keyCode);
		}
	}

	public void touchDown(int screenX, int screenY, int pointer, int button){

		prevX = screenX;
		prevY = screenY;
		
		if(listener != null) {
			listener.touchDown(screenX, screenY, pointer, button);
		}
	}

	public void touchUp(int screenX, int screenY, int pointer, int button){

		if(listener != null) {
			listener.touchUp(screenX, screenY, pointer, button, dragging);
		}

		dragging = false;
	}

	public void mouseMoved(int screenX, int screenY) {

		int deltaX = screenX - prevX;
		int deltaY = screenY - prevY;

		prevX = screenX;
		prevY = screenY;

		if(listener != null) {
			listener.mouseMoved(screenX, screenY, deltaX, deltaY);
		}
	}

	public void scrolled(int amount) {
		zoom(1f + .2f * amount);
	}

	public void touchDragged(int screenX, int screenY, int pointer) {

		int deltaX = screenX - prevX;
		int deltaY = screenY - prevY;

		prevX = screenX;
		prevY = screenY;

		dragging = true;

		listener.touchDragged(screenX, screenY, pointer, deltaX, deltaY);
	}

	public void touchDragged(int screenX, int screenY, int pointer, int deltaX, int deltaY) {

		if(listener != null) {
			listener.touchDragged(screenX, screenY, pointer, deltaX, deltaY);
		}
	}
	
	public void zoom(float percent) {

		if(listener != null) {
			listener.zoom(percent);
		}
	}
}
