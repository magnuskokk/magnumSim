package model.physics;

import model.maths.Vector3D;

public class Mass {
	
	protected double mass; // mass
	protected Vector3D pos; // position, velocity and force
	protected Vector3D vel;
	protected Vector3D force;

	public Mass(double mass) {
		this.mass = mass;
	}
	
	public void applyForce(Vector3D force) {
		this.force.add(force);
	}
	
	public void simulate(double dt) {
		// vel += (force/mass) * dt
		double mul = dt / this.mass;
		this.vel.add(this.force.multiply(mul));
		
		// pos += vel * dt
		this.pos.add(this.vel.multiply(dt)); 
	}
}
