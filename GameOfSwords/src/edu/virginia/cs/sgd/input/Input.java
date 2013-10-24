package edu.virginia.cs.sgd.input;

public class Input {

	public static void keyDown(int keyCode){
		System.out.println("A key was pressed, its keycode was "+keyCode);
	}
	
	public static void keyUp(int keyCode){
		System.out.println("A key was released, its keycode was "+keyCode);
	}
	
	public static void touchDown(int screenX, int screenY){
		System.out.println("The mouse was pressed at x-coord: "+screenX);
		System.out.println("The mouse was pressed at y-coord: "+screenY);
	}
	
	public static void touchUp(int screenX, int screenY){
		System.out.println("The mouse was realeased at x-coord was: "+screenX);
		System.out.println("The mouse was realeased at y-coord was: "+screenY);
	}
	
}
