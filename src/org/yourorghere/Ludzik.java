package org.yourorghere;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.*;


public class Ludzik {
    
    public void rysujLudzika(GL gl){
        //glowa
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glTranslatef(0.0f, 2.0f, 0.0f);
        gl.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
        kula(gl);
        //tu³ów
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glTranslatef(-1.0f, -1.0f, 0.0f);
        gl.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
        Prostopadloscian(gl, 0.0f, 0.0f, -0.5f, 2.0f, -3.0f, 1.0f);
        //prawa reka
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glTranslatef(2.0f, -1.0f, -0.3f);
        gl.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
        Prostopadloscian(gl, 0.0f, 0.0f, 0.0f, 2.0f, 0.3f, 0.3f);
        //lewa reka
        
//        gl.glTranslatef(-4.0f, -2.0f, -0.0f);
//        gl.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
//        Prostopadloscian(gl, 0.0f, 0.0f, 0.0f, 2.0f, 0.3f, 0.3f);
    }

   

    private void Prostopadloscian(GL gl, float x0, float y0, float z0,
            float dx, float dy, float dz) {
        float x1 = x0 + dx;
        float y1 = y0 + dy;
        float z1 = z0 + dz;
        gl.glBegin(GL.GL_QUADS);
//sciana przednia
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(x0, y0, z1);
        gl.glVertex3f(x1, y0, z1);
        gl.glVertex3f(x1, y1, z1);
        gl.glVertex3f(x0, y1, z1);
//sciana tylnia
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        gl.glVertex3f(x0, y1, z0);
        gl.glVertex3f(x1, y1, z0);
        gl.glVertex3f(x1, y0, z0);
        gl.glVertex3f(x0, y0, z0);
//sciana lewa
        gl.glNormal3f(-1.0f, 0.0f, 0.0f);
        gl.glVertex3f(x0, y0, z0);
        gl.glVertex3f(x0, y0, z1);
        gl.glVertex3f(x0, y1, z1);
        gl.glVertex3f(x0, y1, z0);
//sciana prawa
        gl.glNormal3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(x1, y1, z0);
        gl.glVertex3f(x1, y1, z1);
        gl.glVertex3f(x1, y0, z1);
        gl.glVertex3f(x1, y0, z0);
//sciana dolna
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glVertex3f(x0, y0, z1);
        gl.glVertex3f(x0, y0, z0);
        gl.glVertex3f(x1, y0, z0);
        gl.glVertex3f(x1, y0, z1);
//sciana gorna
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(x1, y1, z1);
        gl.glVertex3f(x1, y1, z0);
        gl.glVertex3f(x0, y1, z0);
        gl.glVertex3f(x0, y1, z1);
        gl.glEnd();
    }

    private void Walec(GL gl, float promien, float dlugosc,
            float px, float py, float pz) {
        float x = 0.0f, y = 0.0f, kat = 0.0f;
        gl.glBegin(GL.GL_QUAD_STRIP);
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = px + promien * (float) Math.sin(kat);
            y = py + promien * (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), 0.0f);
            gl.glVertex3f(x, y, pz);
            gl.glVertex3f(x, y, pz + dlugosc);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(px, py, pz); //srodek kola
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = px + promien * (float) Math.sin(kat);
            y = py + promien * (float) Math.cos(kat);
            gl.glVertex3f(x, y, pz);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(px, py, pz + dlugosc); //srodek kola
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = px + promien * (float) Math.sin(kat);
            y = py + promien * (float) Math.cos(kat);
            gl.glVertex3f(x, y, pz + dlugosc);
        }
        gl.glEnd();
    }


    
     void stozek(GL gl) {
//wywo³ujemy automatyczne normalizowanie normalnych 
        gl.glEnable(GL.GL_NORMALIZE);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        float x, y, kat;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -2.0f); //wierzcholek stozka 
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = (float) Math.sin(kat);
            y = (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), -2.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola 
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = (float) Math.sin(kat);
            y = (float) Math.cos(kat);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
    }
     
     
      void choinka(GL gl) {
     //   Walec(gl);
        gl.glTranslatef(0.0f, 0.0f, -1.0f);
        stozek(gl);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glTranslatef(0.0f, 0.0f, -2.5f);
        stozek(gl);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glTranslatef(0.0f, 0.0f, -2.5f);
        stozek(gl);
    }

    void las(GL gl) {
        for (int i = 0; i < 10; i++) {
            gl.glPushMatrix();
            choinka(gl);
            gl.glPopMatrix();
            gl.glTranslatef(0.0f, -3.0f, 0.0f);
        }
    }
    void kula(GL gl){
         GLUT glut = new GLUT();
         glut.glutSolidSphere(1.0f, 32, 10);
    }
}
