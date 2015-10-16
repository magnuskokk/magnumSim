package view;

import com.jogamp.opengl.glu.GLU;

public class Camera {

	private float eyeX, eyeY, eyeZ;

	public Camera() {
		this.eyeX = 0.0f;
		this.eyeY = 20.0f;
		this.eyeZ = 0.0f;
	}

	public void zoomIn() {
		this.eyeY--;
	}

	public void zoomOut() {
		this.eyeY++;
	}

	public void lookAt(GLU glu) {
		glu.gluLookAt(eyeX, eyeY, eyeZ, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0);

	}
}