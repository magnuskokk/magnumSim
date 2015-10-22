package view;

import com.jogamp.opengl.glu.GLU;

import model.maths.Point3D;
import model.maths.Vector3D;

public class Camera {

	private Point3D eye;

	private Point3D center;

	private Vector3D vector;

	private Vector3D up;

	private Vector3D side;

	private double rotateAngle = 0.1;

	private float movingSpeed = 20.0f;

	public Camera() {
		// Init the camera
		this.eye = new Point3D(0.0f, 70.0f, 0.0f);
		this.center = new Point3D();

		// This is the camera vector (center is 0,0,0)
		this.vector = new Vector3D(center, eye).getUnitVector();

		// Z is up
		this.up = new Vector3D(0.0f, 0.0f, 1.0f);

		this.side = new Vector3D(this.vector).vektorKorrutis(this.up).getUnitVector();
	}

	public void move(int direction, double dt) {
		int dir = 0;
		Vector3D dirVector = null;

		switch (direction) {
		case 0: // Move forward
			dir = 1;
			dirVector = this.vector;
			break;

		case 1: // Move right
			dir = 1;
			dirVector = this.side;
			break;

		case 2: // Move back
			dir = -1;
			dirVector = this.vector;
			break;

		case 3: // Move left
			dir = -1;
			dirVector = this.side;
			break;
		}

		dir *= this.movingSpeed;

		this.eye.add(dir * dirVector.x * (float) dt, dir * dirVector.y * (float) dt, dir * dirVector.z * (float) dt);
		this.center.add(dir * dirVector.x * (float) dt, dir * dirVector.y * (float) dt, dir * dirVector.z * (float) dt);

		this.vector = new Vector3D(center, eye).getUnitVector();
	}

	public void rotate(int direction, double dt) {
		int dir = 0, mul = 0;
		Vector3D axis = null, calcPerp = null;

		switch (direction) {
		case 0: // Rotate up
			dir = -1;
			mul = -1;
			axis = this.side;
			calcPerp = this.up;
			break;

		case 1: // Rotate right
			dir = 1;
			mul = 1;
			axis = this.up;
			calcPerp = this.side;
			break;

		case 2: // Rotate down
			dir = 1;
			mul = -1;
			axis = this.side;
			calcPerp = this.up;
			break;

		case 3: // Rotate left
			dir = -1;
			mul = 1;
			axis = this.up;
			calcPerp = this.side;
			break;
		}

		this.vector.rotateAroundAxis(axis, dir * this.rotateAngle * dt);
		calcPerp = new Vector3D(this.vector).vektorKorrutis(axis).multiply(mul);

		Point3D copyEye = new Point3D(this.eye);

		this.center = copyEye.add(this.vector);
	}

	public void lookAt(GLU glu) {
		glu.gluLookAt(this.eye.x, this.eye.y, this.eye.z, this.center.x, this.center.y, this.center.z, this.up.x,
				this.up.y, this.up.z);
	}

	public void roll(int direction) {

	}
}