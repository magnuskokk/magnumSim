package model.astronomy;

import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import main.someMaterials;
import model.maths.Point3D;
import model.maths.Vector3D;
import model.physics.Mass;

public class Space {
	//
	// public float hourofday = 0f;
	// public float dayofyear = 0f;
	// public float dayofmonth = 10f;
	private Mass[] planets;

	private int numPlanets = 100;

	static float G = 0.001f; // TODO: change this

	public float[][] colors;

	public Space() {
		// Vector3D pos = new Vector3D(0.0f, 0.0f, 5.0f);
		// Vector3D vel = new Vector3D(0.0f, 0.0f, 0.0f);
		// Vector3D force = new Vector3D(0.0f, 0.0f, -0.098f);

		this.colors = new float[numPlanets][10];

		Random rand = new Random();

		this.planets = new Mass[this.numPlanets];

		for (int i = 0; i < this.numPlanets; i++) {
			int randomX = rand.nextInt((20));
			int randomY = rand.nextInt((10));
			int randomZ = rand.nextInt((15));

			float mul = 1;
			if (i % 3 == 0) {
				mul = (float) Math.random();

			} else {
				mul = -1;
			}

			Vector3D pos = new Vector3D((float) randomX, (float) randomY, (float) randomZ * mul);
			Vector3D vel = new Vector3D((float) Math.random(), (float) Math.random(), (float) Math.random());

			// Vector3D vel = new Vector3D();
			Vector3D force = new Vector3D();

			float massRadius = (float) Math.random();

			this.planets[i] = new Mass(massRadius * 80, massRadius, pos, vel, force);
			this.planets[i].passes = 0;

			// TODO: random colors atm, make some classes orsmth
			for (int j = 0; j < 10; j++) {
				this.colors[i][j] = (float) Math.random();
			}

		}

	}

	/**
	 * This method is called from Main.display() every frame
	 */
	public void simulate(GL2 gl, double dt) {
		/*
		 * Basically we need to iterate through all the planets and calculate
		 * their new position and velocity vectors according to the force
		 * applied to them
		 */

		for (int i = 0; i < this.numPlanets; i++) {

			// Find all gravitational forces which attract a single planet

			for (int j = 0; j < this.numPlanets; j++) {
				if (i != j) {

					float distanceVectorX = planets[j].pos.x - planets[i].pos.x;
					float distanceVectorY = planets[j].pos.y - planets[i].pos.y;
					float distanceVectorZ = planets[j].pos.z - planets[i].pos.z;

					Vector3D distanceVector = new Vector3D(distanceVectorX, distanceVectorY, distanceVectorZ);

					float forceBetween = (G * planets[i].mass * planets[j].mass)
							/ ((float) Math.pow(distanceVector.length(), 2));

					Vector3D unitVector = distanceVector.getUnitVector();

					Vector3D forceVector = new Vector3D();

					// if the planets are far enough (not side by side)
					if (distanceVector.length() > (planets[i].radius + planets[j].radius)) {
						forceVector = unitVector.multiply(forceBetween);
					} else {
						// we have a collision
						// va1 = ( v1 * (m1 - m2) + 2 * m2 * v2 ) / ( m1 + m2 )

						////
						//
						// planets[i].force.multiply(-1);
						// planets[j].force.multiply(-1);

						// v1f = −v1i + 2(m1 v1i + m2 v2i)
						// m1 + m2
						// (1)
						//
						// Vector3D v1 = new Vector3D(planets[i].vel);
						// Vector3D v2 = new Vector3D(planets[j].vel);
						//
						//
						// Vector3D murd1 = v1.multiply(planets[i].mass);
						// murd1.add(v2.multiply(planets[j].mass));
						// murd1.multiply(2);
						// murd1.divide(planets[i].mass + planets[j].mass);
						//
						// v1 = new Vector3D(planets[i].vel);
						// v1.multiply(-1);
						//
						// v2 = new Vector3D(planets[j].vel);
						// v2.multiply(-1);

						// Vector3D changedVel1 = v1.add(murd1);
						// Vector3D changedVel2 = v2.add(murd1);
						//
						// System.out.println(changedVel1.x);
						//
						//
						// planets[i].vel = changedVel1;
						// planets[j].vel = changedVel2;
						// this.planets[j].solve(dt);

						// v2f = −v2i + 2(m1 v1i + m2 v2i)
						// m1 + m2

						//
						//
						//
						//
						//
						//
						//

					}

					planets[i].applyForce(forceVector);

					this.planets[i].solve(dt);

					// planets[i].checkAndFixOutOfBounds();
				}
			}
		}

		// Here the calculations are made, time to draw to the screen

		GLU glu = new GLU(); // needed for lookat
		for (int i = 0; i < this.numPlanets; i++) {

			GLUquadric glpQ = glu.gluNewQuadric();

			gl.glPushMatrix();
			{

				float amb[] = { colors[i][0], colors[i][1], colors[i][2], 1.0f };
				float diff[] = { colors[i][3], colors[i][4], colors[i][5], 1.0f };
				float spec[] = { colors[i][6], colors[i][7], colors[i][8], 1.0f };
				float shine = colors[i][9];
				gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
				gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
				gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, spec, 0);
				gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine * 128.0f);

				// someMaterials.setMaterialGoldenSun(gl);

				gl.glColor4f(1f, 1f, 1f, 1f);
				gl.glTranslatef((float) this.planets[i].pos.x, (float) this.planets[i].pos.y,
						(float) this.planets[i].pos.z);

				glu.gluSphere(glpQ, this.planets[i].radius, 10, 10);

			}
			gl.glPopMatrix();
		}

	}

	public Point3D getCenterPointOfMasses() {
		Point3D centerPoint = new Point3D();

		for (int i = 0; i < this.planets.length; i++) {
			centerPoint.add(this.planets[i].pos);
		}

		centerPoint.divide(this.planets.length);

		return centerPoint;
	}

	/**
	 * TODO: rewrite this method
	 * 
	 * @param gl
	 */
	// planets
	public void drawPlanets(GL2 gl) {
		//
		// float angleDay = (hourofday / 24f) * 360f;
		// float angleYear = (dayofyear / 365f) * 360f;
		// float angleMonth = (dayofmonth / 28f) * 360f;
		// // Clocktick (unit is 1 hour): step time.
		// final float clocktick = 24f;
		// hourofday = (hourofday + clocktick) % 24f;
		// dayofmonth = (dayofmonth + (clocktick / 24f)) % 28f;
		// dayofyear = (dayofyear + (clocktick / 24f)) % 365f;
		//
		// // System.out.println("Day: " + angleDay + " Month: " + angleMonth +
		// "
		// // Year: " + angleYear);// ddd
		//
		// GLU glu = new GLU(); // needed for lookat
		// GLUquadric glpQ = glu.gluNewQuadric();
		//
		// gl.glPushMatrix();
		// {
		// someMaterials.setMaterialGoldenSun(gl);
		//
		// // Sun
		// gl.glColor4f(1f, 1f, 1f, 1f);
		// glu.gluSphere(glpQ, 0.8f, 10, 10);
		//
		// gl.glPushMatrix();
		// {
		// someMaterials.setMaterialGreenPlanet(gl);
		//
		// // Planet 1
		// gl.glRotatef(angleYear, 0.0f, 1.0f, 0.0f);
		// gl.glTranslatef(3.0f, 0.0f, 0.0f);
		// gl.glColor4f(0f, 1f, 0f, 1f);
		// glu.gluSphere(glpQ, 0.3f, 10, 10);
		//
		// // Moon 11
		// gl.glPushMatrix();
		// {
		// gl.glRotatef(angleMonth, 0.0f, 1.0f, 0.0f);
		// gl.glTranslatef(0.8f, 0.0f, 0.0f);
		// glu.gluSphere(glpQ, 0.1f, 10, 10);
		// }
		// gl.glPopMatrix();
		//
		// }
		// gl.glPopMatrix(); // Planet 1
		//
		// }
		// gl.glPopMatrix(); // sun

	}

}
