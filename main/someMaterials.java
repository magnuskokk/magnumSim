package main;

import com.jogamp.opengl.GL2;
/**
 *
 * @author borres
 */
public class someMaterials {

  static void setMaterialGoldenSun(GL2 gl)
    {
        float amb[]={0.24725f, 0.1995f, 0.0745f,1.0f};
        float diff[]={0.75164f, 0.60648f, 0.22648f,1.0f};
        float spec[]={0.628281f, 0.555802f, 0.366065f,1.0f};
        float shine=0.4f;
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL2.GL_FRONT,GL2.GL_SHININESS,shine*128.0f);
    }

    static void setMaterialSilverMoon(GL2 gl)
    {
        float amb[]={0.19225f, 0.19225f, 0.19225f,1.0f};
        float diff[]={0.50754f, 0.50754f, 0.50754f,1.0f};
        float spec[]={0.508273f, 0.508273f, 0.508273f,1.0f};
        float shine=0.4f;
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL2.GL_FRONT,GL2.GL_SHININESS,shine*128.0f);
    }

    static void setMaterialGreenPlanet(GL2 gl)
    {
        float amb[]={0.0f, 0.0f, 0.0f,1.0f};
        float diff[]={0.1f, 0.35f, 0.1f,1.0f};
        float spec[]={0.45f, 0.55f, 0.45f, 1.0f};
        float shine=0.25f;
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL2.GL_FRONT,GL2.GL_SHININESS,shine*128.0f);
    }

    static void setMaterialObsidianPlanet(GL2 gl)
    {
        float amb[]={0.05375f, 0.05f, 0.06625f, 1.0f};
        float diff[]={0.18275f, 0.17f, 0.22525f,1.0f};
        float spec[]={0.332741f, 0.328634f, 0.346435f, 1.0f};
        float shine=0.3f;
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL2.GL_FRONT,GL2.GL_SHININESS,shine*128.0f);
    }

    static void setMaterialWhiteMoon(GL2 gl)
    {
        float amb[]={0.3f, 0.2f, 0.2f,  1.0f};
        float diff[]={1.0f, 0.9f, 0.8f,1.0f};
        float spec[]={0.4f, 0.2f, 0.2f,  1.0f};
        float shine=0.35f;
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL2.GL_FRONT,GL2.GL_SHININESS,shine*128.0f);
    }

}