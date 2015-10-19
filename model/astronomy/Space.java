package model.astronomy;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import main.someMaterials;

public class Space {


	public float hourofday = 0f;
	public float dayofyear = 0f;
	public float dayofmonth = 10f;
	public Space() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method is called from Main.display() every frame
	 */
	public void simulate(GL2 gl) {
		/* Basically we need to iterate through all the planets
		 * and calculate their new position and velocity vectors
		 * according to the force applied to them
		 */
		
		
		
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
