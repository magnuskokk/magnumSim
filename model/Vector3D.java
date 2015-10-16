package model;

public class Vector3D {

	public double x, y, z;
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void multiply(double mul) {
		this.x *= mul;
		this.y *= mul;
		this.z *= mul;
	}
}
