package model.physics;

import model.maths.Vector3D;

public class Mass {

	public float mass; // mass
	public float radius;
	public Vector3D pos; // position, velocity and force
	public Vector3D vel;
	protected Vector3D force;

	public Mass(float mass, float radius, Vector3D pos, Vector3D vel, Vector3D force) {
		this.mass = mass;
		this.radius = radius;
		this.pos = pos;
		this.vel = vel;
		this.force = force;
	}

	public void applyForce(Vector3D force) {
		this.force.add(force);
	}

	// TODO rewrite a new Planet class extending this class
	public void checkAndFixOutOfBounds() {

		int bounds = 20;

		if (Math.abs(this.pos.x) > bounds || Math.abs(this.pos.y) > bounds || Math.abs(this.pos.z) > bounds) {
			this.vel.multiply(-1);
		}
	}

	public void solve(int fps) {
		// acceleration = force / mass
		// vel += acceleration

		// Reference the force, we only need to calculate the position and
		// velocity
		Vector3D currentForce = new Vector3D(this.force);
		Vector3D acceleration = currentForce.divide(this.mass);

		acceleration.multiply((float) fps);

		this.vel.add(acceleration);

		// pos += vel
		this.pos.add(this.vel);
	}
}
