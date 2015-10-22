package model.maths;

public class Vector3D {

	public float x, y, z;

	// Define by vector coordinates
	public Vector3D(float d, float e, float f) {
		this.x = d;
		this.y = e;
		this.z = f;
	}
	
	// Define by end and begin points
	public Vector3D(Point3D end, Point3D begin) {
		this.x = end.x - begin.x;
		this.y = end.y - begin.y;
		this.z = end.z - begin.z;
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
	
	public Point3D getAsPoint() {
		return new Point3D(this.x, this.y, this.x);
	}

	public float skalaarKorrutis(Vector3D vector) {
		float skalaarKorrutis = this.x * vector.x + this.y * vector.y + this.z * vector.z;

		return skalaarKorrutis;
	}

	public Vector3D vektorKorrutis(Vector3D vector) {
		float Mx = this.y * vector.z - this.z * vector.y;
		float My = this.z * vector.x - this.x * vector.z;
		float Mz = this.x * vector.y - this.y * vector.x;

		this.x = Mx;
		this.y = My;
		this.z = Mz;

		return this;
	}

	public boolean isNull() {
		return (this.x == 0 && this.y == 0 && this.z == 0) ? true : false;
	}
	
	public boolean isPerpTo(Vector3D vector) {
		Vector3D copyVector = new Vector3D(this);
		return copyVector.skalaarKorrutis(vector) == 0 ? true : false;
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

	public Vector3D rotateAroundAxis(Vector3D axis, double angle) {

		// Rodrigues' rotation formula
		// https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
		Vector3D copyVector = new Vector3D(this);
		Vector3D liidetav1 = copyVector.multiply(Math.cos(angle));

		copyVector = new Vector3D(this);
		Vector3D liidetav2 = copyVector.vektorKorrutis(axis);
		liidetav2.multiply(Math.sin(angle));

		copyVector = new Vector3D(this);
		Vector3D copyAxisVector = new Vector3D(axis);

		float skalaarKorrutis = copyVector.skalaarKorrutis(axis);
		Vector3D liidetav3 = copyAxisVector.multiply(skalaarKorrutis * (1 - Math.cos(angle)));

		liidetav1.add(liidetav2).add(liidetav3);

		// TODO: make this better
		this.x = liidetav1.x;
		this.y = liidetav1.y;
		this.z = liidetav1.z;

		return this;
	}
}
