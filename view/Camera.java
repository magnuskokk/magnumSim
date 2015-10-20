package view;

import com.jogamp.opengl.glu.GLU;

import model.maths.Point3D;
import model.maths.Vector3DWithPoints;

public class Camera {

	private Point3D eye, center;

	private Vector3DWithPoints vector;

	public Camera() {
		eye = new Point3D(0.0f, 70.0f, 0.0f);
		center = new Point3D(0.0f, 0.0f, 0.0f);

		// This is the camera vector
		vector = new Vector3DWithPoints(eye, center);
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
		vector.multiply(0.9f);
	}

	public void zoomOut() {
		// Make the vector longer, so the camera moves further
		vector.multiply(1.1f);
	}

	public void lookAt(GLU glu) {
		glu.gluLookAt(vector.begin.x, vector.begin.y, vector.begin.z, vector.end.x, vector.end.y, vector.end.z, 0.0,
				0.0, 1.0);
	}
}