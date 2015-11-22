package eu.kokk.model.physics;

import eu.kokk.model.maths.Vector3D;

public class PointMass {

    public double mass; // mass
    public Vector3D pos; // position, velocity and force
    public Vector3D vel;
    public Vector3D force;

    public int passes;

    /**
     * Construct a new point mass
     *
     * @param mass  Mass
     * @param pos   Position
     * @param vel   Velocity
     * @param force Total force applied
     */
    public PointMass(double mass, Vector3D pos, Vector3D vel, Vector3D force) {
        this.mass = mass;
        this.pos = pos;
        this.vel = vel;
        this.force = force;
    }

    /**
     * Apply force to this mass
     *
     * @param force Force to be applied
     */
    public void applyForce(Vector3D force) {
        this.force = this.force.add(force);
    }

    /**
     * Calculate the new velocity and position for this mass
     *
     * @param dt Time passed since the beginning of frame
     */
    public void solve(double dt) {
        // acceleration = force / mass
        // vel += acceleration

        // Reference the force, we only need to calculate the position and
        // velocity
        // acceleration.multiply((float) fps);
        Vector3D currentForce = new Vector3D(this.force);
        Vector3D acceleration = currentForce.divide(this.mass);
        this.vel = this.vel.add(acceleration);

        this.vel = this.vel.multiply(dt);

        this.pos = this.pos.add(this.vel);

        // pos += vel
    }
}
