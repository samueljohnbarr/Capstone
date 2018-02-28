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
	public final int MERCURY = 1;
    public final int VENUS = 2;
    public final int EARTH = 3;
    public final int MARS = 4;
    public final int JUPITER = 5;
    public final int SATURN= 6;
    public final int URANUS = 7;
    public final int NEPTUNE = 8;
    public final int PLUTO = 9;
    private static final double EARTH_YEAR = 365.25;
    private static final int CIRCLE_DEGREES = 360;
    ArrayList<Body> bodies;
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
            
            //Set position
            planet.setX(getXPosition(planet));
            planet.setY(getYPosition(planet));
        }
        date.add(GregorianCalendar.DAY_OF_YEAR, days);
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
     * Converts the current date object to a Julian date
     * @return julian date
     */
    public double getJulian() {
    	int day = date.get(GregorianCalendar.DAY_OF_MONTH);
    	int month = date.get(GregorianCalendar.MONTH)+1;
    	int year = date.get(GregorianCalendar.YEAR);
    	double julian = (1461 * (year + 4800 + (month - 14)/12))/4 + (367 * (month - 2 - 12 * ((month - 14)/12)))/12 - (3 * ((year + 4900 + (month - 14)/12)/100))/4 + day - 32075;
    	julian -= 0.5;
    	System.out.println(month + "/" + day + "/" + year);
    	System.out.println(julian + "\n");


    	return julian;
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
        for (int i = 0; i < bodies.size(); i++) {
        	Body planet = bodies.get(i);
        	double current = planet.getSemiMajorAxis();
        	planet.setSemiMajorAxis((current/this.scale) * scale);
        	planet.setOffsets();
        	planet.setX(getXPosition(planet));
        	planet.setY(getYPosition(planet));
        	planet.setSize((int)((planet.getSize()/this.scale) * scale));
        	
        	//Hide inner planets if scale is too small
        	if (scale < 0.1) {
        		if (i == 1 || i == 2 || i == 3 || i == 4)
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
    	sun.setSize((int)(4.3 * scale));
    	sun.setColor(Color.YELLOW);
    	bodies.add(sun);
    	
    	//Mercury
    	Body mercury = new Body();
    	mercury.setSize((int)(0.66 * scale));
    	mercury.setOrbitalPeriod(0.241);
    	mercury.setEccentricity(0.206);
    	mercury.setSemiMajorAxis(5.79 * scale);
    	mercury.setColor(Color.BLACK);
    	mercury.setAngle(getAngularDistance(mercury.getOrbitalPeriod(), 44));  //3.1406903184;
    	mercury.setX(getXPosition(mercury));
    	mercury.setY(getYPosition(mercury));
    	mercury.setOffsets();
    	mercury.setShowLine(false);
    	bodies.add(mercury);
    	
    	//Venus
    	Body venus = new Body();
    	venus.setSize((int)(1.46 * scale));
    	venus.setOrbitalPeriod(0.615);
    	venus.setEccentricity(0.0068);
    	venus.setSemiMajorAxis(10.8 * scale); //10.8 * scale
    	venus.setColor(Color.ORANGE);
    	venus.setAngle(getAngularDistance(venus.getOrbitalPeriod(), 187)); //5.2306557038660664;
    	venus.setX(getXPosition(venus));
        venus.setY(getYPosition(venus));
        venus.setOffsets();
        venus.setShowLine(false);
    	bodies.add(venus);
    	
    	//Earth
    	Body earth = new Body();
    	earth.setSize((int)(1.66 * scale));
    	earth.setOrbitalPeriod(1);
    	earth.setEccentricity(0.0167); 
    	earth.setSemiMajorAxis(15 * scale);
    	earth.setX(earth.getSemiMajorAxis());
    	earth.setColor(Color.BLUE);
    	earth.setAngle(0);
    	earth.setOffsets();
    	earth.setShowLine(true);
    	bodies.add(earth);
    	
    	//Mars
    	Body mars = new Body();
    	mars.setSize((int)(1.06 * scale));
    	mars.setOrbitalPeriod(1.88);
    	mars.setEccentricity(0.0934);
    	mars.setSemiMajorAxis(22.8 * scale);
    	mars.setColor(Color.RED);
    	mars.setAngle(getAngularDistance(mars.getOrbitalPeriod(), 170)); //193
    	mars.setX(getXPosition(mars));
    	mars.setY(getYPosition(mars));
    	mars.setOffsets();
    	mars.setShowLine(false);
    	bodies.add(mars);
    	
    	//Jupiter
    	Body jupiter = new Body();
    	jupiter.setSize((int)(27.5 * scale));
    	jupiter.setOrbitalPeriod(11.9);
    	jupiter.setEccentricity(0.0485);
    	jupiter.setSemiMajorAxis(77.8 * scale);
    	jupiter.setColor(Color.DARKORANGE);
    	jupiter.setAngle(getAngularDistance(jupiter.getOrbitalPeriod(), 0)); //TODO: fix this
    	jupiter.setX(getXPosition(jupiter));
    	jupiter.setY(getYPosition(jupiter));
    	jupiter.setOffsets();
    	jupiter.setShowLine(false);
    	bodies.add(jupiter);
    	
    	//Saturn
    	Body saturn = new Body();
    	saturn.setSize((int)(22.5 * scale));
    	saturn.setOrbitalPeriod(29.5);
    	saturn.setEccentricity(0.0556);
    	saturn.setSemiMajorAxis(143 * scale);
    	saturn.setColor(Color.ORANGE);
    	saturn.setAngle(getAngularDistance(saturn.getOrbitalPeriod(), 0)); //TODO: fix this
    	saturn.setX(getXPosition(saturn));
    	saturn.setY(getYPosition(saturn));
    	saturn.setOffsets();
    	saturn.setShowLine(false);
    	bodies.add(saturn);
    	
    	//Uranus
    	Body uranus = new Body();
    	uranus.setSize((int)(20 * scale));
    	uranus.setOrbitalPeriod(84);
    	uranus.setEccentricity(0.0472);
    	uranus.setSemiMajorAxis(287 * scale);
    	uranus.setColor(Color.AQUA);
    	uranus.setAngle(getAngularDistance(uranus.getOrbitalPeriod(), 0)); //TODO: fix this
    	uranus.setX(getXPosition(uranus));
    	uranus.setY(getYPosition(uranus));
    	uranus.setOffsets();
    	uranus.setShowLine(false);
    	bodies.add(uranus);
    	
    	//Neptune
    	Body neptune = new Body();
    	neptune.setSize((int)(35 * scale));
    	neptune.setOrbitalPeriod(165);
    	neptune.setEccentricity(0.0086);
    	neptune.setSemiMajorAxis(450 * scale);
    	neptune.setColor(Color.DARKBLUE);
    	neptune.setAngle(getAngularDistance(neptune.getOrbitalPeriod(), 0)); //TODO: fix this
    	neptune.setX(getXPosition(neptune));
    	neptune.setY(getYPosition(neptune));
    	neptune.setOffsets();
    	neptune.setShowLine(false);
    	bodies.add(neptune);
    	
    	//Pluto
    	Body pluto = new Body();
    	pluto.setSize((int)(6 * scale));
    	pluto.setOrbitalPeriod(248);
    	pluto.setEccentricity(0.25);
    	pluto.setSemiMajorAxis(590 * scale);
    	pluto.setColor(Color.BROWN);
    	pluto.setAngle(getAngularDistance(pluto.getOrbitalPeriod(), 0)); //TODO: fix this
    	pluto.setX(getXPosition(pluto));
    	pluto.setY(getYPosition(pluto));
    	pluto.setOffsets();
    	pluto.setShowLine(false);
    	bodies.add(pluto);

    	
    	
    }
    
}