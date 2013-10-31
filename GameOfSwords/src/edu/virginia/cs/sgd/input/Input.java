package edu.virginia.cs.sgd.input;

import com.badlogic.gdx.Gdx;

public class Input {
	
	private MyInputProcessor a;
	
	public Input(){
		a = new MyInputProcessor(this);
		Gdx.input.setInputProcessor(a);
	}

	public void keyDown(int keyCode){
		System.out.println("A key was pressed, its keycode was "+keyCode);
	}
	
	public void keyUp(int keyCode){
		System.out.println("A key was released, its keycode was "+keyCode);
	}
	
	public void touchDown(int screenX, int screenY){
		System.out.println("The mouse was pressed at x-coord: "+screenX);
		System.out.println("The mouse was pressed at y-coord: "+screenY);
	}
	
	public void touchUp(int screenX, int screenY){
		System.out.println("The mouse was realeased at x-coord: "+screenX);
		System.out.println("The mouse was realeased at y-coord: "+screenY);
	}
	
	public void keyTyped(char character){
		System.out.println("The key "+character+" was pressed.");
	}
	
	public void mouseMoved(int screenX, int screenY) {
		System.out.println("The mouse was moved: "+screenX+", "+screenY);
	}
	
	public void scrolled(int amount) {
		System.out.println("Mouse wheel scroll amount: "+amount);
	}
}
