package view;

import com.jogamp.opengl.GL2;
import main.Config;
import main.Main;
import model.maths.Point3D;
import model.maths.Vector3D;

public class Camera implements Config {

    public static Point3D eye;
    public static Point3D center;

    public static Vector3D up;

    public float zoomLevel = 1;

    /**
     * Camera constructor
     */
    public Camera() {
        // Init the camera
        eye = new Point3D(0, 0, 70);
        center = new Point3D();

        this.up = new Vector3D(0, 1, 0);
    }

    /**
     * Set the camera according to settings
     */
    public void setCamera() {
        // Change to projection matrix.
        Main.gl.glMatrixMode(GL2.GL_PROJECTION);
        Main.gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) Config.frameWidth / (float) Config.frameHeight;
        Main.glu.gluPerspective(45.0f * this.zoomLevel, widthHeightRatio, 1, 1000);
        Main.glu.gluLookAt(this.eye.x, this.eye.y, this.eye.z, this.center.x, this.center.y, this.center.z, this.up.x, this.up.y, this.up.z);

        // Change back to model view matrix.
        Main.gl.glMatrixMode(GL2.GL_MODELVIEW);
        Main.gl.glLoadIdentity();
    }

    /**
     * Get the vector that is perpendicular to the camera direction vector and up vector
     *
     * @return The side vector
     */
    public Vector3D getSideVector() {
        return new Vector3D(this.center, this.eye).crossProduct(this.up).toUnitVector();
    }

    /**
     * Move camera in the specified direction
     *
     * @param direction 0 - forward
     *                  1 - right
     *                  2 - back
     *                  3 - left
     * @param dt        Time passed since the beginning of frame
     */
    public void move(int direction, double dt) {
        switch (direction) {
            case 0: // Move forward
                this.zoomLevel *= 0.99f;
                break;

            case 1: // Move right
                this.center.add(this.getSideVector());
                this.eye.add(this.getSideVector());
                break;

            case 2: // Move back
                this.zoomLevel *= 1.01f;
                break;

            case 3: // Move left
                this.center.subtract(this.getSideVector());
                this.eye.subtract(this.getSideVector());
                break;
        }
    }

    /**
     * Rotate the camera in the specified direction
     *
     * @param direction 0 - up
     *                  1 - right
     *                  2 - down
     *                  3 - left
     */
    public void rotate(int direction) {
        Vector3D sideAxis = new Vector3D();

        if (direction % 2 == 0) {
            // Rotate up & down, we need the side axis
            sideAxis = new Vector3D(this.center, this.eye).crossProduct(this.up).toUnitVector();
        }

        switch (direction) {
            case 0: // Rotate up
                this.center.rotateAround(this.eye, sideAxis, Config.cameraRotationAngle);
                break;

            case 1: // Rotate right
                this.center.rotateAround(this.eye, this.up, -Config.cameraRotationAngle);
                break;

            case 2: // Rotate down
                this.center.rotateAround(this.eye, sideAxis, -Config.cameraRotationAngle);
                break;

            case 3: // Rotate left
                this.center.rotateAround(this.eye, this.up, Config.cameraRotationAngle);
                break;
        }
    }

    /**
     * Roll the camera in the specified direction
     *
     * @param direction 1 - right
     *                  -1 - left
     */
    public void roll(int direction) {
        // We need to rotate the up vector
        Vector3D axis = new Vector3D(this.center, this.eye);

        switch (direction) {
            case 1: // Roll to the right
                this.up.rotateAroundAxis(axis, -Config.cameraRollAngle);
                break;


            case -1: // Roll to the left
                this.up.rotateAroundAxis(axis, Config.cameraRollAngle);
                break;
        }
    }
}
