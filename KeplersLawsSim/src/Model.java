import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Models each planet
 * (Just Inner planets for now)
 * Start Date: 12/21/2000
 * (JD = 2400000 + 5190)?
 * 
 * TODO: Offset Orbits, scale manipulation
 * 
 * @author barrsj
 * @version 2/2/18
 */
public class Model {
    private static final double EARTH_YEAR = 365.25;
    private static final int CIRCLE_DEGREES = 360;
    private ArrayList<Body> bodies;
    private GregorianCalendar date;
    private int scale = 15;

    public Model() {
        //Create initial date for simulation
        date = new GregorianCalendar(2000, 11, 21);
         
        bodies = new ArrayList<Body>();
        
        //Initialize planet objects
        initInnerPlanets();
        
        //Set positions using their angle
        for (int i = 1; i < bodies.size(); i++) {
        	Body planet = bodies.get(i);
        	planet.setX(getXPosition(planet));
        	planet.setY(getYPosition(planet));
        	
        }
    }
    
    
    /**
     * Gets the planet x position according to
     * its stored angle
     * 
     * @param planet to set
     * @return x position
     */
    private double getXPosition(Body planet) {
    	//Calculate x & y position
    	double x = Math.cos(planet.getAngle()) * 
                   planet.getSemiMajorAxis();
    	
    		
   		//Round to eight decimal place
    	return Math.round(x * 1000000000) / 1000000000;
    	
    }
    
    /**
    * Gets the planet y position according to
    * its stored angle
    * 
    * @param planet to set
    * @return y position
    */
    private double getYPosition(Body planet) {
    	//Calculate x & y position
    	double y = Math.sin(planet.getAngle()) * 
                planet.getSemiMajorAxis();
    	
    	//Round to eight decimal places
    	return Math.round(y * 1000000000) / 1000000000;
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
            //Get angular distance
            angle = getAngularDistance(planet.getOrbitalPeriod(), days);

            //Update planet angle and date
            planet.setAngle(planet.getAngle() + angle);
            date.add(GregorianCalendar.DAY_OF_YEAR, days);
            
            //Set position
            planet.setX(getXPosition(planet));
            planet.setY(getYPosition(planet));
        }
    }
    
    /**
     * Step method helper
     * Returns to the angular distance of a planet in one day.
     * @param period of planet in earth years
     * @param days of distance (can be negative)
     * @return angular distance
     */
    private double getAngularDistance(double period, int days) {
        return -(Math.toRadians(
                    CIRCLE_DEGREES / (period * EARTH_YEAR))) * days;
    }
    
    /**
     * @return date
     */
    public GregorianCalendar getDate() {
        return date;
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
        int days = 0;
        //New date is in the future
        if (date.before(newDate)) {
            while (date.before(newDate)) {
                days++;
                newDate.add(GregorianCalendar.DAY_OF_YEAR, -1);
            }
        //New date is in the past
        } else {
            while (date.after(newDate)) {
                days--;
                newDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
            }
        }

    //Step the difference
    step(days);

    }
    
    /**
     * @return bodies
     */
    public ArrayList<Body> getBodies() {
        return bodies;
    }
    
    /**
     * Sets scale for the simulation
     * Updates bodies accordingly
     * 
     * @param scale to set
     */
    public void setScale(int scale) {
    	int sizeDiff = (scale - this.scale) * 5;
        for (int i = 0; i < bodies.size(); i++) {
        	Body planet = bodies.get(i);
        	double current = planet.getSemiMajorAxis();
        	planet.setSemiMajorAxis((current/this.scale) * scale);
        	planet.setX(getXPosition(planet));
        	planet.setY(getYPosition(planet));
        	planet.setSize(planet.getSize() + sizeDiff);
        }
        this.scale = scale;
    }
    
    
    /**
     * Initializes all bodies
     * (Null Constructor & Mutator setting for readability)
     */
    private void initInnerPlanets() {
    	//Sun
    	Body sun = new Body();
    	sun.setSize(75);
    	sun.setColor(Color.YELLOW);
    	bodies.add(sun);
    	
    	//Mercury
    	Body mercury = new Body();
    	mercury.setSize(10);
    	mercury.setOrbitalPeriod(0.241);
    	mercury.setEccentricity(0.206);
    	mercury.setSemiMajorAxis(5.79 * scale);
    	mercury.setX(getXPosition(mercury));
    	mercury.setY(getYPosition(mercury));
    	mercury.setColor(Color.BLACK);
    	mercury.setAngle(getAngularDistance(mercury.getOrbitalPeriod(), 44));  //3.1406903184;
    	bodies.add(mercury);
    	
    	//Venus
    	Body venus = new Body();
    	venus.setSize(28);
    	venus.setOrbitalPeriod(0.615);
    	venus.setEccentricity(0.0068);
    	venus.setSemiMajorAxis(10.8 * scale);
        venus.setX(getXPosition(venus));
        venus.setY(getYPosition(venus));
    	venus.setColor(Color.ORANGE);
    	venus.setAngle(getAngularDistance(venus.getOrbitalPeriod(), 187)); //5.2306557038660664;
    	bodies.add(venus);
    	
    	//Earth
    	Body earth = new Body();
    	earth.setSize(30);
    	earth.setOrbitalPeriod(1);
    	earth.setEccentricity(0.167);
    	earth.setSemiMajorAxis(15 * scale);
    	earth.setX(earth.getSemiMajorAxis());
    	earth.setColor(Color.BLUE);
    	earth.setAngle(0);
    	bodies.add(earth);
    	
    	//Mars
    	Body mars = new Body();
    	mars.setSize(16);
    	mars.setOrbitalPeriod(1.88);
    	mars.setEccentricity(0.0934);
    	mars.setSemiMajorAxis(22.8 * scale);
    	mars.setX(getXPosition(mars));
    	mars.setY(getYPosition(mars));
    	mars.setColor(Color.RED);
    	mars.setAngle(getAngularDistance(mars.getOrbitalPeriod(), 170)); //193
    	bodies.add(mars);
    	
    }
    
}
