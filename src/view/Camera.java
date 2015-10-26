package view;

import com.jogamp.opengl.glu.GLU;

import main.Config;
import model.maths.Point3D;
import model.maths.Vector3D;

public class Camera implements Config {

    public static Point3D eye;

    public static Point3D center;

    private Vector3D vector;

    private Vector3D up;

    private Vector3D side;

    private Point3D previousCenter;

    public float fov;

    public float aspectRatio;

    public float zNear;

    public float zFar;

    public float zoomLevel = 1.0f;

    public Camera() {
        // Init the camera
        eye = new Point3D(0.0f, 70.0f, 70.0f);
        center = new Point3D();

        // This is the camera vector (center is 0,0,0)
        this.vector = new Vector3D(center, eye).getUnitVector();

        // Z is up
        this.up = new Vector3D(0.0f, 0.0f, 1.0f);

        this.side = new Vector3D(this.vector).vektorKorrutis(this.up).getUnitVector();

        this.fov = 1.0f;
        this.aspectRatio = 2.0f;
        this.zNear = 1.0f;
        this.zFar = 20.0f;

    }

    public void move(int direction, double dt) {
        int dir = 0;
        Vector3D dirVector = null;

        switch (direction) {
            case 0: // Move forward
                this.zoomLevel *= 1.1f;
                break;

            case 1: // Move right
                break;

            case 2: // Move back
                this.zoomLevel *= 0.9f;
                break;

            case 3: // Move left
                break;
        }
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

        this.vector.rotateAroundAxis(axis, dir * Config.cameraRotationAngle * dt);
        calcPerp = new Vector3D(this.vector).vektorKorrutis(axis).multiply(mul);

        Point3D copyEye = new Point3D(this.eye);

        center = copyEye.add(this.vector);
    }

    public void lookAt(GLU glu) {
        glu.gluLookAt(eye.x, eye.y, eye.z, center.x, center.y, center.z, this.up.x,
                this.up.y, this.up.z);

        float d = 0.01f;
        float minFocalLength = 0.01f;

        this.fov = (float) (2.0 * Math.atan(d / (2 * minFocalLength * this.zoomLevel)));

        glu.gluPerspective(this.fov, this.aspectRatio, this.zNear, this.zFar);
    }

    public void roll(int direction) {

    }
}
