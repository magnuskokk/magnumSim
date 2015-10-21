package view;

import com.jogamp.opengl.glu.GLU;

import model.maths.Point3D;
import model.maths.Vector3D;

public class Camera {

	private Point3D eye;

	private Vector3D vector;

	private Vector3D up;

	public Camera() {
		// Init the camera
		this.eye = new Point3D(0.0f, 70.0f, 0.0f);

		// This is the camera vector (center is 0,0,0)
		this.vector = new Vector3D(-this.eye.x, -this.eye.y, -this.eye.z);

		this.up = new Vector3D(0.0f, 1.0f, 1.0f);
	}

	public void moveForward() {
		Vector3D unitVector = this.vector.getUnitVector();

		this.eye.add(unitVector.x, unitVector.y, unitVector.z);
		this.vector.subtract(unitVector);
	}

	public void moveBack() {
		Vector3D unitVector = this.vector.getUnitVector();

		this.eye.add(-unitVector.x, -unitVector.y, -unitVector.z);
		this.vector.add(unitVector);
	}

	public void moveLeft() {
		Vector3D unitVector = this.up.getUnitVector();

		this.up.add(unitVector);
	}

	public void moveRight() {
		Vector3D unitVector = this.up.getUnitVector();

		this.up.subtract(unitVector);
	}

	public void rotateUp() {

	}

	public void rotateDown() {

	}

	public void rotateLeft() {
		this.rotate(1);
	}

	public void rotateRight() {
		this.rotate(-1);
	}

	public void rotate(int direction) {
		double angle = direction > 0 ? -0.1 : 0.1;

		// Rodrigues' rotation formula
		// https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
		Vector3D copyVector = new Vector3D(this.vector);
		Vector3D liidetav1 = copyVector.multiply(Math.cos(angle));

		copyVector = new Vector3D(this.vector);
		Vector3D liidetav2 = copyVector.vektorKorrutis(this.up);
		liidetav2.multiply(Math.sin(angle));

		copyVector = new Vector3D(this.vector);
		Vector3D copyUpVector = new Vector3D(this.up);

		float skalaarKorrutis = copyVector.skalaarKorrutis(this.up);
		Vector3D liidetav3 = copyUpVector.multiply(skalaarKorrutis * (1 - Math.cos(angle)));

		liidetav1.add(liidetav2).add(liidetav3);

		this.vector = liidetav1;
	}

	public void lookAt(GLU glu) {
		glu.gluLookAt(this.eye.x, this.eye.y, this.eye.z, this.vector.x, this.vector.y, this.vector.z, this.up.x,
				this.up.y, this.up.z);
	}

	public void rollLeft() {

	}

	public void rollRight() {

	}
}