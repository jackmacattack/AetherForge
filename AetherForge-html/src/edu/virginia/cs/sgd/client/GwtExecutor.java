package edu.virginia.cs.sgd.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

import edu.virginia.cs.sgd.util.Executor;

public class GwtExecutor implements Executor {

	@Override
	public void execute(final Runnable r) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				r.run();
				
			}
			
		});
		
	}

	@Override
	public boolean isExecuting() {
		// TODO Auto-generated method stub
		return false;
	}

}
