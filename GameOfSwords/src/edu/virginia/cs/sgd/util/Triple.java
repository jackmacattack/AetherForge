package edu.virginia.cs.sgd.util;

public class Triple {
	private int mvn;
	private int x;
	private int y;
	
	public int getMvn() {
		return mvn;
	}

	public void setMvn(int mvn) {
		this.mvn = mvn;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isAdjacent(Triple t){
		if(this.x == t.getX()-1 && this.y == t.getY()) return true;
		if(this.x == t.getX()+1 && this.y == t.getY()) return true;
		if(this.x == t.getX() && this.y == t.getY()-1) return true;
		if(this.x == t.getX() && this.y == t.getY()+1) return true;
		return false;
	}

	public Triple(int mvn, int x, int y){
		this.mvn = mvn;
		this.x = x;
		this.y = y;
		
	}
	
	public String toString(){
		return "(" + mvn + "," + x + "," + y + ")";
	}

	public boolean equals(Object e){
		if(e instanceof Triple)
		if(this.x == ((Triple) e).getX() && this.y == ((Triple) e).getY()) return true;
		return false;
	}
}
