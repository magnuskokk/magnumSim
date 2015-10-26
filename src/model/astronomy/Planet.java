/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.astronomy;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import model.maths.Vector3D;
import model.physics.PointMass;

/**
 *
 * @author magnus
 */
public class Planet extends PointMass {

    public float radius;

    private final float amb[];
    private final float diff[];
    private final float spec[];
    private final float shine;

    public Planet(float mass, float radius, Vector3D pos, Vector3D vel, Vector3D force) {
        super(mass, pos, vel, force);

        this.radius = radius;

        this.amb = new float[4];
        this.diff = new float[4];
        this.spec = new float[4];
        this.shine = (float) Math.random();

        for (int i = 0; i < 3; i++) {
            this.amb[i] = (float) Math.random();
            this.diff[i] = (float) Math.random();
            this.spec[i] = (float) Math.random();
        }

        this.amb[3] = 1.0f;
        this.diff[3] = 1.0f;
        this.spec[3] = 1.0f;
    }

    public void drawOnScreen(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric glpQ = glu.gluNewQuadric();

        gl.glPushMatrix();
        {
            gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, this.amb, 0);
            gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, this.diff, 0);
            gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, this.spec, 0);
            gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, this.shine * 128.0f);

            // someMaterials.setMaterialGoldenSun(gl);
            gl.glColor4f(1f, 1f, 1f, 1f);
            gl.glTranslatef((float) this.pos.x, (float) this.pos.y,
                    (float) this.pos.z);

            glu.gluSphere(glpQ, this.radius, 10, 10);

        }
        gl.glPopMatrix();

    }
}
