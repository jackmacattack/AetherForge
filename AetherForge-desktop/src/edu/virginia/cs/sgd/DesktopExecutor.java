package edu.virginia.cs.sgd;

import edu.virginia.cs.sgd.util.Executor;

public class DesktopExecutor implements Executor {

	private Thread t;
	
	@Override
	public void execute(final Runnable r) {
		t = new Thread(r);
		
		t.start();

	}

	@Override
	public boolean isExecuting() {
		return t.isAlive();
	}

}
