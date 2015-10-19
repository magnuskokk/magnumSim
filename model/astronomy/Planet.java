package model.astronomy;

import model.maths.Vector3D;
import model.physics.Mass;

public class Planet extends Mass {

	public Planet() {
		super(2.0f);
		//this.mass = 2.0f;
		
		this.pos = new Vector3D(0.0f, 0.0f, 0.0f);
		this.vel = new Vector3D(0.0f, 0.0f, 0.0f);
		this.force = new Vector3D(0.0f, 0.0f, 0.0f);
		
		
		
		// TODO Auto-generated constructor stub
	}

}
