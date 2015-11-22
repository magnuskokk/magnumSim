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
     * @param end Ending point
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
     *
     * @param vector
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
        Vector3D temp = new Vector3D(this);

        temp.x *= scalar;
        temp.y *= scalar;
        temp.z *= scalar;

        return temp;
    }

    /**
     * Divide vector by a scalar
     *
     * @param scalar Scalar
     * @return The new vector
     */
    public Vector3D divide(double scalar) {
        Vector3D temp = new Vector3D(this);

        temp.x /= scalar;
        temp.y /= scalar;
        temp.z /= scalar;

        return temp;
    }

    /**
     * Add a vector
     *
     * @param vector Vector
     * @return The new vector
     */
    public Vector3D add(Vector3D vector) {
        Vector3D temp = new Vector3D(this);

        temp.x += vector.x;
        temp.y += vector.y;
        temp.z += vector.z;

        return temp;
    }

    /**
     * Subtract a vector
     *
     * @param vector Vector
     * @return The new vector
     */
    public Vector3D subtract(Vector3D vector) {
        Vector3D temp = new Vector3D(this);

        temp.x -= vector.x;
        temp.y -= vector.y;
        temp.z -= vector.z;

        return temp;
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
        Vector3D temp = new Vector3D(this);

        double Mx = this.y * vector.z - this.z * vector.y;
        double My = this.z * vector.x - this.x * vector.z;
        double Mz = this.x * vector.y - this.y * vector.x;

        temp.x = Mx;
        temp.y = My;
        temp.z = Mz;

        return temp;
    }

    /**
     * Get the unit vector
     *
     * @return The unit vector
     */
    public Vector3D getUnitVector() {
        return this.divide(this.length());
    }

    /**
     * Normalize this vector
     *
     * @return This vector
     */
    public Vector3D normalize() {
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
     * @param axis The axis vector
     * @param angle The angle to rotate by
     * @return The new vector
     */
    public Vector3D rotateAroundAxis(Vector3D axis, double angle) {

        // Rodrigues' rotation formula
        // https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
        Vector3D liidetav1 = this.multiply(Math.cos(angle));

        Vector3D liidetav2 = this.crossProduct(axis).multiply(Math.sin(angle));

        double skalaarKorrutis = this.dotProduct(axis);
        Vector3D liidetav3 = axis.multiply(skalaarKorrutis * (1 - Math.cos(angle)));

        return new Vector3D(liidetav1.add(liidetav2).add(liidetav3));
    }

    public Point3D getAsPoint() {
        return new Point3D(this.x, this.y, this.z);
    }
}
