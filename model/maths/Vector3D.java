package model.maths;

public class Vector3D {

	public float x, y, z;

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

	public boolean isNullVector() {
		return (this.x == 0 && this.y == 0 && this.z == 0) ? true : false;
	}

	public boolean isParallelTo(Vector3D vector) {
		float Mx = this.y * vector.z - this.z * vector.y;
		float My = this.z * vector.x - this.x * vector.z;
		float Mz = this.x * vector.y - this.y * vector.x;

		Vector3D vektorKorrutis = new Vector3D(Mx, My, Mz);

		return vektorKorrutis.isNullVector() ? true : false;
	}

	public float getAngleCosTo(Vector3D vector) {
		float angleCos = (this.x * vector.x + this.y * vector.y + this.z + vector.z)
				/ (this.length() * vector.length());

		return angleCos;
	}
	
	public Vector3D getUnitVector() {
		Vector3D vector = new Vector3D(this);
		
		return vector.divide(vector.length());
	}

	public float length() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
}
