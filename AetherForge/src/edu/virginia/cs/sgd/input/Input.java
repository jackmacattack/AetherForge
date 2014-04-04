package edu.virginia.cs.sgd.input;

import com.badlogic.gdx.Gdx;

public class Input {
	
	private InputListener listener;
	private MyInputProcessor a;
	
	private boolean dragging = false;
	
	private int prevX;
	private int prevY;
	
	public Input(){
		a = new MyInputProcessor(this);
		Gdx.input.setInputProcessor(a);
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

		if(listener != null) {
			listener.scrolled(amount);
		}
	}

	public void touchDragged(int screenX, int screenY, int pointer) {

		int deltaX = screenX - prevX;
		int deltaY = screenY - prevY;
		
		prevX = screenX;
		prevY = screenY;
		
		dragging = true;
		
		if(listener != null) {
			listener.touchDragged(screenX, screenY, pointer, deltaX, deltaY);
		}
	}
}
