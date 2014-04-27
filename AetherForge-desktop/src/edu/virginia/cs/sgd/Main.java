package edu.virginia.cs.sgd;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import edu.virginia.cs.sgd.util.Threader;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "AetherForge";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;

        Threader.getInstance().setExecutor(new DesktopExecutor());
		new LwjglApplication(new Entry(), cfg); 
	}
}
