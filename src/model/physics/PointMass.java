package model.physics;

import model.maths.Vector3D;

public class PointMass {

    public float mass; // mass
    public Vector3D pos; // position, velocity and force
    public Vector3D vel;
    public Vector3D force;

    public int passes;

    public PointMass(float mass, Vector3D pos, Vector3D vel, Vector3D force) {
        this.mass = mass;
        this.pos = pos;
        this.vel = vel;
        this.force = force;
    }

    public void applyForce(Vector3D force) {
        this.force.add(force);
    }

    // TODO rewrite a new Planet class extending this class
    public void checkAndFixOutOfBounds() {

        int bounds = 5;

        if (Math.abs(this.pos.x) > bounds || Math.abs(this.pos.y) > bounds || Math.abs(this.pos.z) > bounds) {
            this.vel.multiply(-1);
        }
    }

    public void solve(double dt) {
		// acceleration = force / mass
        // vel += acceleration

		// Reference the force, we only need to calculate the position and
        // velocity
        // acceleration.multiply((float) fps);
        Vector3D currentForce = new Vector3D(this.force);
        Vector3D acceleration = currentForce.divide(this.mass);
        this.vel.add(acceleration);

        this.vel.multiply(dt);

        this.pos.add(this.vel);

        // pos += vel
    }
}
