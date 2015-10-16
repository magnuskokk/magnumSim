package main;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;

public class Main implements GLEventListener, MouseListener, MouseMotionListener {

	public static void main(String[] args) {
		Frame frame = new Frame("Solar system");
		GLCanvas canvas = new GLCanvas();
		final FPSAnimator animator = new FPSAnimator(canvas, 100);

		canvas.addGLEventListener(new Main(animator));
		frame.add(canvas);
		frame.setSize(800, 600);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		animator.start();

	}

	// rotating the scene
	private float view_rotx = 20.0f;
	private float view_roty = 30.0f;

	// remember last mouse position
	private int oldMouseX;
	private int oldMouseY;

	private float m_Vx = 0.0f;
	private float m_Vz = 0.0f;
	private float m_pV = 0.0f;
	private float m_mV = 0.0f;
	private float m_pV2 = 0.0f;
	private float m_mV2 = 0.0f;

	private FPSAnimator m_animator = null;;

	public Main(FPSAnimator animator) {
		m_animator = animator;
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		// prepare ligthsource
		float ambient[] = { 0.4f, 0.4f, 0.4f, 1.0f };
		float diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float position[] = { 1.0f, 1.0f, 1.0f, 0.0f };

		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);

		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_LIGHTING);

		// Set material, yellowish
		float amb[] = { 0.3f, 0.3f, 0.0f, 1.0f };
		float diff[] = { 1.0f, 1.0f, 0.5f, 1.0f };
		float spec[] = { 0.6f, 0.6f, 0.5f, 1.0f };
		float shine = 0.25f;
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, spec, 0);
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine * 128.0f);

		// smooth the drawing
		gl.glShadeModel(GL2.GL_SMOOTH);

		// depth sorting
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LESS);

		// set background to light gray
		gl.glClearColor(0.9f, 0.9f, 0.9f, 1.0f);
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		gl.glEnable(GL2.GL_NORMALIZE);

		// drawable.addGLEventListener(this);
		// drawable.addMouseMotionListener(this);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();

		if (height <= 0) // no divide by zero
			height = 1;

		// keep ratio
		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 1.0, 200.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU(); // needed for lookat
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_ACCUM_BUFFER_BIT);
		gl.glLoadIdentity();
		glu.gluLookAt(20.0, 20.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
		// rotate around x-axis
		gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
		// rotate around z-axis
		gl.glRotatef(view_roty, 0.0f, 0.0f, 1.0f);
		// draw planets
		drawPlanets(gl);
		gl.glFlush();
	}

	// planets
	public void drawPlanets(GL2 gl) {
		GLU glu = new GLU(); // spheres
		GLUquadric glpQ = glu.gluNewQuadric();
		/** the sun in origo */
		/** Material of the sun */
		someMaterials.SetMaterialGoldenSun(gl);
		/** radius 4 */
		glu.gluSphere(glpQ, 3.0f, 20, 20);

		gl.glPushMatrix();

		/** a planet */
		/** This planets material */
		someMaterials.setMaterialGreenPlanet(gl);
		/** in XY-plane */
		gl.glRotatef(m_pV, 1.0f, 0.0f, 0.0f);
		/** distance 7 */
		gl.glTranslatef(0.0f, 7.0f, 0.0f);
		/** radius 1 */
		glu.gluSphere(glpQ, 1.0f, 20, 20);

		/** with its only moon */
		/** This moons material */
		someMaterials.setMaterialSilverMoon(gl);
		/** in YZ-pane (of planet) */
		gl.glRotatef(m_mV, 1.0f, 0.0f, 0.0f);
		/** distance 1.5 */
		gl.glTranslatef(0.0f, 1.5f, 0.0f);
		/** radius 0.5 */
		glu.gluSphere(glpQ, 0.5f, 20, 20);

		gl.glPopMatrix();
		gl.glPushMatrix();

		/** another planet */
		/** This planets material */
		someMaterials.setMaterialObsidianPlanet(gl);
		/** in XZ-plane */
		gl.glRotatef(m_pV2, 0.0f, 1.0f, 0.0f);
		/** distance 10 */
		gl.glTranslatef(10.0f, 0.0f, 0.0f);
		/** radius 1 */
		glu.gluSphere(glpQ, 1.0f, 20, 20);

		gl.glPushMatrix();

		/** with its first moon */
		/** These moons material */
		someMaterials.setMaterialWhiteMoon(gl);
		/** in XZ-pane (of planet) */
		gl.glRotatef(m_mV2, 0.0f, 1.0f, 0.0f);
		/** distance 3 */
		gl.glTranslatef(3.0f, 0.0f, 0.0f);
		/** radius 0.5 */
		glu.gluSphere(glpQ, 0.5f, 20, 20);

		gl.glPopMatrix();
		gl.glPushMatrix();

		/** and second moon */
		/** in XY-pane (of planet) */
		gl.glRotatef(m_mV2, 0.0f, 0.0f, 1.0f);
		/** distance 2 */
		gl.glTranslatef(2.0f, 0.0f, 0.0f);
		/** radius 0.5 */
		glu.gluSphere(glpQ, 0.5f, 20, 20);

		gl.glPopMatrix();

		gl.glPopMatrix();
		glu.gluDeleteQuadric(glpQ);
		// increment model movement
		m_Vz += 0.5f;
		if (m_Vz > 360.0f)
			m_Vz = 0.5f;
		m_Vx += 0.1f;
		if (m_Vx > 360.0f)
			m_Vx = 0.1f;
		m_pV += 0.1f;
		if (m_pV > 360.0f)
			m_pV = 0.1f;
		m_mV += 0.9f;
		if (m_mV > 360.0f)
			m_mV = 0.9f;
		m_pV2 += 0.2f;
		if (m_pV2 > 360.0f)
			m_pV2 = 0.2f;
		m_mV2 += 0.9f;
		if (m_mV2 > 360.0f)
			m_mV2 = 0.0f;
	}
	// eofplanets

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {

		System.out.println("mousePressed");

		if (e.getButton() == MouseEvent.BUTTON3) {
			if (m_animator.isAnimating())
				m_animator.stop();
			else
				m_animator.start();
		}
		oldMouseX = e.getX();
		oldMouseY = e.getY();
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Dimension size = e.getComponent().getSize();

		float thetaY = 360.0f * ((float) (x - oldMouseX) / (float) size.width);
		float thetaX = 360.0f * ((float) (oldMouseY - y) / (float) size.height);

		oldMouseX = x;
		oldMouseY = y;

		view_rotx += thetaX;
		view_roty += thetaY;
	}

	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

}
