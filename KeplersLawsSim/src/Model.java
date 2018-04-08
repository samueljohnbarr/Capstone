import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

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
	public static final int MERCURY = 1;
    public static final int VENUS = 2;
    public static final int EARTH = 3;
    public static final int MARS = 4;
    public static final int JUPITER = 5;
    public static final int SATURN= 6;
    public static final int URANUS = 7;
    public static final int NEPTUNE = 8;
    public static final int PLUTO = 9;
    public static final int HALLEY = 10;
    public static final double EARTH_DAYS = 365.25;
    private static final int CIRCLE_DEGREES = 360;
    ArrayList<Body> bodies;
    private GregorianCalendar date;
    private double scale = 15;

    public Model() {
        //Create initial date for simulation
        date = new GregorianCalendar(2000, 11, 21);

        bodies = new ArrayList<Body>();

        //Initialize planet objects
        JFXPanel jfxPanel = new JFXPanel();
        initPlanets();

        //Set positions using their angle
        for (int i = 1; i < bodies.size(); i++) {
        	Body planet = bodies.get(i);
        	planet.setX(getXPosition(planet));
        	planet.setY(getYPosition(planet));

        }
    }


    /**
     * Gets the planet x position according to
     * its stored angles, semimajor/minor axis, and offset
     *
     * **Math source http://quickcalcbasic.com/ellipse%20line%20intersection.pdf
     *
     * @param planet to set
     * @return x position
     */
    private double getXPosition(Body planet) {
    	//Calculate x position on non angled plane
    	double x = planet.getXOffset() + Math.cos(planet.getAngle()) *
                   planet.getSemiMajorAxis();
    	double y = planet.getYOffset() + Math.sin(planet.getAngle()) *
                planet.getSemiMinorAxis();

    	//Adjust for orbital angle
    	x = (x - planet.getXOffset()) * Math.cos(planet.getOrbitalAngle()) -
    	    (y - planet.getYOffset()) * Math.sin(planet.getOrbitalAngle()) + planet.getXOffset();


   		//Round to eight decimal places
    	x =  Math.round(x * 1000000000) / 1000000000;

    	return x;
    }

    /**
    * Gets the planet y position according to
    * its stored angles, semimajor/minor axis, offset
    *
    * **Math source http://quickcalcbasic.com/ellipse%20line%20intersection.pdf
    *
    * @param planet to set
    * @return y position
    */
    private double getYPosition(Body planet) {
    	//Calculate y position
    	double y = planet.getYOffset() + Math.sin(planet.getAngle()) *
                planet.getSemiMinorAxis();
    	double x = planet.getXOffset() + Math.cos(planet.getAngle()) *
                planet.getSemiMajorAxis();

    	//adjust for orbital angle
    	y = (y - planet.getYOffset()) * Math.cos(planet.getOrbitalAngle()) +
    	    (x - planet.getXOffset()) * Math.sin(planet.getOrbitalAngle()) + planet.getYOffset();

    	//Round to eight decimal places
    	y = Math.round(y * 1000000000) / 1000000000;

    	return y;
    }

    /**
     * Steps all planet coordinates by days
     * @param days to step (Can be negative)
     * @param radius of orbit in pixels
     */
    public void step(int days) {
    	double meanAnomaly;
    	double eccentricAnomaly;
    	double trueAnomaly;
        double angle;
        Body planet;
        for (int i = 0; i < bodies.size(); i++) {
            planet = bodies.get(i);
            //Calculate Kepler's Equation
            //TODO: change this
            meanAnomaly = getMeanAnomaly(planet.getLastMeanAnomaly(), planet.getOrbitalPeriod(), days);
            planet.setLastMeanAnomaly(meanAnomaly);
            eccentricAnomaly = getEccentricAnomaly(meanAnomaly, planet.getEccentricity());
            trueAnomaly = getTrueAnomaly(planet.getEccentricity(), eccentricAnomaly);
            
            //Find the angle
            angle = getAngle(planet.getSemiMajorAxis(), planet.getEccentricity(), trueAnomaly);

            //Debug
            if (i == MERCURY) {
            	System.out.println("Mean: " + meanAnomaly);
            	System.out.println("Eccentric: " + eccentricAnomaly);
            	System.out.println("True: " + trueAnomaly);
            	System.out.println("Angle: " + angle);
            	System.out.println();
            }

            //Update planet angle
            //planet.setAngle(planet.getAngle() + angle);
            planet.setAngle(angle);

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
    public double getAngularDistance(double period, int days) {
        return -(Math.toRadians(
                    CIRCLE_DEGREES / (period * EARTH_DAYS))) * days;
    }

    /**
     * Calculates the mean anomaly
     * @param perihelionTime time at which planet is at perihelion
     * @param period of the planet in Earth years
     * @param days to step 
     * @return mean anomaly
     */
    private double getMeanAnomaly(double lastMeanAnomaly, double period, int days) {
		double n = (2 * Math.PI) / (period * Model.EARTH_DAYS);
    	return lastMeanAnomaly + n * (days - 0); // days - perihelion time
    }
    
    /**
     * Finds eccentric anomaly using iterative calculation
     * @param meanAnomaly
     * @param eccentricity
     * @return eccentric anomaly
     */
    private double getEccentricAnomaly(double meanAnomaly, double eccentricity) {
    	//Two eccentric anomaly accumulators
    	double eA0 = meanAnomaly;
    	double eA1 = meanAnomaly + eccentricity * Math.sin(eA0);
    	
    	//Loop until the error is negligible
    	while ((Math.abs(eA0-eA1)/eA1 > 0.00001)) {
    		eA0 = eA1;
    		eA1 = meanAnomaly + eccentricity * Math.sin(eA0);
    	}
    	
    	return eA1;
    }
    
    /**
     * Calculates the true anomaly
     * @param eccentricity
     * @param eccentricAnomaly
     * @return true anomaly
     */
    private double getTrueAnomaly(double eccentricity, double eccentricAnomaly) {
    	double x = Math.sqrt(1 - eccentricity) * Math.cos(eccentricAnomaly/2);
    	double y = Math.sqrt(1 + eccentricity) * Math.sin(eccentricAnomaly/2);
    	
    	//atan2 is a polar argument vector
    	return 2*Math.atan2(y, x);
    }
    
    /**
     * Calculates the angle from the center of the ellipse that the planet should move.
     * @param a semiMajorAxis
     * @param e eccentricity
     * @param trueAnomaly
     * @return angle of movement
     */
    private double getAngle(double a, double e, double trueAnomaly) {
    	//Find radius from focus to point
    	double trueRadius = a * ((1-Math.pow(e, 2))/
    						(1 + e * Math.cos(trueAnomaly)));
    	//Get angle from other side of focus
    	double inverseAnomaly = Math.PI - trueAnomaly;
    	if (trueAnomaly > Math.PI)
    		inverseAnomaly = -inverseAnomaly;
    	
    	//Use Law of Cosines to find length from center to point
    	double r = Math.sqrt(Math.pow((e*a), 2) + Math.pow(trueRadius, 2) - (2*(e*a)*
    			trueRadius * Math.cos(inverseAnomaly)));
    	
    	//Use Law of Sines to find angle from center to point
    	double result = -Math.asin((Math.sin(inverseAnomaly)/r)*trueRadius);
    	
    	//Use Law of Sines to find angle from center to point
    	//if (trueAnomaly >= (Math.PI))
    		//return result + Math.PI;
    	return result;
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
        	planet.setSize((int)(planet.getScaleFactor() * scale));

        	//Hide inner planets if scale is too small
        	if (scale < 2) {
        		if (i == MERCURY || i == VENUS || i == EARTH || i == MARS)
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
    private void initPlanets() {
    	//Sun
    	Body sun = new Body();
    	sun.setScaleFactor(4.3);
    	sun.setSize((int)(sun.getScaleFactor() * scale));
    	sun.setColor(Color.YELLOW);
    	sun.setPattern(new ImagePattern(new Image("sun.png")));
    	bodies.add(sun);

    	//Mercury
    	Body mercury = new Body();
    	mercury.setScaleFactor(0.66);
    	mercury.setSize((int)(mercury.getScaleFactor() * scale));
    	mercury.setOrbitalPeriod(0.241);
    	mercury.setEccentricity(0.206);
    	mercury.setInclination(7);
    	mercury.setSemiMajorAxis(5.79 * scale);
    	mercury.offsetNegation(true, false);
    	mercury.setOffsets();
    	mercury.setColor(Color.BLACK);
    	mercury.setPattern(new ImagePattern(new Image("mercury.png")));
    	//mercury.setAngle(getAngularDistance(mercury.getOrbitalPeriod(), 40) + 0.785); //44
    	mercury.setX(getXPosition(mercury));
    	mercury.setY(getYPosition(mercury));
    	mercury.setShowLine(false);
    	bodies.add(mercury);

    	//Venus
    	Body venus = new Body();
    	venus.setScaleFactor(1.46);
    	venus.setSize((int)(venus.getScaleFactor() * scale));
    	venus.setOrbitalPeriod(0.615);
    	venus.setEccentricity(0.0068);
    	venus.setInclination(3);
    	venus.setSemiMajorAxis(10.8 * scale);
    	venus.setOffsets();
    	venus.setColor(Color.ORANGE);
    	venus.setPattern(new ImagePattern(new Image("venus.png")));
    	//venus.setAngle(getAngularDistance(venus.getOrbitalPeriod(), 216));
    	venus.setX(getXPosition(venus));
        venus.setY(getYPosition(venus));
        venus.setShowLine(false);
    	bodies.add(venus);

    	//Earth
    	Body earth = new Body();
    	earth.setScaleFactor(1.66);
    	earth.setSize((int)(earth.getScaleFactor() * scale));
    	earth.setOrbitalPeriod(1);
    	earth.setEccentricity(0.0167);
    	earth.setInclination(0);
    	earth.setSemiMajorAxis(15 * scale);
    	earth.setOffsets();
    	earth.setX(earth.getSemiMajorAxis());
    	earth.setColor(Color.BLUE);
    	earth.setPattern(new ImagePattern(new Image("earth.png")));
    	earth.setAngle(-0.8);
    	earth.setShowLine(false);
    	bodies.add(earth);

    	//Mars
    	Body mars = new Body();
    	mars.setScaleFactor(1.06);
    	mars.setSize((int)(mercury.getScaleFactor() * scale));
    	mars.setOrbitalPeriod(1.88);
    	mars.setEccentricity(0.0934);
    	mars.setInclination(1.85);
    	mars.setSemiMajorAxis(22.8 * scale);
    	mars.offsetNegation(false, true);
    	mars.setOffsets();
    	mars.setColor(Color.RED);
    	mars.setPattern(new ImagePattern(new Image("mars.png")));
    	//mars.setAngle(getAngularDistance(mars.getOrbitalPeriod(), 170) + 0.785);
    	mars.setX(getXPosition(mars));
    	mars.setY(getYPosition(mars));
    	mars.setShowLine(false);
    	bodies.add(mars);

    	//Jupiter
    	Body jupiter = new Body();
    	jupiter.setScaleFactor(27.5);
    	jupiter.setSize((int)(jupiter.getScaleFactor() * scale));
    	jupiter.setOrbitalPeriod(11.9);
    	jupiter.setEccentricity(0.0485);
    	jupiter.setInclination(1.3);
    	jupiter.setSemiMajorAxis(77.8 * scale);
    	jupiter.setOffsets();
    	jupiter.setColor(Color.DARKORANGE);
    	jupiter.setPattern(new ImagePattern(new Image("jupiter.png")));
    	//jupiter.setAngle(getAngularDistance(jupiter.getOrbitalPeriod(), 4035));
    	jupiter.setX(getXPosition(jupiter));
    	jupiter.setY(getYPosition(jupiter));
    	jupiter.setShowLine(false);
    	bodies.add(jupiter);

    	//Saturn
    	Body saturn = new Body();
    	saturn.setScaleFactor(22.5);
    	saturn.setSize((int)(saturn.getScaleFactor() * scale));
    	saturn.setOrbitalPeriod(29.5);
    	saturn.setEccentricity(0.0556);
    	saturn.setInclination(2.49);
    	saturn.setSemiMajorAxis(143 * scale);
    	saturn.setOffsets();
    	saturn.setColor(Color.ORANGE);
    	saturn.setPattern(new ImagePattern(new Image("saturn.png")));
    	//saturn.setAngle(getAngularDistance(saturn.getOrbitalPeriod(), 9885));
    	saturn.setX(getXPosition(saturn));
    	saturn.setY(getYPosition(saturn));
    	saturn.setShowLine(false);
    	bodies.add(saturn);

    	//Uranus
    	Body uranus = new Body();
    	uranus.setScaleFactor(20);
    	uranus.setSize((int)(uranus.getScaleFactor() * scale));
    	uranus.setOrbitalPeriod(84);
    	uranus.setEccentricity(0.0472);
    	uranus.setInclination(0.77);
    	uranus.setSemiMajorAxis(287 * scale);
    	uranus.setOffsets();
    	uranus.setColor(Color.AQUA);
    	uranus.setPattern(new ImagePattern(new Image("uranus.png")));
    	uranus.setAngle(getAngularDistance(uranus.getOrbitalPeriod(), 18870));
    	uranus.setX(getXPosition(uranus));
    	uranus.setY(getYPosition(uranus));
    	uranus.setShowLine(false);
    	bodies.add(uranus);

    	//Neptune
    	Body neptune = new Body();
    	neptune.setScaleFactor(35);
    	neptune.setSize((int)(neptune.getScaleFactor() * scale));
    	neptune.setOrbitalPeriod(165);
    	neptune.setEccentricity(0.0086);
    	neptune.setInclination(1.77);
    	neptune.setSemiMajorAxis(450 * scale);
    	neptune.offsetNegation(true, true);
    	neptune.setOffsets();
    	neptune.setColor(Color.DARKBLUE);
    	neptune.setPattern(new ImagePattern(new Image("neptune.png")));
    	//neptune.setAngle(getAngularDistance(neptune.getOrbitalPeriod(), 36150));
    	neptune.setX(getXPosition(neptune));
    	neptune.setY(getYPosition(neptune));
    	neptune.setShowLine(false);
    	bodies.add(neptune);

    	//Pluto
    	Body pluto = new Body();
    	pluto.setScaleFactor(6);
    	pluto.setSize((int)(pluto.getScaleFactor() * scale));
    	pluto.setOrbitalPeriod(248);
    	pluto.setEccentricity(0.25);
    	pluto.setInclination(17.2);
    	pluto.setSemiMajorAxis(590 * scale);
    	pluto.setOffsets();
    	pluto.setColor(Color.BROWN);
    	pluto.setPattern(new ImagePattern(new Image("pluto.png")));
    	//pluto.setAngle(getAngularDistance(pluto.getOrbitalPeriod(), 39000));
    	pluto.setX(getXPosition(pluto));
    	pluto.setY(getYPosition(pluto));
    	pluto.setShowLine(false);
    	bodies.add(pluto);

    	//Halley's Comet
    	Body halley = new Body();
    	halley.setScaleFactor(0);
    	halley.setSize((int)(halley.getScaleFactor() * scale));
    	halley.setOrbitalPeriod(76);
    	halley.setEccentricity(0.967);
    	halley.setInclination(162.24);
    	halley.setSemiMajorAxis(266 * scale);
    	halley.offsetNegation(false, true);
    	halley.setOffsets();
    	halley.setColor(Color.DARKGRAY);
    	halley.setPattern(new ImagePattern(new Image("mercury.png")));
        halley.setAngle(Math.PI);  //TODO: fix this
        halley.setX(getXPosition(halley));
        halley.setY(getYPosition(halley));
        halley.setShowLine(false);
        bodies.add(halley);
        
        //Toutatis
        Body toutatis = new Body();
        toutatis.setScaleFactor(0);
        toutatis.setSize((int)(toutatis.getScaleFactor() * scale));
        toutatis.setOrbitalPeriod(3.98);
        toutatis.setEccentricity(0.6294);
        toutatis.setInclination(0.45);
        toutatis.setSemiMajorAxis(16.9387);
        toutatis.offsetNegation(false, false);
        toutatis.setColor(Color.DARKGRAY);
        toutatis.setPattern(new ImagePattern(new Image("mercury.png")));
        toutatis.setX(getXPosition(toutatis));
        toutatis.setY(getYPosition(toutatis));
        toutatis.setShowLine(false);
        //bodies.add(toutatis);
    }

}