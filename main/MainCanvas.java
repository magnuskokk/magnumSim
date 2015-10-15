package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

/**
 * JOGL 2.0 Example 2: Rotating 3D Shapes (GLCanvas)
 */
// @SuppressWarnings("serial")
public class MainCanvas extends GLCanvas implements GLEventListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Setup OpenGL Graphics Renderer

	private GLU glu; // for the GL Utility
	private float anglePyramid = 0; // rotational angle in degree for pyramid
	private float angleCube = 0; // rotational angle in degree for cube
	private float speedPyramid = 1f; // rotational speed for pyramid
	private float speedCube = -1.5f; // rotational speed for cube

	/** Constructor to setup the GUI for this Component */
	public MainCanvas() {
		this.addGLEventListener(this);
	}

	// ------ Implement methods declared in GLEventListener ------

	/**
	 * Called back immediately after the OpenGL context is initialized. Can be
	 * used to perform one-time initialization. Run only once.
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		glu = new GLU(); // get GL Utilities
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f); // set clear depth value to farthest
		gl.glEnable(GL.GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL.GL_LEQUAL); // the type of depth test to do
		gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // best
																	// perspective
																	// correction
		gl.glShadeModel(gl.GL_SMOOTH); // blends colors nicely, and smoothes out
										// lighting
	}

	/**
	 * Call-back handler for window re-size event. Also called when the drawable
	 * is first set to visible.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		if (height == 0)
			height = 1; // prevent divide by zero
		float aspect = (float) width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(gl.GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear,
														// zFar

		// Enable the model-view transform
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}

	/**
	 * Called back by the animator to perform rendering.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT); // clear
																		// color
																		// and
																		// depth
																		// buffers

		// ----- Render the Pyramid -----
		gl.glLoadIdentity(); // reset the model-view matrix
		gl.glTranslatef(-1.6f, 0.0f, -6.0f); // translate left and into the
												// screen
		gl.glRotatef(anglePyramid, -0.0f, 1.0f, 0.0f); // rotate about the
														// y-axis

		gl.glBegin(gl.GL_TRIANGLES); // of the pyramid

		// Font-face triangle
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		// Right-face triangle
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex3f(1.0f, -1.0f, -1.0f);

		// Back-face triangle
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);

		// Left-face triangle
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		gl.glEnd(); // of the pyramid

		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // reset the current model-view matrix
		gl.glTranslatef(1.6f, 0.0f, -7.0f); // translate right and into the
											// screen
		gl.glRotatef(angleCube, 1.0f, 1.0f, 1.0f); // rotate about the x, y and
													// z-axes

		gl.glBegin(gl.GL_QUADS); // of the color cube

		// Top-face
		gl.glColor3f(0.0f, 1.0f, 0.0f); // green
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);

		// Bottom-face
		gl.glColor3f(1.0f, 0.5f, 0.0f); // orange
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);

		// Front-face
		gl.glColor3f(1.0f, 0.0f, 0.0f); // red
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		// Back-face
		gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);

		// Left-face
		gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		// Right-face
		gl.glColor3f(1.0f, 0.0f, 1.0f); // magenta
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);

		gl.glEnd(); // of the color cube

		// Update the rotational angle after each refresh.
		anglePyramid += speedPyramid;
		angleCube += speedCube;
	}

	/**
	 * Called back before the OpenGL context is destroyed. Release resource such
	 * as buffers.
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}