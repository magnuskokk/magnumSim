package model.astronomy;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLUquadric;
import main.Main;
import model.maths.Vector3D;
import model.physics.PointMass;

public class Planet extends PointMass {

    private final float amb[];
    private final float diff[];
    private final float spec[];
    private final float shine;
    public double radius;

    /**
     * Planet constructor
     *
     * @param mass   Mass
     * @param radius Radius
     * @param pos    Position
     * @param vel    Velocity
     * @param force  Force applied to the planet
     */
    public Planet(double mass, double radius, Vector3D pos, Vector3D vel, Vector3D force) {
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

    /**
     * Draw the planet on the screen
     */
    public void drawOnScreen() {
        GLUquadric glpQ = Main.glu.gluNewQuadric();

        Main.gl.glPushMatrix();
        {
            Main.gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, this.amb, 0);
            Main.gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, this.diff, 0);
            Main.gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, this.spec, 0);
            Main.gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, this.shine * 128.0f);

            // someMaterials.setMaterialGoldenSun(gl);
            Main.gl.glColor4f(1f, 1f, 1f, 1f);
            Main.gl.glTranslated(this.pos.x, this.pos.y, this.pos.z);

            Main.glu.gluSphere(glpQ, this.radius, 10, 10);

        }
        Main.gl.glPopMatrix();
    }
}
