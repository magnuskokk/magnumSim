package model.maths;

public class Point3D {

    public double x, y, z;

    /**
     * Point constructor
     *
     * @param x x-value
     * @param y y-value
     * @param z z-value
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Zero-point constructor
     */
    public Point3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Copy constructor
     */
    public Point3D(Point3D point) {
        this(point.x, point.y, point.z);
    }

    /**
     * Add a vector to this point (move)
     * @param vector Vector
     * @return The new point
     */
    public Point3D add(Vector3D vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;

        return this;
    }

    /**
     * Subtract a vector from this point (move)
     * @param vector
     * @return The new point
     */
    public Point3D subtract(Vector3D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;

        return this;
    }

    /**
     * Rotates this point about the line through point p with direction vector dir (must be unit vector)
     * by an angle
     * Formula from here: https://sites.google.com/site/glennmurray/Home/rotation-matrices-and-formulas
     * @param p A point on the rotation axis
     * @param dir Direction of the rotation axis
     * @param angle Angle to rotate by
     * @return The new point
     */
    public Point3D rotateAround(Point3D p, Vector3D dir, double angle) {
        double a = p.x;
        double b = p.y;
        double c = p.z;
        double u = dir.x;
        double v = dir.y;
        double w = dir.z;

        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double newX;
        double newY;
        double newZ;

        newX = (a * (v * v + w * w) - u * (b * v + c * w - u * this.x - v * this.y - w * this.z)) * (1 - angleCos)
                + this.x * angleCos
                + (-c * v + b * w - w * this.y + v * this.z) * angleSin;

        newY = (b * (u * u + w * w) - v * (a * u + c * w - u * this.x - v * this.y - w * this.z)) * (1 - angleCos)
                + this.y * angleCos
                + (c * u - a * w + w * this.x - u * this.z) * angleSin;

        newZ = (c * (u * u + v * v) - w * (a * u + b * v - u * this.x - v * this.y - w * this.z)) * (1 - angleCos)
                + this.z * angleCos
                + (-b * u + a * v - v * this.x + u * this.y) * angleSin;

        this.x = newX;
        this.y = newY;
        this.z = newZ;

        return this;
    }
}
