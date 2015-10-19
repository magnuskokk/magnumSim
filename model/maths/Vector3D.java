package model.maths;

public class Vector3D {

	public double x, y, z;

	// Define by vector coordinates
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3D multiply(double scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		
		return this;
	}
	
	public Vector3D add(Vector3D vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		
		return this;
	}
	
	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
}
