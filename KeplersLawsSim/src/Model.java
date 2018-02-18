import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Models each planet
 * (Just Inner planets for now)
 * Start Date: 12/21/2000
 * (JD = 2400000 + 5190)?
 * 
 * TODO: Redo planet size scaling
 * 
 * @author barrsj
 * @version 2/2/18
 */
public class Model {
    private static final double EARTH_YEAR = 365.25;
    private static final int CIRCLE_DEGREES = 360;
    private ArrayList<Body> bodies;
    private GregorianCalendar date;
    private double scale = 15;

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
     * its stored angle, semimajor axis, and offset
     * 
     * @param planet to set
     * @return x position
     */
    private double getXPosition(Body planet) {
    	//Calculate x & y position
    	double x = planet.getXOffset() + Math.cos(planet.getAngle()) * 
                   planet.getSemiMajorAxis();
    	
    		
   		//Round to eight decimal place
    	return Math.round(x * 1000000000) / 1000000000;
    	
    }
    
    /**
    * Gets the planet y position according to
    * its stored angle, semiminor axis, and offset
    * 
    * @param planet to set
    * @return y position
    */
    private double getYPosition(Body planet) {
    	//Calculate x & y position
    	double y = planet.getYOffset() + Math.sin(planet.getAngle()) * 
                planet.getSemiMinorAxis();
    	
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
    public void setScale(double scale) {
    	int sizeDiff = (int)(scale - this.scale) * 2;
        for (int i = 0; i < bodies.size(); i++) {
        	Body planet = bodies.get(i);
        	double current = planet.getSemiMajorAxis();
        	planet.setSemiMajorAxis((current/this.scale) * scale);
        	planet.setX(getXPosition(planet));
        	planet.setY(getYPosition(planet));
        	planet.setSize(planet.getSize() + sizeDiff);
        	planet.setOffsets();
        	
        	//Stop displaying inner planets if scale is too small
        	if (scale < 10) {
        		if (i == 1 || i == 2 || i == 3 || i ==4)
        			planet.setVisible(false);
        	} else 
        		planet.setVisible(true);
        	
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
    	sun.setSize(65);
    	sun.setColor(Color.YELLOW);
    	bodies.add(sun);
    	
    	//Mercury
    	Body mercury = new Body();
    	mercury.setSize(10);
    	mercury.setOrbitalPeriod(0.241);
    	mercury.setEccentricity(0.206);
    	mercury.setSemiMajorAxis(5.79 * scale);
    	mercury.setColor(Color.BLACK);
    	mercury.setAngle(getAngularDistance(mercury.getOrbitalPeriod(), 44));  //3.1406903184;
    	mercury.setX(getXPosition(mercury));
    	mercury.setY(getYPosition(mercury));
    	mercury.setOffsets();
    	bodies.add(mercury);
    	
    	//Venus
    	Body venus = new Body();
    	venus.setSize(22);
    	venus.setOrbitalPeriod(0.615);
    	venus.setEccentricity(0.0068);
    	venus.setSemiMajorAxis(10.8 * scale); //10.8 * scale
    	venus.setColor(Color.ORANGE);
    	venus.setAngle(getAngularDistance(venus.getOrbitalPeriod(), 187)); //5.2306557038660664;
    	venus.setX(getXPosition(venus));
        venus.setY(getYPosition(venus));
        venus.setOffsets();
    	bodies.add(venus);
    	
    	//Earth
    	Body earth = new Body();
    	earth.setSize(25);
    	earth.setOrbitalPeriod(1);
    	earth.setEccentricity(0.0167); 
    	earth.setSemiMajorAxis(15 * scale);
    	earth.setX(earth.getSemiMajorAxis());
    	earth.setColor(Color.BLUE);
    	earth.setAngle(0);
    	earth.setOffsets();
    	bodies.add(earth);
    	
    	//Mars
    	Body mars = new Body();
    	mars.setSize(16);
    	mars.setOrbitalPeriod(1.88);
    	mars.setEccentricity(0.0934);
    	mars.setSemiMajorAxis(22.8 * scale);
    	mars.setColor(Color.RED);
    	mars.setAngle(getAngularDistance(mars.getOrbitalPeriod(), 170)); //193
    	mars.setX(getXPosition(mars));
    	mars.setY(getYPosition(mars));
    	mars.setOffsets();
    	bodies.add(mars);
    	
    	//Jupiter
    	Body jupiter = new Body();
    	jupiter.setSize(55);
    	jupiter.setOrbitalPeriod(11.9);
    	jupiter.setEccentricity(0.0485);
    	jupiter.setSemiMajorAxis(77.8 * scale);
    	jupiter.setColor(Color.DARKORANGE);
    	jupiter.setAngle(getAngularDistance(jupiter.getOrbitalPeriod(), 0)); //TODO: fix this
    	jupiter.setX(getXPosition(jupiter));
    	jupiter.setY(getYPosition(jupiter));
    	jupiter.setOffsets();
    	bodies.add(jupiter);
    	
    	//Saturn
    	Body saturn = new Body();
    	saturn.setSize(45);
    	saturn.setOrbitalPeriod(29.5);
    	saturn.setEccentricity(0.0556);
    	saturn.setSemiMajorAxis(143 * scale);
    	saturn.setColor(Color.ORANGE);
    	saturn.setAngle(getAngularDistance(saturn.getOrbitalPeriod(), 0)); //TODO: fix this
    	saturn.setX(getXPosition(saturn));
    	saturn.setY(getYPosition(saturn));
    	saturn.setOffsets();
    	bodies.add(saturn);
    	
    	//Uranus
    	Body uranus = new Body();
    	uranus.setSize(40);
    	uranus.setOrbitalPeriod(84);
    	uranus.setEccentricity(0.0472);
    	uranus.setSemiMajorAxis(287 * scale);
    	uranus.setColor(Color.AQUA);
    	uranus.setAngle(getAngularDistance(uranus.getOrbitalPeriod(), 0)); //TODO: fix this
    	uranus.setX(getXPosition(uranus));
    	uranus.setY(getYPosition(uranus));
    	uranus.setOffsets();
    	bodies.add(uranus);
    	
    	//Neptune
    	Body neptune = new Body();
    	neptune.setSize(40);
    	neptune.setOrbitalPeriod(165);
    	neptune.setEccentricity(0.0086);
    	neptune.setSemiMajorAxis(450 * scale);
    	neptune.setColor(Color.DARKBLUE);
    	neptune.setAngle(getAngularDistance(neptune.getOrbitalPeriod(), 0)); //TODO: fix this
    	neptune.setX(getXPosition(neptune));
    	neptune.setY(getYPosition(neptune));
    	neptune.setOffsets();
    	bodies.add(neptune);
    	
    	//Pluto
    	Body pluto = new Body();
    	pluto.setSize(10);
    	pluto.setOrbitalPeriod(248);
    	pluto.setEccentricity(0.25);
    	pluto.setSemiMajorAxis(590 * scale);
    	pluto.setColor(Color.BROWN);
    	pluto.setAngle(getAngularDistance(pluto.getOrbitalPeriod(), 0)); //TODO: fix this
    	pluto.setX(getXPosition(pluto));
    	pluto.setY(getYPosition(pluto));
    	pluto.setOffsets();
    	bodies.add(pluto);
    	
    	
    }
    
}
