package model.physics;

import model.maths.Vector3D;

public class Mass {

	protected float mass; // mass
	public Vector3D pos; // position, velocity and force
	public Vector3D vel;
	protected Vector3D force;

	public Mass(float mass, Vector3D pos, Vector3D vel, Vector3D force) {
		this.mass = mass;
		this.pos = pos;
		this.vel = vel;
		this.force = force;
	}

	public void applyForce(Vector3D force) {
		this.force.add(force);
	}

	public void solve(int dt) {
		// vel += (force/mass) * dt
		float mul = dt / this.mass;
		this.vel.add(this.force.multiply(mul));

		// pos += vel * dt
		this.pos.add(this.vel.multiply(dt));
	}
}
