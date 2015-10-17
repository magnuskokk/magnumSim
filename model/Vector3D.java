package model;

public class Vector3D {

	public double x, y, z;
	public Point3D begin, end;

	// Define by begin and end points
	public Vector3D(Point3D begin, Point3D end) {
		this.begin = begin;
		this.end = end;
	}

	public void multiply(double mul) {
		this.begin.multiply(mul);
		this.end.multiply(mul);
	}
}
