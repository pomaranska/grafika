package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.*;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * SimpleJOGL.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Animacja implements GLEventListener {
    Ludzik ludzik=new Ludzik();

    private static float xrot = 0.0f, yrot = 0.0f, zrot = 0.0f;

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Animacja());
        frame.add(canvas);
        frame.setSize(640, 600);
        final Animator animator = new Animator(canvas);
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

//Obs�uga klawiszy strza�ek 
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    xrot -= 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    xrot += 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    yrot += 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    yrot -= 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    zrot += 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    zrot -= 1.0f;
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
// Center frame 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
// Use debug pipeline 
// drawable.setGL(new DebugGL(drawable.getGL())); 

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

// Enable VSync 
        gl.setSwapInterval(1);

//warto�ci sk�adowe o�wietlenia i koordynaty �r�d�a �wiat�a 
        float ambientLight[] = {0.3f, 0.3f, 0.3f, 1.0f};//swiat�o otaczaj�ce 
        float diffuseLight[] = {0.7f, 0.7f, 0.7f, 1.0f};//�wiat�o rozproszone 
        float specular[] = {1.0f, 1.0f, 1.0f, 1.0f}; //�wiat�o odbite 
        float lightPos[] = {0.0f, 150.0f, 150.0f, 1.0f};//pozycja �wiat�a 
//(czwarty parametr okre�la odleg�o�� �r�d�a: 
//0.0f-niesko�czona; 1.0f-okre�lona przez pozosta�e parametry) 
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o�wietlenia 
//ustawienie parametr�w �r�d�a �wiat�a nr. 0 
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0); //swiat�o otaczaj�ce 
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0); //�wiat�o rozproszone 
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0); //�wiat�o odbite 
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0); //pozycja �wiat�a 
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie �r�d�a �wiat�a nr. 0 
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie �ledzenia kolor�w 
//kolory b�d� ustalane za pomoc� glColor 
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
//Ustawienie jasno�ci i odblaskowo�ci obiekt�w 
        float specref[] = {1.0f, 1.0f, 1.0f, 1.0f}; //parametry odblaskowo�ci 
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specref, 0);

        gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 128);

        gl.glEnable(GL.GL_DEPTH_TEST);
// Setup the drawing area and shading mode 
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens. 
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error! 

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
// glu.gluPerspective(120.0f, h, 1.0, 20.0); //transformacja perspektywiczna 
        float ilor;
        if (width <= height) {
            ilor = height / width;
            gl.glOrtho(-10.0f, 10.0f, -10.0f * ilor, 10.0f * ilor, -10.0f, 10.0f);
        } else {
            ilor = width / height;
            gl.glOrtho(-10.0f * ilor, 10.0f * ilor, -10.0f, 10.0f, -10.0f, 10.0f);
        }
//� gl.glViewport(width/2, height/2, width, height);� //transformacja wycinaj�ca 

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

// Clear the drawing area 
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
// Reset the current matrix to the "identity" 
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -4.0f); //przesuni�cie o 6 jednostek 
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wok� osi X 
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wok� osi Z 
        gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f); //rotacja wok� osi Z 
        
        ludzik.rysujLudzika(gl);
        

// Flush all drawing operations to the graphics card 
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

  

   

   
}
