package model.astronomy;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import main.someMaterials;
import model.maths.Vector3D;
import model.physics.Mass;

public class Space {

	public float hourofday = 0f;
	public float dayofyear = 0f;
	public float dayofmonth = 10f;
	private Mass planet;

	public Space() {
		Vector3D pos = new Vector3D();
		Vector3D vel = new Vector3D(0.0f, 0.0f, 0.0f);
		Vector3D force = new Vector3D(0.0f, 0.0f, -0.098f);

		this.planet = new Mass(8.0f, pos, vel, force);

	}

	/**
	 * This method is called from Main.display() every frame
	 */
	public void simulate(GL2 gl, int time, int frame) {
		/*
		 * Basically we need to iterate through all the planets and calculate
		 * their new position and velocity vectors according to the force
		 * applied to them
		 */

		this.planet.solve();

		// Here the calculations are made, time to draw to the screen

		GLU glu = new GLU(); // needed for lookat
		GLUquadric glpQ = glu.gluNewQuadric();

		gl.glPushMatrix();
		{
			someMaterials.setMaterialGoldenSun(gl);

			// Sun
			gl.glColor4f(1f, 1f, 1f, 1f);
			gl.glTranslatef((float) planet.pos.x, (float) planet.pos.y, (float) planet.pos.z);

			glu.gluSphere(glpQ, 0.8f, 10, 10);

		}
		gl.glPopMatrix(); // sun

	}

	/**
	 * TODO: rewrite this method
	 * 
	 * @param gl
	 */
	// planets
	public void drawPlanets(GL2 gl) {

		float angleDay = (hourofday / 24f) * 360f;
		float angleYear = (dayofyear / 365f) * 360f;
		float angleMonth = (dayofmonth / 28f) * 360f;
		// Clocktick (unit is 1 hour): step time.
		final float clocktick = 24f;
		hourofday = (hourofday + clocktick) % 24f;
		dayofmonth = (dayofmonth + (clocktick / 24f)) % 28f;
		dayofyear = (dayofyear + (clocktick / 24f)) % 365f;

		// System.out.println("Day: " + angleDay + " Month: " + angleMonth + "
		// Year: " + angleYear);// ddd

		GLU glu = new GLU(); // needed for lookat
		GLUquadric glpQ = glu.gluNewQuadric();

		gl.glPushMatrix();
		{
			someMaterials.setMaterialGoldenSun(gl);

			// Sun
			gl.glColor4f(1f, 1f, 1f, 1f);
			glu.gluSphere(glpQ, 0.8f, 10, 10);

			gl.glPushMatrix();
			{
				someMaterials.setMaterialGreenPlanet(gl);

				// Planet 1
				gl.glRotatef(angleYear, 0.0f, 1.0f, 0.0f);
				gl.glTranslatef(3.0f, 0.0f, 0.0f);
				gl.glColor4f(0f, 1f, 0f, 1f);
				glu.gluSphere(glpQ, 0.3f, 10, 10);

				// Moon 11
				gl.glPushMatrix();
				{
					gl.glRotatef(angleMonth, 0.0f, 1.0f, 0.0f);
					gl.glTranslatef(0.8f, 0.0f, 0.0f);
					glu.gluSphere(glpQ, 0.1f, 10, 10);
				}
				gl.glPopMatrix();

			}
			gl.glPopMatrix(); // Planet 1

		}
		gl.glPopMatrix(); // sun

	}

}
