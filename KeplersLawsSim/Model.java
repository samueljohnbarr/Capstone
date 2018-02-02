import java.awt.Color;
import java.util.ArrayList;

/**
 * Models each planet
 * Just Earth for the time being
 * 
 * @author barrsj
 * @version 1/31/18
 */
public class Model {
    private static final double EARTH_YEAR = 365.25;
    private static final int CIRCLE_DEGREES = 360;
    public ArrayList<Body> bodies;
    
    public Model() {
        bodies = new ArrayList<Body>();
        //Given: coordinates to sit on radius of 10
        bodies.add(new Body("Earth", 30, 1.0, 0.167, 15.0, Color.BLUE, 10, 0));
    }
    
    /**
     * Steps all planet coordinates by one day
     * @param forward step (true) / backward step (false)
     * @param radius of orbit in pixels
     */
    public void step(boolean forward, int radius) {
        double angle;
        Body planet;
        for (int i = 0; i < bodies.size(); i++) {
            planet = bodies.get(i);
            //Get anglular distance for one day
            angle = getAngle(planet.getOrbitalPeriod());
            //Update planet angle
            if (forward)
                planet.setAngle(planet.getAngle() + angle);
            else
                planet.setAngle(planet.getAngle() - angle);
            
            //Set the new coordinates
            planet.setX(Math.cos(planet.getAngle()) * radius);
            planet.setY(Math.sin(planet.getAngle()) * radius);
        }
    }

    /**
     * Returns to the angular distance of a planet in one day.
     * @param period of planet in earth years
     * @return angular distance
     */
    public double getAngularDistance(double period) {
        return Math.toRadians(CIRCLE_DEGREES / (period * EARTH_YEAR));
    }
    
}
