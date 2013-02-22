/*
 * Created on 26/12/2006
 */
package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.Animator;

/**
 * @author Demián Gutierrez
 */
public class Main implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {
	private float view_zoom = 1;
	double[] camera_center  = {0.0,0.0,0.0};
	double[] camera_rot = {0.0, 0.0};
	double camera_radius;

	private int prevMouseX = 0, prevMouseY = 0;
	private GLU glu = new GLU();

	private Part part;
	Integer lId = null;

	// ***************************************
	// XXX: Change according the real location
	public static final String ldraw_base = "ldraw"; // LDraw home
	public static final String model_base = "model"; // Where to the
																				// models to be
																				// displayed

	// ***************************************

	public Main(String[] args) throws Exception {
		String fs = System.getProperty("file.separator");

		PartLoader partLoader = PartLoader.getInstance();

		partLoader.getPath().add(ldraw_base + fs + "p");
		partLoader.getPath().add(ldraw_base + fs + "parts");
		partLoader.getPath().add(ldraw_base + fs + "parts" + fs + "s");
		partLoader.getPath().add(model_base);
		if(args.length == 0) {
			reload();
		} else {
			File file = new File(args[0]);
			String absolutePath = file.getAbsolutePath();
			String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));

			partLoader.getPath().add(filePath);
			part = partLoader.loadPart(file.getName(), true);
		}
	}

	public void init(GLAutoDrawable drawable) {
		((Component) drawable).addMouseListener(this);
		((Component) drawable).addMouseMotionListener(this);
		((Component) drawable).addKeyListener(this);

		GL2 gl = drawable.getGL().getGL2();

		gl.glDisable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		// ??? I really need to understand this better
		glu.gluPerspective(35, 1, 1, 3200);
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		if ((drawable instanceof GLJPanel) && !((GLJPanel) drawable).isOpaque() && ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
			gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		} else {
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		}
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		camera_radius = 200 * view_zoom;
		
		double[] camera_pos = {
		    camera_center[0] + camera_radius*-Math.sin(Math.toRadians(camera_rot[0]))*Math.cos(Math.toRadians(camera_rot[1])),
		    camera_center[1] + camera_radius*-Math.sin(Math.toRadians(camera_rot[1])),
		    camera_center[2] + camera_radius*-Math.cos(Math.toRadians(camera_rot[0]))*Math.cos(Math.toRadians(camera_rot[1]))  
		};

		// maybe not the best solution but it works
		if(camera_rot[1] > 90 && camera_rot[1] <= 270) {
			glu.gluLookAt(
			    camera_pos[0], camera_pos[1], camera_pos[2],
			    camera_center[0],camera_center[1],camera_center[2],
			    0,1,0
			);
		} else {
			glu.gluLookAt(
			    camera_pos[0], camera_pos[1], camera_pos[2],
			    camera_center[0],camera_center[1],camera_center[2],
			    0,-1,0
			);	    
		}
		
		if (lId == null) {
			lId = new Integer(1);
			gl.glNewList(lId.intValue(), GL2.GL_COMPILE);

			try {
				part.render(gl, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}

			gl.glEndList();
		}

		gl.glCallList(lId.intValue());
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// Empty
	}

	public void mouseEntered(MouseEvent e) {
		// Empty
	}

	public void mouseExited(MouseEvent e) {
		// Empty
	}

	public void mousePressed(MouseEvent e) {
		prevMouseX = e.getX();
		prevMouseY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {	
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Dimension size = e.getComponent().getSize();

		double thetaX = 360.0f * ((double) (x - prevMouseX) / (double) size.width);
		double thetaY = 360.0f * ((double) (prevMouseY - y) / (double) size.height);

		prevMouseX = e.getX();
		prevMouseY = e.getY();
		
		double temp_rotX = camera_rot[0] + thetaX;
		temp_rotX += (temp_rotX < 0)? 360 : 0;
		temp_rotX -= (temp_rotX > 360) ? 360 : 0;
		double temp_rotY = camera_rot[1] - thetaY;
		temp_rotY += (temp_rotY < 0)? 360 : 0;
		temp_rotY -= (temp_rotY > 360) ? 360 : 0;
		
		camera_rot[0] = temp_rotX ;
		camera_rot[1] = temp_rotY ;
	}

	public void mouseMoved(MouseEvent e) {
		// Empty
	}

	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case KeyEvent.VK_Z:
			view_zoom *= 2;
			System.err.println("Zoom: " + view_zoom);
			break;
		case KeyEvent.VK_A:
			System.err.println("Zoom: " + view_zoom);
			view_zoom /= 2;
			break;
		case KeyEvent.VK_R:
			System.err.println("r");
			reload();
			break;
		}
	}

	public void keyPressed(KeyEvent e) {
		// Empty
	}

	public void keyReleased(KeyEvent e) {
		// Empty
	}

	private void reload() {
		String name = null;
		//		name = "3004.DAT"; // Boring, just a lego part for testing
		//		name = "3034.DAT"; // Boring, just a lego part for testing
		//		name = "3062B.DAT"; // Boring, just a lego part for testing
		//		name = "3298P90.DAT"; // Boring, just a lego part for testing
		//		name = "3298s01.dat"; // Boring, just a lego part for testing
		//		name = "3839A.dat"; // Boring, just a lego part for testing
		//		name = "4-4cyli.dat"; // Boring, just a lego part for testing
		//		name = "3829b.DAT"; // Boring, just a lego part for testing
		//		name = "3829a.DAT"; // Boring, just a lego part for testing
		//		name = "3829.DAT"; // Boring, just a lego part for testing
		//		name = "1499-1.mpd"; // Medium twin seat spaceship
		//		name = "885-1.mpd"; // Very small spaceship (5)
		//		name = "487-1.mpd"; // Medium Ship with deployable car
		//		name = "6804-1.mpd"; // Very small car (2)
		//		name = "6807-1.mpd"; // Very (very) small spaceship with robot
		//		name = "6824.mpd"; // Very small spaceship (4)
		//		name = "6872-1.mpd"; // Medium ship with small robot
		//		name = "6890-1.mpd"; // Medium ship with deployable small ship
		//		name = "6926-1.mpd"; // Nice blue car (1)
		//		name = "6928-1.mpd"; // Nice blue car (2)
		//		name = "6950-1.mpd"; // Childhood car & rocket
		//		name = "6985.mpd"; // Big spaceship
		//		name = "886-1.mpd"; // Very small car (1)
		//		name = "1580-1.mpd"; // Medium car //XXX: BUG!!!!!
		//		name = "6801-1.mpd"; // Very small spaceship (1)
		//		name = "6806-1.mpd"; // Very small spaceship (2)
		//		name = "6821-1.mpd"; // Small-med Bulldozer Car (1)
		//		name = "6847-1.mpd"; // Small-med Bulldozer Car (2)
		//		name = "6881-1.mpd"; // Small-med satelite-rocket launcher
		//		name = "6891.mpd"; // Medium Ship
		//		name = "6927-1.mpd"; // Medium car with rear deployable module
		//		name = "6929-1.mpd"; // Medium Childhood Ship
		//		name = "6972-1.mpd"; // Big lunar station small ship launcher
		//		name = "885-1.mpd"; // Very small spaceship (3)
		//		name = "889-1.mpd"; // Very small car with satelite
		//		name = "train.ldr"; // front part of train
		
		reload(name);
	}
	
	public void reload(String name) {
		lId = null;
		PartLoader partLoader = PartLoader.getInstance();
		try {
			part = partLoader.loadPart(name, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		final Frame frame = new Frame("Lego Demo");
		final GLCanvas canvas = new GLCanvas();
		final Animator animator = new Animator(canvas);

		// TODO: Put in fs mode
		// TODO: Refactor to a lego panel
		canvas.addGLEventListener(new Main(args));
		frame.add(canvas);
		frame.setSize(900, 900);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});

		frame.setVisible(true);
		animator.start();
	}

	public void dispose(GLAutoDrawable drawable) {
	}
}
