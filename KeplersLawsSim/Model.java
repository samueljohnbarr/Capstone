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
    public ArrayList<Body> bodies;
    
    public Model() {
        bodies = new ArrayList<Body>();
        bodies.add(new Body("Earth", 30, 1, 0.167, 15, Color.BLUE, 0, 0));
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
            angle = angularDistance(radius, planet.getOrbitalPeriod(), 1);
            planet.setX(planet.get(X) + (Math.cos(angle) * radius));
            planet.setY(planet.get(Y) + (Math.sin(angle) * radius));
        }
    }

    /**
     * Returns the angular distance in radians
     * @param radius of orbit
     * @param period orbital period of planet
     * @param days of orbit
     */
    public double angularDistance(double radius, double period, 
                                  int days) {
        //Find circumference from radius
        double circumference = Math.pow((Math.PI * radius), 2);
        //Divide result by orbital period to find angle
        double angle = (circumference / (period * EARTH_YEAR)) * days;
   
        return Math.toRadians(angle);      
    }
}
