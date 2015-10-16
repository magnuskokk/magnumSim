package view;

import com.jogamp.opengl.glu.GLU;

import model.Vector3D;

public class Camera {

	private double eyeX, eyeY, eyeZ, centerX, centerY, centerZ;
	
	public Camera() {
		eyeX = 0.0f;
		eyeY = 20.0f;
		eyeZ = 0.0f;

		centerX = 0.0f;
		centerY = 0.0f;
		centerZ = 0.0f;
	}

	public void moveUp() {

	}

	public void moveDown() {

	}

	public void moveLeft() {

	}

	public void moveRight() {

	}

	public void zoomIn() {
		eyeX *= 0.9f;
		eyeY *= 0.9f;
		eyeZ *= 0.9f;
	}

	public void zoomOut() {
		eyeX *= 1.1f;
		eyeY *= 1.1f;
		eyeZ *= 1.1f;
	}

	public void lookAt(GLU glu) {
		glu.gluLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, 0.0, 0.0, 1.0);
	}
}