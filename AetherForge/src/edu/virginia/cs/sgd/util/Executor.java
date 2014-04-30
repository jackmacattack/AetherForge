package edu.virginia.cs.sgd.util;

public interface Executor {

	void execute(Runnable r);

	boolean isExecuting();
}
