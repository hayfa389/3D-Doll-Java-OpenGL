package org.yourorghere;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;



/**
 * DrawDoll.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class DrawDoll {

    //presisi dan variabel global
    private static final int SLICES=40;
    private static final int STACKS=40;
    private GLUquadric q=null;
    private static int mvt=0;

    //tinggi dan lebar masing-masing komponen
    private static final float HEIGHT_BODY=0.5f;
    private static final float TOP_BODY=0.35f;
    private static final float BOTTOM_BODY=0.40f;
    private static final float HEIGHT_LEGS=0.2f;
    private static final float WIDTH_LEGS=0.185f;
    private static final float HEIGHT_ARMS=0.22f;
    private static final float WIDTH_ARMS=0.09f;
    private static final float WIDTH_HEAD=0.5f;
    private static final float WIDTH_EYES=0.3f;
    private static final float WIDTH_HANDS=0.1f;
    private static final float WIDTH_FINGERS=0.0525f;
    private static final float WIDTH_SHOES=0.28f;
    private static final float HEIGHT_SHOES=0.05f;
    private static final float WIDTH_OPEN_MOUTH=0.1f;
    private static final float WIDTH_BUTTONS=0.0525f;
    private static final float SPACE_BETWEEN_BUTTONS=0.12f;
    private static final float WIDTH_BOTTOM_BONNET=0.525f;
    private static final float HEIGHT_BOTTOM_BONNET=0.12f;
    private static final float WIDTH_BONNET=0.52f;
    private static final float WIDTH_PUPILS=0.03f;
    private static final float WIDTH_POMPON=0.12f;

    //posisi setiap komponen di jendela
    public DrawDoll(){}

    public void draw_doll (GL gl, boolean walk, boolean jump){

        GLU glu = new GLU();
        q=glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
        glu.gluQuadricOrientation(q, GLU.GLU_OUTSIDE);
        glu.gluQuadricNormals(q, GLU.GLU_SMOOTH);

        //Boneka sedang berjalan
        if(walk && mvt%20+10>20){
            draw_legs(gl, glu, 'W', false);
            draw_legs(gl, glu, ' ', true);
            draw_arm_left(gl, glu, ' ');
            draw_arm_right(gl, glu, 'W');
            draw_head (gl, glu, false);
        }
        else if(walk && mvt%20+10<=20){
            draw_legs(gl, glu, ' ', false);
            draw_legs(gl, glu, 'W', true);
            draw_arm_left(gl, glu, 'W');
            draw_arm_right(gl, glu, ' ');
            draw_head (gl, glu, false);
        }

       //Boneka melompat
        else if(jump && mvt%20+10>20){
            gl.glTranslatef(0f, 0.35f, 0f);
            draw_legs(gl, glu, 'J', false);
            draw_legs(gl, glu, 'J', true);
            draw_arm_left(gl, glu, 'J');
            draw_arm_right(gl, glu, 'J');
            draw_head (gl, glu, true);
        }

        //boneka dalam bentuk normal
        else{
            draw_legs(gl, glu, ' ', false);
            draw_legs(gl, glu, ' ', true);
            draw_arm_left(gl, glu, ' ');
            draw_arm_right(gl, glu, ' ');
            draw_head (gl, glu, false);
        }

        mvt++;
        draw_body (gl, glu);
        draw_scarf (gl, glu);
        draw_hat (gl, glu);

    }


    public void draw_body (GL gl, GLU glu){

        //membuat badan boneka
        set_shirt_material(gl);
        gl.glPushMatrix();
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluCylinder(q, TOP_BODY, BOTTOM_BODY, HEIGHT_BODY, SLICES, STACKS);
        gl.glRotatef(90f, -1f, 0f, 0f);
        gl.glTranslatef(0.0f, -HEIGHT_BODY, 0f);
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluDisk(q, 0f, BOTTOM_BODY, SLICES, STACKS);
        gl.glPopMatrix();

        //membuat retsleting boneka
        set_grey_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.475f, 0.40f);
        gl.glRotatef(-6f, 1.0f, 0f, 0f);
        gl.glBegin(GL.GL_LINES);
        gl.glNormal3f(-1.0f,0.0f,0.0f);
        gl.glVertex3f(0.0f,0.0f,0.0f);
        gl.glVertex3f(0.0f,0.44f,0.0f);
        gl.glEnd();
        gl.glPopMatrix();

        //membuat kancing baju boneka
        gl.glPushMatrix();
        gl.glTranslatef(-0.06f, -0.15f, 0.315f);
        glu.gluSphere(q, WIDTH_BUTTONS, SLICES, STACKS);
        gl.glTranslatef(0.0f, -SPACE_BETWEEN_BUTTONS, 0.012f);
        glu.gluSphere(q, WIDTH_BUTTONS, SLICES, STACKS);
        gl.glTranslatef(0.0f, -SPACE_BETWEEN_BUTTONS, 0.013f);
        glu.gluSphere(q, WIDTH_BUTTONS, SLICES, STACKS);
        gl.glPopMatrix();

    }

    public void draw_head (GL gl, GLU glu, boolean jump){

        //membuat kepala boneka
        set_skin_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.5f, 0f);
        glu.gluSphere(q, WIDTH_HEAD, SLICES, STACKS);
        gl.glPopMatrix();

        //membuat mata (putih) boneka
        set_eyes_material(gl);

        gl.glPushMatrix();
        gl.glTranslatef(-0.08f, 0.45f, 0.205f);
        glu.gluSphere(q, WIDTH_EYES, SLICES, STACKS);
        gl.glTranslatef(0.16f, 0.0f, 0.0f);
        glu.gluSphere(q, WIDTH_EYES, SLICES, STACKS);
        gl.glPopMatrix();

        //membuat mulut boneka
        gl.glPushMatrix();
        set_black_material(gl);
        //Mulut berbeda saat boneka melompat
        if (jump==true){
            set_grey_material(gl);
            gl.glTranslatef(0.0f, 0.19f, 0.275f);
            glu.gluSphere(q, WIDTH_OPEN_MOUTH, SLICES, STACKS);
        }
        else{
            gl.glTranslatef(0.1f, 0.19f, 0.257f);
            gl.glRotatef(45f, 1f, 0f, 0f);
            gl.glScalef(-0.2f, 0.03f, 0.1f);
            box(gl);
        }
        gl.glPopMatrix();

        //membuat mata (hitam) boneka
        set_grey_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(-0.07f, 0.35f, 0.468f);
        glu.gluSphere(q, WIDTH_PUPILS, SLICES, STACKS);
        gl.glTranslatef(0.14f, 0f, 0f);
        glu.gluSphere(q, WIDTH_PUPILS, SLICES, STACKS);
        gl.glPopMatrix();

    }


     public void draw_hat (GL gl, GLU glu){

        //membuat bagian bawah kap mesin
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.58f, -0.01f);
        gl.glRotatef(80f, 1f, 0f, 0f);
        glu.gluCylinder(q, WIDTH_BOTTOM_BONNET, WIDTH_BOTTOM_BONNET, HEIGHT_BOTTOM_BONNET, 100, STACKS);
        gl.glRotatef(80f, -1f, 0f, 0f);
        gl.glTranslatef(0.0f, -HEIGHT_BOTTOM_BONNET+0.005f, 0.02f);
        gl.glRotatef(80f, 1f, 0f, 0f);
        glu.gluDisk(q, 0f, 0.527, SLICES, STACKS);
        gl.glPopMatrix();
        //membuat kap mesin
        set_blue_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.5f, 0.0f);
        gl.glRotatef(350f, 1f, 0f, 0f);
        double[] cutplane=new double[]{0.0f,1.0f,0.0f,0.0f};
        gl.glClipPlane(GL.GL_CLIP_PLANE2,cutplane,0);
        gl.glEnable(GL.GL_CLIP_PLANE2);
        glu.gluSphere(q, WIDTH_BONNET, 100, 100);
        gl.glDisable(GL.GL_CLIP_PLANE2);
        gl.glPopMatrix();

        // membuat pompon
        set_red_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.05f, -0.1f);
        glu.gluSphere(q, WIDTH_POMPON, SLICES, STACKS);
        gl.glPopMatrix();

     }


     public void draw_legs(GL gl, GLU glu, char c, boolean left){
        gl.glPushMatrix();

        //mengorientasikan sumbu jika boneka melompat atau berjalan
        if (c=='W'){
            gl.glTranslatef(0f, -0.1f, -0.2f);
            gl.glRotatef(30, -100f, 0f, 0f);
        }

        if (c=='J'){
            gl.glTranslatef(0f, -0.05f, -0.1f);
            if (left)
                gl.glRotatef(30, -100f, -100f, 0f);
            else
                gl.glRotatef(30, -100f, 100f, 0f);
        }

        //membuat kaki
        set_blue_material(gl);
        gl.glPushMatrix();
        if (left)
            gl.glTranslatef(-0.19f, -0.45f, 0f);
        else
            gl.glTranslatef(0.19f, -0.45f, 0f);
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluCylinder(q, WIDTH_LEGS, WIDTH_LEGS, HEIGHT_LEGS, SLICES, STACKS);
        glu.gluSphere(q, WIDTH_LEGS, SLICES, STACKS);
        gl.glRotatef(90f, -1f, 0f, 0f);
        gl.glTranslatef(0f, -HEIGHT_LEGS, 0f);
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluDisk(q, 0f, WIDTH_LEGS, SLICES, STACKS);
        gl.glPopMatrix();

        //membuat sepatu
        set_black_material(gl);
        gl.glPushMatrix();
        if (left){
            gl.glTranslatef(-0.34f, -0.7f, -0.15f);
            gl.glScalef(WIDTH_SHOES, HEIGHT_SHOES, 0.25f);
        }
        else{
            gl.glTranslatef(0.34f, -0.7f, -0.15f);
            gl.glScalef(-WIDTH_SHOES, HEIGHT_SHOES, 0.25f);
        }
        box(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        if (left)
            gl.glTranslatef(-0.2f, -0.65f, 0.12f);
        else
            gl.glTranslatef(0.2f, -0.65f, 0.12f);
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluDisk(q, 0f, 0.14, SLICES, STACKS);
        glu.gluCylinder(q, WIDTH_SHOES/2, WIDTH_SHOES/2, HEIGHT_SHOES, SLICES, STACKS);
        gl.glRotatef(90f, -1f, 0f, 0f);
        gl.glTranslatef(0f, -HEIGHT_SHOES, 0f);
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluDisk(q, 0f, WIDTH_SHOES/2, SLICES, STACKS);
        gl.glPopMatrix();

        gl.glPopMatrix();
     }


     public void draw_arm_left (GL gl, GLU glu, char c){
        set_red_material(gl);
        gl.glPushMatrix();
        //mengorientasikan sumbu jika boneka berjalan atau melompat
        if (c=='J'){
            gl.glTranslatef(-0.47f, 0.15f, -0.01f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(0.05f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        if (c=='W'){
            gl.glTranslatef(-0.45f, -0.38f, 0.1f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(0.05f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        if (c==' '){
            gl.glTranslatef(-0.45f, -0.42f, 0f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(0.055f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }

        gl.glPopMatrix();


        //buat lengan kiri
        set_shirt_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(-0.37f, -0.125f, 0f);
        gl.glRotatef(90f, 1f, -0.20f, 0f);
        if (c=='J')
            gl.glRotatef(150, 0f, -100f, 0f);
        if (c=='W')
            gl.glRotatef(20, -1f, 0f, 0f);
        glu.gluCylinder(q, WIDTH_ARMS, WIDTH_ARMS, HEIGHT_ARMS, SLICES, STACKS);
        glu.gluSphere(q, WIDTH_ARMS, SLICES, STACKS);
        gl.glRotatef(90f, -1f, 0.20f, 0f);
        gl.glTranslatef(0f, -HEIGHT_ARMS, 0f);
        gl.glRotatef(90f, 1f, -0.20f, 0f);
        if (c!='J')
            glu.gluDisk(q, 0f, WIDTH_ARMS, SLICES, STACKS);
        gl.glPopMatrix();
     }


     public void draw_arm_right (GL gl, GLU glu, char c){

        set_red_material(gl);
        gl.glPushMatrix();

        //mengorientasikan sumbu jika boneka berjalan atau melompat
        if (c=='J'){
            gl.glTranslatef(0.47f, 0.15f, -0.01f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(-0.05f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        if (c=='W'){
            gl.glTranslatef(0.45f, -0.38f, 0.1f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(-0.05f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        if (c==' '){
            gl.glTranslatef(0.45f, -0.42f, 0f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(-0.055f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }

        gl.glPopMatrix();
        //membuat lengan kanan
        set_shirt_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.37f, -0.125f, 0f);
        gl.glRotatef(90f, 1f, 0.20f, 0f);
        if (c=='J')
            gl.glRotatef(150, 0f, 100f, 0f);
        if (c=='W')
            gl.glRotatef(20, -1f, 0f, 0f);
        glu.gluCylinder(q, WIDTH_ARMS, WIDTH_ARMS, HEIGHT_ARMS, SLICES, STACKS);
        glu.gluSphere(q, WIDTH_ARMS, SLICES, STACKS);
        gl.glRotatef(90f, -1f, -0.20f, 0f);
        gl.glTranslatef(0f, -HEIGHT_ARMS, 0f);
        gl.glRotatef(90f, 1f, 0.20f, 0f);
        if (c==' ')
            glu.gluDisk(q, 0f, WIDTH_ARMS, SLICES, STACKS);
        gl.glPopMatrix();
     }


     public void draw_scarf (GL gl, GLU glu){

        //membuat syal
        set_red_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0f, 0.0f);
        gl.glRotatef(93f, 1f, 0f, 0f);
        draw_torus(gl, 0.32f, 0.092f, SLICES, STACKS);
        gl.glPopMatrix();

     }


     public void draw_torus (GL gl, float R, float r, int N, int n){

        int maxn= 1000;
        n=Math.min(n,maxn-1);
        N=Math.min(N,maxn-1);
        float rr=1.5f*r;
        double dv=2*Math.PI/n;
        double dw=2*Math.PI/N;
        double v=0.0f;
        double w=0.0f;
        while(w<2*Math.PI+dw)
        {
            v=0.0f;
            gl.glBegin(GL.GL_TRIANGLE_STRIP);
            while(v<2*Math.PI+dv)
            {
                gl.glNormal3d(
                        (R+rr*Math.cos(v))*Math.cos(w)-(R+r*Math.cos(v))*Math.cos(w),
                        (R+rr*Math.cos(v))*Math.sin(w)-(R+r*Math.cos(v))*Math.sin(w),
                        (rr*Math.sin(v)-r*Math.sin(v)));
                gl.glVertex3d((R+r*Math.cos(v))*Math.cos(w),
                           (R+r*Math.cos(v))*Math.sin(w),
                            r*Math.sin(v));
                gl.glNormal3d(
                        (R+rr*Math.cos(v+dv))*Math.cos(w+dw)-(R+r*Math.cos(v+dv))*Math.cos(w+dw),
                        (R+rr*Math.cos(v+dv))*Math.sin(w+dw)-(R+r*Math.cos(v+dv))*Math.sin(w+dw),
                        rr*Math.sin(v+dv)-r*Math.sin(v+dv));
                gl.glVertex3d((R+r*Math.cos(v+dv))*Math.cos(w+dw),
                           (R+r*Math.cos(v+dv))*Math.sin(w+dw),
                            r*Math.sin(v+dv));
                v+=dv;
            }
            gl.glEnd();
            w+=dw;
        }
    }


    public void set_skin_material (GL gl){
        float[] mat_ambient ={1.0f,0.79f, 0.68f, 0.0f};
        float[] mat_diffuse ={0.59f,0.44f,0.41f,0.0f};
        float shine = 128f;

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,mat_diffuse,0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);

    }
    

    public void set_shirt_material (GL gl){

        float mat_ambient[]={ 0.5f, 0.45f, 0.3f, 1.0f };
        float[] mat_diffuse ={ 0.8f,0.8f,0.8f,1.0f};
        float mat_specular[]={ 0.4f, 0.3f, 0.2f, 1.0f };
        float shine=128f;

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,mat_diffuse,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR,mat_specular,0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);

    }


    public void set_red_material (GL gl){

        float[] mat_ambient ={ 0.8f,0.05f,0.15f,0.2f };
        float[] mat_diffuse ={ 0.4f,0.4f,0.4f,1.0f};
        float[] mat_specular ={0.7f,0.6f,0.6f,1.0f };
        float shine =15.0f ;

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,mat_diffuse,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR,mat_specular,0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);

    }


    public void set_eyes_material (GL gl){

        float mat_ambient[]={1.0f,1.0f,1.0f,1.0f};
        float mat_diffuse[]={1.0f,1.0f,1.0f,1.0f};
        float mat_specular[]={0.8f,0.8f,0.8f,1.0f};
        float shine=51.2f;

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,mat_diffuse,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR,mat_specular,0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);

    }


    public void set_blue_material (GL gl){

        float mat_ambient[]={0.2f,0.2f,0.6f,1.0f};
        float mat_diffuse[]={1.0f,1.0f,1.0f,1.0f};
        float mat_specular[]={0.8f,0.8f,0.8f,1.0f};
        float shine=125.2f;

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,mat_diffuse,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR,mat_specular,0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);

    }


    public void set_grey_material (GL gl){

        float mat_ambient[]={0.07f,0.07f,0.07f,0.0f};
        float mat_diffuse[]={1.0f,1.0f,1.0f,1.0f};
        float mat_specular[]={0.8f,0.8f,0.8f,1.0f};
        float shine=125.2f;

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,mat_diffuse,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR,mat_specular,0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);

    }


    public void set_black_material (GL gl){

        float mat_ambient[]={0.0f,0.0f,0.0f,1.0f};
        float mat_diffuse[]={0.0f,0.0f,0.0f,1.0f};
        float mat_specular[]={0.0f,0.0f,0.0f,1.0f};
        float shine=125.2f;

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,mat_diffuse,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR,mat_specular,0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);

    }

    public void box (GL gl){
        gl.glBegin(GL.GL_POLYGON);/* f1: front */
            gl.glNormal3f(-1.0f,0.0f,0.0f);
            gl.glVertex3f(0.0f,0.0f,0.0f);
            gl.glVertex3f(0.0f,0.0f,1.0f);
            gl.glVertex3f(1.0f,0.0f,1.0f);
            gl.glVertex3f(1.0f,0.0f,0.0f);
          gl.glEnd();
          gl.glBegin(GL.GL_POLYGON);/* f2: bottom */
            gl.glNormal3f(0.0f,0.0f,-1.0f);
            gl.glVertex3f(0.0f,0.0f,0.0f);
            gl.glVertex3f(1.0f,0.0f,0.0f);
            gl.glVertex3f(1.0f,1.0f,0.0f);
            gl.glVertex3f(0.0f,1.0f,0.0f);
          gl.glEnd();
          gl.glBegin(GL.GL_POLYGON);/* f3:back */
            gl.glNormal3f(1.0f,0.0f,0.0f);
            gl.glVertex3f(1.0f,1.0f,0.0f);
            gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glVertex3f(0.0f,1.0f,1.0f);
            gl.glVertex3f(0.0f,1.0f,0.0f);
          gl.glEnd();
          gl.glBegin(GL.GL_POLYGON);/* f4: top */
            gl.glNormal3f(0.0f,0.0f,1.0f);
            gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glVertex3f(1.0f,0.0f,1.0f);
            gl.glVertex3f(0.0f,0.0f,1.0f);
            gl.glVertex3f(0.0f,1.0f,1.0f);
          gl.glEnd();
          gl.glBegin(GL.GL_POLYGON);/* f5: left */
            gl.glNormal3f(0.0f,1.0f,0.0f);
            gl.glVertex3f(0.0f,0.0f,0.0f);
            gl.glVertex3f(0.0f,1.0f,0.0f);
            gl.glVertex3f(0.0f,1.0f,1.0f);
            gl.glVertex3f(0.0f,0.0f,1.0f);
          gl.glEnd();
          gl.glBegin(GL.GL_POLYGON);/* f6: right */
            gl.glNormal3f(0.0f,-1.0f,0.0f);
            gl.glVertex3f(1.0f,0.0f,0.0f);
            gl.glVertex3f(1.0f,0.0f,1.0f);
            gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glVertex3f(1.0f,1.0f,0.0f);
          gl.glEnd();

    }

}