package edu.virginia.cs.sgd.util;


public class StateMachine {

	private int[][] table;
	private int startState;
	private int state;
	
	public StateMachine(int[][] table, int startState) {
		this.table = table;
		this.startState = startState;
		this.state = startState;
	}
	
	public int getState() {
		return state;
	}
	
	public void transition(int i) {
		state = table[state][i];
	}
	
	public void reset() {
		state = startState;
	}
}
