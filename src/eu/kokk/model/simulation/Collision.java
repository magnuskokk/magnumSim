package eu.kokk.model.simulation;

import eu.kokk.model.astronomy.Planet;
import eu.kokk.model.maths.Vector3D;

public class Collision {

    private Vector3D resultVel1, resultVel2;

    public Collision(Planet planet1, Planet planet2) {

        // Vector3D U1x, U1y, U2x, U2y, V1x, V1y, V2x, V2y;
        double m1, m2, x1, x2;
        Vector3D v1, v2, v1x, v2x, v1y, v2y;

        Vector3D x = planet1.pos.subtract(planet2.pos).normalize();

        v1 = planet1.vel;
        x1 = x.dotProduct(v1);
        v1x = x.multiply(x1);
        v1y = v1.subtract(v1x);
        m1 = planet1.mass;

        x = x.multiply(-1);

        v2 = planet2.vel;
        x2 = x.dotProduct(v2);
        v2x = x.multiply(x2);
        v2y = v2.subtract(v2x);
        m2 = planet2.mass;

        Vector3D newVel1 = new Vector3D();

        newVel1.add(v1x.multiply((m1 - m2) / (m1 + m2)));
        newVel1.add(v2x.multiply((2 * m2) / (m1 + m2)));
        newVel1.add(v1y);

        this.resultVel1 = newVel1;

        Vector3D newVel2 = new Vector3D();

        newVel2.add(v1x.multiply((2 * m1) / (m1 + m2)));
        newVel2.add(v2x.multiply((m2 - m1) / (m1 + m2)));
        newVel2.add(v2y);

        this.resultVel2 = newVel2;
    }

    public Vector3D getResultVel1() {
        return this.resultVel1;
    }

    public Vector3D getResultVel2() {
        return this.resultVel2;
    }
}
