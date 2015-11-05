package eu.kokk;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import eu.kokk.model.astronomy.Space;
import eu.kokk.view.Camera;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class Main implements Config, GLEventListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    public static Space space;
    public static GL2 gl;
    public static GLU glu;
    // Which keys are currently pressed (to enable "flying")
    private final Set<Integer> keysPressed = new HashSet<>();
    public Camera camera = null;
    private int oldMouseX;
    private int oldMouseY;
    private FPSAnimator m_animator = null;
    private long time0 = System.nanoTime();
    private double time = 0;
    private double lastTime = 0;
    private double dt = 0;

    public Main(FPSAnimator animator) {
        m_animator = animator;
    }

    public static void main(String[] args) {
        Frame frame = new Frame("Solar system");
        GLCanvas canvas = new GLCanvas();
        final FPSAnimator animator = new FPSAnimator(canvas, fps);

        GLEventListener listener = new Main(animator);

        canvas.addMouseListener((MouseListener) listener);
        canvas.addMouseMotionListener((MouseMotionListener) listener);
        canvas.addMouseWheelListener((MouseWheelListener) listener);
        canvas.addKeyListener((KeyListener) listener);

        canvas.addGLEventListener(listener);
        frame.add(canvas);
        frame.setSize(Config.frameWidth, Config.frameHeight);

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

    /**
     * This method is called in the beginning of rendering
     *
     * @param drawable Rendering context
     */
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        glu = new GLU();

        // prepare ligthsource
        float ambient[] = {0.4f, 0.4f, 0.4f, 1.0f};
        float diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float position[] = {1.0f, 1.0f, 1.0f, 0.0f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);

        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHTING);

        // smooth the drawing
        gl.glShadeModel(GL2.GL_SMOOTH);

        // depth sorting
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LESS);

        // set background to black
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glEnable(GL2.GL_NORMALIZE);

        this.camera = new Camera();
        space = new Space();
    }

    /**
     * This method is called when the window is resized
     *
     * @param drawable Rendering context
     * @param x
     * @param y
     * @param width    New width
     * @param height   New Height
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        if (height <= 0) // no divide by zero
        {
            height = 1;
        }

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
     *
     * @param drawable Rendering context
     */
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_ACCUM_BUFFER_BIT);
        gl.glClearColor(0, 0, 0, 1);
        gl.glLoadIdentity();

        for (Iterator<Integer> i = keysPressed.iterator(); i.hasNext(); ) {
            this.doActionWhenKeyPressed(i.next());
        }

        this.time = (System.nanoTime() - this.time0) / 1E9; // time in seconds
        // from the
        // beginning
        this.dt = this.time - this.lastTime; // time of last loop
        this.lastTime = this.time;

        Main.space.simulate(this.dt * Config.speedPercent/100);

        this.camera.setCamera();

        gl.glFlush();
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
        //TODO: this is buggy atm
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (m_animator.isAnimating()) {
                m_animator.stop();
            } else {
                m_animator.start();
            }
        }
        oldMouseX = e.getX();
        oldMouseY = e.getY();
    }

    /**
     * Dragging the mouse rotates the camera
     *
     * @param e Mouse event
     */
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (x > oldMouseX) {
            this.camera.rotate(1);
        } else {
            this.camera.rotate(3);
        }

        if (y < oldMouseY) {
            this.camera.rotate(0);
        } else {
            this.camera.rotate(2);
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

    /**
     * WASD keys are used for movement, QE for camera roll
     *
     * @param e Key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        keysPressed.add(keyCode);


    }

    /**
     * @param keyCode
     */
    public void doActionWhenKeyPressed(int keyCode) {
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

            case KeyEvent.VK_UP:
                this.camera.rotate(2);
                break;

            case KeyEvent.VK_RIGHT:
                this.camera.rotate(1);
                break;

            case KeyEvent.VK_DOWN:
                this.camera.rotate(0);
                break;

            case KeyEvent.VK_LEFT:
                this.camera.rotate(3);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        keysPressed.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
