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
        //Given: coordinates to sit on radius of 10
        bodies.add(new Body("Earth", 30, 1.0, 0.167, 15.0, Color.BLUE, 10, 0));
    }
    
    /**
     * Steps all planet coordinates by one day
     * @param forward step (true) / backward step (false)
     * @param radius of orbit in pixels
     */
    public void step(boolean forward, int radius) {
        double arcLen;
        double angle;
        Body planet;
        for (int i = 0; i < bodies.size(); i++) {
            planet = bodies.get(i);
            arcLen = arcLength(radius, planet.getOrbitalPeriod());
            angle = arcAngle(radius, arcLen);
            planet.setAngle(planet.getAngle() + angle);
            if (forward) {
                planet.setX(planet.getX() + (Math.cos(planet.getAngle())));
                planet.setY(planet.getY() + (Math.sin(planet.getAngle())));
            } 
            else {
                planet.setX(planet.getX() - (Math.cos(angle) * radius));
                planet.setY(planet.getY() - (Math.sin(angle) * radius));
            }
        }
    }

    /**
     * Returns the arc length in degrees
     * @param radius of orbit
     * @param period orbital period of planet
     * @param days of orbit
     */
    public double arcLength(int radius, double period) {
        //Find circumference from radius
        double circumference = Math.pow((Math.PI * radius), 2);
        //Divide result by orbital period to find angle
        double arc = (circumference / (period * EARTH_YEAR));
   
        return arc;      
    }
    
    /**
     * Finds the arc angle from the arc length
     * Derived from the arc length formula
     */
    public double arcAngle(double radius, double arcLength) {
        return (360 * arcLength) / (2 * Math.PI * radius);
    }
}
