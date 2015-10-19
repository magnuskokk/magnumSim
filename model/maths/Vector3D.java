package model.maths;

public class Vector3D {

	public float x, y, z;
	private Vector3D vector;

	// Define by vector coordinates
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// Zero vector
	public Vector3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	/**
	 * Copy constructor
	 */
	public Vector3D(Vector3D vector) {
		this(vector.x, vector.y, vector.z);
	}

	public Vector3D multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;

		return this;
	}
	
	public Vector3D divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		this.z /= scalar;

		return this;
	}

	public Vector3D add(Vector3D vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;

		return this;
	}

	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
}
