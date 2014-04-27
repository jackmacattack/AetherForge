package edu.virginia.cs.sgd.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import edu.virginia.cs.sgd.Entry;
import edu.virginia.cs.sgd.util.Threader;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(480, 320);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
        Threader.getInstance().setExecutor(new GwtExecutor());
		return new Entry();
	}
}