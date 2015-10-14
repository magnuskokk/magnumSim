package main;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
  import com.jogamp.opengl.GLCapabilities;
  import com.jogamp.opengl.GLEventListener;
  import com.jogamp.opengl.GLProfile;
  import com.jogamp.opengl.awt.GLCanvas;
  import javax.swing.JFrame;
  
  public class Game extends JFrame implements GLEventListener {
     private static final long serialVersionUID = 1L;
 
     final private int width = 800;
     final private int height = 600;
     
     public void play() {
     }
 
     public Game() {
         super("Minimal OpenGL");
         GLProfile profile = GLProfile.getDefault();
         GLCapabilities capabilities = new GLCapabilities(profile);
 
         GLCanvas canvas = new GLCanvas(capabilities);
         canvas.addGLEventListener(this);
 
         this.setName("Minimal OpenGL");
         this.getContentPane().add(canvas);
 
         this.setSize(width, height);
         this.setLocationRelativeTo(null);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setVisible(true);
         this.setResizable(false);
         canvas.requestFocusInWindow();
     }

	@Override
	public void display(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}
}