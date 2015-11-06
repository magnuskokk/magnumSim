package eu.kokk.model.maths;

public class Vector3D {

    public static Vector3D zeroVector = new Vector3D();
    public double x, y, z;

    /**
     * Construct by vector coordinates
     *
     * @param d x-value
     * @param e y-value
     * @param f z-value
     */
    public Vector3D(double d, double e, double f) {
        this.x = d;
        this.y = e;
        this.z = f;
    }

    /**
     * Construct by ending and beginning points
     *
     * @param end   Ending point
     * @param begin Beginning point
     */
    public Vector3D(Point3D end, Point3D begin) {
        this.x = end.x - begin.x;
        this.y = end.y - begin.y;
        this.z = end.z - begin.z;
    }

    /**
     * Construct a zero-vector
     */
    public Vector3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Copy constructor
     */
    public Vector3D(Vector3D vector) {
        this(vector.x, vector.y, vector.z);
    }

    /**
     * Multiply vector by a scalar
     *
     * @param scalar Scalar
     * @return The new vector
     */
    public Vector3D multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;

        return this;
    }

    /**
     * Divide vector by a scalar
     *
     * @param scalar Scalar
     * @return The new vector
     */
    public Vector3D divide(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;

        return this;
    }

    /**
     * Add a vector
     *
     * @param vector Vector
     * @return The new vector
     */
    public Vector3D add(Vector3D vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;

        return this;
    }

    /**
     * Subtract a vector
     *
     * @param vector Vector
     * @return The new vector
     */
    public Vector3D subtract(Vector3D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;

        return this;
    }

    /**
     * Get the dot product of 2 vectors
     *
     * @param vector
     * @return Dot product
     */
    public double dotProduct(Vector3D vector) {
        return (this.x * vector.x + this.y * vector.y + this.z * vector.z);
    }

    /**
     * Get the cross product of 2 vectors
     *
     * @param vector
     * @return The new vector
     */
    public Vector3D crossProduct(Vector3D vector) {
        double Mx = this.y * vector.z - this.z * vector.y;
        double My = this.z * vector.x - this.x * vector.z;
        double Mz = this.x * vector.y - this.y * vector.x;

        this.x = Mx;
        this.y = My;
        this.z = Mz;

        return this;
    }

    /**
     * Get the unit vector
     *
     * @return The unit vector
     */
    public Vector3D getUnitVector() {
        Vector3D vector = new Vector3D(this);

        return vector.divide(vector.length());
    }

    /**
     * Make this vector a unit vector
     *
     * @return This vector
     */
    public Vector3D toUnitVector() {
        Vector3D unitVector = this.getUnitVector();

        this.x = unitVector.x;
        this.y = unitVector.y;
        this.z = unitVector.z;

        return this;
    }

    /**
     * Get this vector length
     *
     * @return Length
     */
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Rotate this vector around an axis vector
     *
     * @param axis  The axis vector
     * @param angle The angle to rotate by
     * @return The new vector
     */
    public Vector3D rotateAroundAxis(Vector3D axis, double angle) {

        // Rodrigues' rotation formula
        // https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
        Vector3D copyVector = new Vector3D(this);
        Vector3D liidetav1 = copyVector.multiply(Math.cos(angle));

        copyVector = new Vector3D(this);
        Vector3D liidetav2 = copyVector.crossProduct(axis);
        liidetav2.multiply(Math.sin(angle));

        copyVector = new Vector3D(this);
        Vector3D copyAxisVector = new Vector3D(axis);

        double skalaarKorrutis = copyVector.dotProduct(axis);
        Vector3D liidetav3 = copyAxisVector.multiply(skalaarKorrutis * (1 - Math.cos(angle)));

        liidetav1.add(liidetav2).add(liidetav3);

        // TODO: make this better
        this.x = liidetav1.x;
        this.y = liidetav1.y;
        this.z = liidetav1.z;

        return this;
    }

    public Point3D toPoint() {
        return new Point3D(this.x, this.y, this.z);
    }
}
