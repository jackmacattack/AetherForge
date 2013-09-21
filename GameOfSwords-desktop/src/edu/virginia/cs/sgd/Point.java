package edu.virginia.cs.sgd;

public class Point {
public double x;
public double y;
public Point(int x, int y)
{
this.x = x;
this.y = y;
}
public Point(double x, double y)
{
this.x = x;
this.y = y;
}
public Point(Point p)
{
this.x = p.x;
this.y = p.y;
}

public boolean equals(Point p)
{
	return this.x==p.x && this.y==p.y;
}

}
