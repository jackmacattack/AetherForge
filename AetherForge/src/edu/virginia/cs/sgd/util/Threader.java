package edu.virginia.cs.sgd.util;

public class Threader {

	private Executor e;
	
	private static Threader instance;
	
	public static Threader getInstance() {
		if(instance == null) {
			instance = new Threader();
		}
		
		return instance;
	}
	
	public void setExecutor(Executor e_) {
		e = e_;
	}
	
	public void execute(Runnable r) {
		e.execute(r);
	}
	
	public boolean isExecuting() {
		return e.isExecuting();
	}
}
