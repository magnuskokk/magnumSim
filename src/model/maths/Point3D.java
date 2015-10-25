package model.maths;

public class Point3D {

	public float x, y, z;

	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	/**
	 * Copy constructor
	 */
	public Point3D(Point3D point) {
		this(point.x, point.y, point.z);
	}


	public Point3D multiply(float mul) {
		this.x *= mul;
		this.y *= mul;
		this.z *= mul;

		return this;
	}
	
	public Point3D divide(float mul) {
		this.x /= mul;
		this.y /= mul;
		this.z /= mul;

		return this;
	}

	public Point3D add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;

		return this;
	}
	
	public Point3D add(Point3D point) {
		this.x += point.x;
		this.y += point.y;
		this.z += point.z;

		return this;
	}
	
	public Point3D add(Vector3D vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;

		return this;
	}

	public Point3D subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;

		return this;
	}
}
