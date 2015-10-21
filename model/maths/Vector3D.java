package model.maths;

public class Vector3D {

	public float x, y, z;

	// Define by vector coordinates
	public Vector3D(float d, float e, float f) {
		this.x = d;
		this.y = e;
		this.z = f;
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

	public Vector3D multiply(double scalar) {
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

	public Vector3D subtract(Vector3D vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;

		return this;
	}

	public float skalaarKorrutis(Vector3D vector) {
		float skalaarKorrutis = this.x * vector.x + this.y * vector.y + this.z * vector.z;

		return skalaarKorrutis;
	}

	public Vector3D vektorKorrutis(Vector3D vector) {
		float Mx = this.y * vector.z - this.z * vector.y;
		float My = this.z * vector.x - this.x * vector.z;
		float Mz = this.x * vector.y - this.y * vector.x;

		Vector3D vektorKorrutis = new Vector3D(Mx, My, Mz);

		return vektorKorrutis;
	}

	public boolean isNullVector() {
		return (this.x == 0 && this.y == 0 && this.z == 0) ? true : false;
	}

	public boolean isParallelTo(Vector3D vector) {
		Vector3D copyVector = new Vector3D(this);
		Vector3D vektorKorrutis = copyVector.vektorKorrutis(vector);

		return vektorKorrutis.isNullVector() ? true : false;
	}

	public float getAngleCosTo(Vector3D vector) {
		Vector3D copyVector = new Vector3D(this);

		float angleCos = copyVector.skalaarKorrutis(vector) / (copyVector.length() * vector.length());

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
