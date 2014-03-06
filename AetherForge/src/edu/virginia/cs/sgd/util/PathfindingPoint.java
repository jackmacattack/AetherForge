package edu.virginia.cs.sgd.util;

public class PathfindingPoint extends Point {
	
	PathfindingPoint parent;
	double past;
	double future;
	
	public PathfindingPoint(int x, int y) {
		super(x, y);
		this.past = 0.0;
		this.future = 0.0;
		this.parent = null;
	}

	public PathfindingPoint(int x, int y, double past) {
		super(x, y);
		this.past = past;
		this.future = 0.0;
		this.parent = null;
	}

	public double getFuture() {
		return future;
	}

	public void setFuture(double future) {
		this.future = future;
	}

	public PathfindingPoint getParent() {
		return parent;
	}

	public void setParent(PathfindingPoint parent) {
		this.parent = parent;
	}

	public double getPast() {
		return past;
	}

	public void setPast(double past) {
		this.past = past;
	}
	
}
