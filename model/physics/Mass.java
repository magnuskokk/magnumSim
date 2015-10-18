package model.physics;

import model.maths.Vector3D;

public class Mass {
	
	double mass; // mass
	Vector3D pos, vel, force; // position, velocity and force

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
