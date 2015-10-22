package main;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import model.astronomy.Space;
import view.Camera;

public class Main implements Config, GLEventListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	private int oldMouseX;
	private int oldMouseY;

	private FPSAnimator m_animator = null;

	public Camera camera = null;

	public Space space;

	private long time0 = System.nanoTime();
	private double time = 0;
	private double lastTime = 0;
	private double dt = 0;

	public static void main(String[] args) {
		Frame frame = new Frame("Solar system");
		GLCanvas canvas = new GLCanvas();
		final FPSAnimator animator = new FPSAnimator(canvas, fps);

		GLEventListener listener = new Main(animator);

		// I have no idea how these mouselisteners work that way :D
		canvas.addMouseListener((MouseListener) listener);
		canvas.addMouseMotionListener((MouseMotionListener) listener);
		canvas.addMouseWheelListener((MouseWheelListener) listener);
		canvas.addKeyListener((KeyListener) listener);

		canvas.addGLEventListener(listener);
		frame.add(canvas);
		frame.setSize(1000, 750);

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

		this.camera = new Camera();
		this.space = new Space();

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

	/**
	 * This method is called in every frame
	 */
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU(); // needed for lookat
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_ACCUM_BUFFER_BIT);
		gl.glLoadIdentity();

		this.time = (System.nanoTime() - this.time0) / 1E9; // time in seconds
															// from the
															// beginning
		this.dt = this.time - this.lastTime; // time of last loop
		this.lastTime = this.time;

		this.camera.lookAt(glu);
		this.space.simulate(gl, this.dt / this.slowMotionRatio);

		gl.glFlush();
	}

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

		if (x > oldMouseX) {
			this.camera.rotate(1, this.dt);
		} else {
			this.camera.rotate(3, this.dt);
		}

		if (y < oldMouseY) {
			this.camera.rotate(0, this.dt);
		} else {
			this.camera.rotate(2, this.dt);
		}

		oldMouseX = e.getX();
		oldMouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		// int notches = e.getWheelRotation();
		//
		// if (notches < 0) {
		// camera.zoomIn();
		// } else {
		// camera.zoomOut();
		// }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_W:
			this.camera.move(0, this.dt);
			break;

		case KeyEvent.VK_D:
			this.camera.move(1, this.dt);
			break;

		case KeyEvent.VK_S:
			this.camera.move(2, this.dt);
			break;

		case KeyEvent.VK_A:
			this.camera.move(3, this.dt);
			break;

		case KeyEvent.VK_Q:
			this.camera.roll(-1);
			break;

		case KeyEvent.VK_E:
			this.camera.roll(1);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
