package view;

import com.jogamp.opengl.glu.GLU;

import model.Point3D;
import model.Vector3D;

public class Camera {

	private Point3D eye, center;

	private Vector3D vector;

	public Camera() {

		this.eye = new Point3D(0.0f, 20.0f, 0.0f);
		this.center = new Point3D(0.0f, 0.0f, 0.0f);

		// This is the camera vector
		this.vector = new Vector3D(this.center, this.eye);
	}

	public void moveForward() {
		System.out.println("forward");
	}

	public void moveBack() {
		System.out.println("back");

	}

	public void moveLeft() {
		System.out.println("left");

	}

	public void moveRight() {
		System.out.println("right");

	}

	public void rotateUp() {
		System.out.println("rotateUp");

	}

	public void rotateDown() {
		System.out.println("rotateDown");

	}

	public void rotateLeft() {
		System.out.println("rotateLeft");

	}

	public void rotateRight() {
		System.out.println("rotateRight");

	}

	public void zoomIn() {
		// Make the vector shorter, so the camera moves closer
		this.vector.multiply(0.9f);
	}

	public void zoomOut() {
		// Make the vector longer, so the camera moves further
		this.vector.multiply(1.1f);
	}

	public void lookAt(GLU glu) {
		// System.out.println(this.vector.begin);
		// System.exit(0);

		glu.gluLookAt(this.vector.end.x, this.vector.end.y, this.vector.end.z, this.vector.begin.x, this.vector.begin.y,
				this.vector.begin.z, 0.0, 0.0, 1.0);

	}
}