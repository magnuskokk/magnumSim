package eu.kokk.model.astronomy;

import eu.kokk.Config;
import eu.kokk.model.maths.Vector3D;
import eu.kokk.model.simulation.Collision;

import java.util.Random;

public class Space implements Config {
    //
    // public float hourofday = 0f;
    // public float dayofyear = 0f;
    // public float dayofmonth = 10f;

    public static Vector3D moveVector;
    private Planet[] planets;
    // public float[][] colors;
    private int realNumPlanets;
    private Vector3D centerOfMass = new Vector3D();

    /**
     * Class constructor
     */
    public Space() {
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

            Vector3D pos = new Vector3D(Math.sin(randomX ^ 2), -Math.cos(randomY) * mul, Math.cos(randomZ) * mul);
            Vector3D vel = new Vector3D(Math.cos(Math.pow(randomX, 2 * mul)), -Math.sin(randomY) * mul, Math.sin(randomZ));

            
            //Vector3D pos = new Vector3D();
            // Vector3D vel = new Vector3D();
            Vector3D force = new Vector3D();

            double mass = Math.random()*10;
            double radius = mass * 0.05;

                 
            if (i%15 == 0) {
                mass = Math.random()*1500.0;
                radius = mass/1000.0;
                
            }
            // This is the star
            if (i == 0) {
                mass = 3000.0;
               // mass = 1.989E10;

                radius = mass/1000.0;
                pos = new Vector3D();
                vel = new Vector3D();
            } else {
                pos = pos.multiply(30);
                vel = vel.multiply(30);
            }
       
        
            
            this.planets[i] = new Planet(mass, radius, pos, vel, force);
            this.planets[i].passes = 0;
        }

    }

    /**
     * This method is called from <code>Main.display()</code> every frame.
     * It iterates through all planets and calculates the forces applied to them
     *
     * @param dt Time passed since the last frame
     */
    public void simulate(double dt) {
        /*
         * Basically we need to iterate through all the planets and calculate
         * their new position and velocity vectors according to the force
         * applied to them
         */
        
        
        for (int i = 0; i < this.planets.length; i++) {
            for (int j = i + 1; j < this.planets.length; j++) {
                Vector3D distanceVector = this.planets[j].pos.subtract(this.planets[i].pos);

                // Calculate the gravitational force between 2 point masses
                double forceBetween = (Config.G * this.planets[i].mass * this.planets[j].mass)
                        / (Math.pow(distanceVector.length(), 2));

                // if the planets are far enough (not side by side)
                if (distanceVector.length() > (this.planets[i].radius + this.planets[j].radius)) {
                    Vector3D forceVector = distanceVector.getUnitVector().multiply(forceBetween);

                    this.planets[i].applyForce(forceVector);
                    this.planets[j].applyForce(forceVector.multiply(-1));
                } else { // shit we have a collision
                   // System.out.println("COLL");

                    Collision collision = new Collision(this.planets[i], this.planets[j]);
                    this.planets[i].vel = collision.getResultVel1();
                    this.planets[j].vel = collision.getResultVel2();
                }
                
                this.planets[i].solve(dt);
                this.planets[j].solve(dt);

            }
        }

        // Here the calculations are made, time to draw to the screen
        for (Planet planet : this.planets) {
            planet.drawOnScreen();
        }
        // Set the center back to the star
        // Camera.center = planets[0].pos.toPoint();
        // Camera.eye = new Point3D(0.0, 0.0, Camera.center.z + 70.0);
    }
    
    /**
     * @return The position of the heaviest planet
     */
    public Vector3D getHeaviestPlanet() {
        double mass = 0;
        Vector3D pos = null;


        for (int i = 0; i < this.realNumPlanets; i++) {
            if (this.planets[i].mass > mass) {
                mass = this.planets[i].mass;
                pos = new Vector3D(this.planets[i].pos);
            }

        }

        return pos;
    }

    public Vector3D getCenterOfMass() {
        Vector3D allPos = new Vector3D();

        float allMasses = 0;

        for (int i = 0; i < this.realNumPlanets; i++) {
            allMasses += this.planets[i].mass;

            if (this.planets[i] != null) {
                //TODO maybe exceptions here
                allMasses += this.planets[i].mass;

                Vector3D pos = new Vector3D(this.planets[i].pos);

                pos = pos.multiply(this.planets[i].mass);
                allPos = allPos.add(pos);
            }

        }

        allPos.divide(allMasses);

        return allPos;
    }
}
