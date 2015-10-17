package model.maths3d;

public class Vector3D {

	public Point3D begin, end;
	
	// Define by begin and end points
	public Vector3D(Point3D begin, Point3D end) {
		this.begin = begin;
		this.end = end;
	}

	public void multiply(double mul) {
		begin.multiply(mul);
		end.multiply(mul);
	}
	
	public double length() {
		return Math.sqrt(Math.pow(end.x - begin.x, 2) + Math.pow(end.y - begin.y, 2) + Math.pow(end.z - begin.z, 2));
	}
}
