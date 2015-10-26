package model.astronomy;

import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import main.Config;
import model.maths.Point3D;
import model.maths.Vector3D;

public class Space implements Config {
    //
    // public float hourofday = 0f;
    // public float dayofyear = 0f;
    // public float dayofmonth = 10f;

    private Planet[] planets;

    static float G = 0.001f; // TODO: change this

    // public float[][] colors;
    private int realNumPlanets;

    public Space() {
        // Vector3D pos = new Vector3D(0.0f, 0.0f, 5.0f);
        // Vector3D vel = new Vector3D(0.0f, 0.0f, 0.0f);
        // Vector3D force = new Vector3D(0.0f, 0.0f, -0.098f);

        this.realNumPlanets = Config.numPlanets;

        Random rand = new Random();

        this.planets = new Planet[Config.numPlanets];

        for (int i = 0; i < Config.numPlanets; i++) {
            int randomX = rand.nextInt((20));
            int randomY = rand.nextInt((10));
            int randomZ = rand.nextInt((15));

            float mul = 1;
            if (i % 3 == 0) {
                mul = (float) Math.random();

            } else {
                mul = -1;
            }
//Vector3D pos = new Vector3D();
            Vector3D pos = new Vector3D((float) Math.sin(randomX ^ 2) * 0.1f, (float) Math.cos(randomY), (float) randomZ * mul);
            Vector3D vel = new Vector3D((float) Math.random(), (float) Math.random(), (float) Math.random());

            // Vector3D vel = new Vector3D();
            Vector3D force = new Vector3D();

            float massRadius = (float) Math.random();

            this.planets[i] = new Planet(massRadius * 80, massRadius, pos, vel, force);
            this.planets[i].passes = 0;

            // TODO: random colors atm, make some classes orsmth
            this.planets[i].mass *= 100;
            this.planets[i].pos.multiply(50);
            this.planets[i].vel.multiply(10);
            //  this.planets[i].pos.multiply(10);

        }

    }

    /**
     * This method is called from Main.display() every frame
     */
    public void simulate(GL2 gl, double dt) {
        /*
         * Basically we need to iterate through all the planets and calculate
         * their new position and velocity vectors according to the force
         * applied to them
         */

        for (int i = 0; i < this.realNumPlanets; i++) {

            if (this.planets[i] != null) {

                Point3D oldPoint = new Point3D(this.planets[i].pos.getAsPoint());

                // Find all gravitational forces which attract a single planet
                for (int j = 0; j < this.realNumPlanets; j++) {
                    if (i != j && this.planets[j] != null) {

                        float distanceVectorX = this.planets[j].pos.x - this.planets[i].pos.x;
                        float distanceVectorY = this.planets[j].pos.y - this.planets[i].pos.y;
                        float distanceVectorZ = this.planets[j].pos.z - this.planets[i].pos.z;

                        Vector3D distanceVector = new Vector3D(distanceVectorX, distanceVectorY, distanceVectorZ);

                        float forceBetween = (G * this.planets[i].mass * this.planets[j].mass)
                                / ((float) Math.pow(distanceVector.length(), 2));

                        Vector3D unitVector = distanceVector.getUnitVector();

                        Vector3D forceVector = new Vector3D();

                        // if the planets are far enough (not side by side)
                        if (distanceVector.length() > (this.planets[i].radius + this.planets[j].radius)) {
                            forceVector = unitVector.multiply(forceBetween);
                        } else {

                            System.out.println("coll");

                            //TODO write methods
                            //  this.planets[i].mass += this.planets[j].mass;
//                            Vector3D pos1 = new Vector3D(this.planets[i].pos);
//                            Vector3D pos2 = new Vector3D(this.planets[j].pos);
//
//                            pos1.multiply(this.planets[i].mass);
//                            pos2.multiply(this.planets[j].mass);
//
//                            this.planets[i].pos = pos1.add(pos2).divide(this.planets[i].mass + this.planets[j].mass);
//                            this.planets[i].vel.add(this.planets[j].vel);
//                            this.planets[i].radius += this.planets[j].radius;
//                            this.planets[i].mass += this.planets[j].mass;
//
//                            this.planets[j] = null;
//
//                            if (this.realNumPlanets > 1) {
//                                this.realNumPlanets--;
//                            }
                            for (int k = j; k < this.planets.length - j - 1; k++) {

                                // this.planets[k] = this.planets[k + 1];
                            }

                            // we have a collision
                            // va1 = ( v1 * (m1 - m2) + 2 * m2 * v2 ) / ( m1 + m2 )
                            ////
                            //
                            // planets[i].force.multiply(-1);
                            // planets[j].force.multiply(-1);
                            // v1f = −v1i + 2(m1 v1i + m2 v2i)
                            // m1 + m2
                            // (1)
                            //
                            // Vector3D v1 = new Vector3D(planets[i].vel);
                            // Vector3D v2 = new Vector3D(planets[j].vel);
                            //
                            //
                            // Vector3D murd1 = v1.multiply(planets[i].mass);
                            // murd1.add(v2.multiply(planets[j].mass));
                            // murd1.multiply(2);
                            // murd1.divide(planets[i].mass + planets[j].mass);
                            //
                            // v1 = new Vector3D(planets[i].vel);
                            // v1.multiply(-1);
                            //
                            // v2 = new Vector3D(planets[j].vel);
                            // v2.multiply(-1);
                            // Vector3D changedVel1 = v1.add(murd1);
                            // Vector3D changedVel2 = v2.add(murd1);
                            //
                            // System.out.println(changedVel1.x);
                            //
                            //
                            // planets[i].vel = changedVel1;
                            // planets[j].vel = changedVel2;
                            // this.planets[j].solve(dt);
                            // v2f = −v2i + 2(m1 v1i + m2 v2i)
                            // m1 + m2
                            //
                            //
                            //
                            //
                            //
                            //
                            //
                        }

                        planets[i].applyForce(forceVector);

                        this.planets[i].solve(dt);
                    }
                }
            }
        }

        // Here the calculations are made, time to draw to the screen
        GLU glu = new GLU(); // needed for lookat
        for (int i = 0; i < this.realNumPlanets; i++) {
            if (this.planets[i] != null) {
                this.planets[i].drawOnScreen(gl);
            }
        }
    }

    public Point3D getCenterOfMass() {
        Point3D allPoints = new Point3D();

        float allMasses = 0;

        for (int i = 0; i < this.realNumPlanets; i++) {
            // allMasses += this.planets[i].vel.length();

            if (this.planets[i] != null) {
                //TODO maybe exceptions here
                allMasses += this.planets[i].mass;

                Vector3D pos = new Vector3D(this.planets[i].pos);

                pos.multiply(this.planets[i].mass);
                allPoints.add(pos);

            }

        }

        allPoints.divide(allMasses);

        return allPoints;
    }
}
