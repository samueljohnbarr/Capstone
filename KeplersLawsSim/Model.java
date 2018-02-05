import java.awt.Color;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Models each planet
 * (Just Earth for the time being)
 * Start Date: 12/21/2000, Earth at (radius, 0) 
 * (JD = 2400000 + 5190)?
 * (Mercury is at superior conjuction here too (-radius, 0))
 * @author barrsj
 * @version 2/2/18
 */
public class Model {
    private static final double EARTH_YEAR = 365.25;
    private static final int CIRCLE_DEGREES = 360;
    public ArrayList<Body> bodies;
    private GregorianCalendar date;

    public Model() {
        //Create initial date for simulation
        date = new GregorianCalendar(2000, 11, 21);
         
        bodies = new ArrayList<Body>();
        //Given: coordinates to sit on radius of 10
        /* Adding bodies will be given its own method to allow for
         * variable radii */
        bodies.add(new Body("Earth", 30, 1.0, 0.167, 10.0, 
                            Color.BLUE, 10, 0, 0));
    }
    
    /**
     * Steps all planet coordinates by days
     * @param days to step (Can be negative)
     * @param radius of orbit in pixels
     */
    public void step(int days) {
        double angle;
        Body planet;
        for (int i = 0; i < bodies.size(); i++) {
            planet = bodies.get(i);
            //Get anglular distance
            angle = getAngularDistance(planet.getOrbitalPeriod(), days);

            //Update planet angle and date
            planet.setAngle(planet.getAngle() + angle);
            date.add(GregorianCalendar.DAY_OF_YEAR, days);
            
            //Calculate new coordinates
            double newX = Math.cos(planet.getAngle()) * 
                                   planet.getSemiMajorAxis();
            double newY = Math.sin(planet.getAngle()) * 
                                   planet.getSemiMajorAxis();

            //Round to eight decimal places
            newY = Math.round(newY * 1000000000) / 1000000000;

            planet.setX(newX);
            planet.setY(newY);
        }
    }

    /**
     * Sets the coordinates to a specified date
     * @param year to set
     * @param month to set
     * @param day to set
     */
    public void setDate(int year, int month, int day) {
    GregorianCalendar newDate = 
                    new GregorianCalendar(year, month-1, day);
    //Get the difference in milliseconds
    long diffInMillis = newDate.getTimeInMillis() - 
                            date.getTimeInMillis();

    //Convert the difference into days
    int diffInDays = 
                (int)(diffInMillis / (1000 * 60 * 60 * 24));
    
    //Step the difference
    step(diffInDays);

    }

    /**
     * @return date
     */
    public GregorianCalendar getDate() {
        return date;
    }

    /**
     * Returns to the angular distance of a planet in one day.
     * @param period of planet in earth years
     * @param days of distance (can be negative)
     * @return angular distance
     */
    private double getAngularDistance(double period, int days) {
        return (Math.toRadians(
                    CIRCLE_DEGREES / (period * EARTH_YEAR))) * days;
    }
    
}
