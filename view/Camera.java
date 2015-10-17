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
		this.eyeX *= 0.9f;
		this.eyeY *= 0.9f;
		this.eyeZ *= 0.9f;
	}

	public void zoomOut() {
		this.eyeX *= 1.1f;
		this.eyeY *= 1.1f;
		this.eyeZ *= 1.1f;
	}

	public void lookAt(GLU glu) {
		glu.gluLookAt(this.eyeX, this.eyeY, this.eyeZ, this.centerX, this.centerY, this.centerZ, 0.0, 0.0, 1.0);
	}
}