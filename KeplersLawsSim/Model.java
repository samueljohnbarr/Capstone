import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Models each planet
 * (Just Earth for the time being)
 * **12/21/2000** Earth at (radius, 0) START DATE 
 * (JD = 2400000 + 5190)?
 * (Mercury is at conjuction here too (-radius, 0))
 * @author barrsj
 * @version 2/2/18
 */
public class Model {
    private static final double EARTH_YEAR = 365.25;
    private static final int CIRCLE_DEGREES = 360;
    public ArrayList<Body> bodies;
    public Calendar date;

    public Model() {
        //Create initial date for simulation
        date = Calendar.getInstance();
        date.set(2000, 11, 21);
         
        bodies = new ArrayList<Body>();
        //Given: coordinates to sit on radius of 10
        /* Add bodies will be given its own method to allow for
         * variable radii */
        bodies.add(new Body("Earth", 30, 1.0, 0.167, 15.0, 
                            Color.BLUE, 10, 0));
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
            angle = getAngularDistance(planet.getOrbitalPeriod());
            //Update planet angle and date
            if (forward) {
                planet.setAngle(planet.getAngle() + angle);
                date.roll(Calendar.DATE, true);
            }
            else {
                planet.setAngle(planet.getAngle() - angle);
                date.roll(Calendar.DATE, false);
            }
            
            //Set the new coordinates
            planet.setX(Math.cos(planet.getAngle()) * radius);
            planet.setY(Math.sin(planet.getAngle()) * radius);
        }
    }

    /**
     * Sets the coordinates to a specified date
     */
    public void setDate(int year, int month, int day,
                        int radius) {
       
        
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
