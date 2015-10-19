package model.maths;

public class Vector3D {

	public float x, y, z;

	// Define by vector coordinates
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// Define by constant (useful in creating a zero vector)
	public Vector3D(float c) {
		this.x = c;
		this.y = c;
		this.z = c;
	}

	public Vector3D multiply(float scalar) {
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
