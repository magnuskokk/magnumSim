package model.maths;

public class Vector3D {

	public double x, y, z;
	public Point3D begin, end;
	
	// Define by begin and end points
	public Vector3D(Point3D begin, Point3D end) {
		this.begin = begin;
		this.end = end;
		
		this.x = end.x - begin.x;
		this.y = end.y - begin.y;
		this.z = end.z - begin.z;
	}

	// Define by vector coordinates
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		begin = null;
		end = null;
	}

	public void multiply(double mul) {
		x *= mul;
		y *= mul;
		z *= mul;
		
		if (begin != null && end != null) {
			begin.multiply(mul);
			end.multiply(mul);
		}
	}
	
	public void add(Vector3D vector) {
		
		
	}
	
	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
}
