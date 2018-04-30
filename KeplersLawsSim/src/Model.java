/**
 * Models each planet
 * @author barrsj, shieldsjpt
 * @version 1.0.0
 */

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import java.util.ArrayList;
import java.util.GregorianCalendar;

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
    public static final int TOUTATIS = 11;
    public static final double EARTH_DAYS = 365.25;
    private static final int CIRCLE_DEGREES = 360;
    ArrayList<Body> bodies;
    private GregorianCalendar date;
    private double scale = 15;

    public Model() {

        //Create initial date for simulation
        date = new GregorianCalendar(2000, 11, 21);

        //Initialize planet objects
        bodies = new ArrayList<Body>();

        //Needed to initiate part of the JavaFX library
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setVisible(false);
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
     */
    public void step(int days) {
    	double mean;
    	double meanAnomaly;
    	double eccentricAnomaly;
    	double trueAnomaly;
        double angle;
        boolean reverse = (days < 0);
        Body planet;
        
        for (int i = 0; i < bodies.size(); i++) {
            planet = bodies.get(i);

            //Get mean step
            mean = getMeanAnomaly(planet.getLastMeanAnomaly(), 
            		planet.getOrbitalPeriod(), days);
            
            //Add to current mean anomaly
            meanAnomaly = (planet.getLastMeanAnomaly() + mean);
            
            if (meanAnomaly < 0)
            	meanAnomaly = 2*Math.PI + (meanAnomaly);
            if (meanAnomaly > 2*Math.PI)
            	meanAnomaly -= 2*Math.PI;
            
            planet.setLastMeanAnomaly(meanAnomaly);
            //Calculate Kepler's equation
            eccentricAnomaly = getEccentricAnomaly(meanAnomaly, planet.getEccentricity());
            trueAnomaly = getTrueAnomaly(planet.getEccentricity(), eccentricAnomaly);
            
            //Find the angle
            angle = getAngle(planet.getSemiMajorAxis(), planet.getSemiMinorAxis(), 
            		planet.getEccentricity(), trueAnomaly, reverse);
            
            if (i == HALLEY) {
            	System.out.println("Mean Step: " + mean);
            	System.out.println("Mean: " + meanAnomaly);
            	System.out.println("True: " + trueAnomaly);
            	System.out.println("Angle: " + angle);
            	System.out.println();
            }

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
    	//return (lastMeanAnomaly + n * (days - 0)) % (2*Math.PI); // days - perihelion time
		return n * days;
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
    	return Math.abs(2*Math.atan2(y, x) % (2*Math.PI));
    }
    
    /**
     * Derives the angle from the center of the ellipse that the planet should move.
     * Uses a lot of math I do not understand :)
     * Math Source: 
     * https://math.stackexchange.com/questions/2539604/relation-between-ellipse-true-anomaly-and-center-angle
     * 
     * @param a semiMajorAxis
     * @param b semiMinorAxis
     * @param e eccentricity
     * @param trueAnomaly
     * @return angle of movement
     */
    private double getAngle(double a, double b, double e, double trueAnomaly, boolean reverse) {
    	double result = 0;
    	//Calculate for other half of orbit
    	if (trueAnomaly > Math.PI) {
    		result = Math.PI;
    		trueAnomaly = trueAnomaly % Math.PI;
    	}
    	
    	double d = Math.pow(a, 2)*Math.pow(Math.sin(trueAnomaly), 2) + 
    			Math.pow(b, 2)*Math.pow(Math.cos(trueAnomaly), 2) -
    			Math.pow(a, 2)*Math.pow(Math.sin(trueAnomaly), 2)*Math.pow(e, 2);
    			
    	double y = -Math.pow(b,2)*Math.sin(trueAnomaly)*Math.cos(trueAnomaly)*e +
    			b*Math.sin(trueAnomaly)*Math.sqrt(d);
    	
    	double x = Math.pow(a, 2)*Math.pow(Math.sin(trueAnomaly), 2)*e +
    			b*Math.cos(trueAnomaly)*Math.sqrt(d);
    	
    	
    	if ((trueAnomaly > 0 && trueAnomaly < Math.PI/2) ||
    		 (trueAnomaly > Math.PI/2 && trueAnomaly < (Math.PI-Math.atan(b/(a*e)))))
    		result +=  Math.atan(y/x);
    	
    	else if ((trueAnomaly > Math.PI - Math.atan(b/(a*e)) && trueAnomaly <= Math.PI))
    		result +=  Math.PI - Math.atan(-y/x);
    	
    	else if (trueAnomaly == Math.PI/2)
    		result += Math.atan((b*Math.sqrt(1-Math.pow(e,2)))/(a*e));
    	
    	else if (trueAnomaly == (Math.PI - Math.atan(b/(a*e))))
    		result +=  Math.PI/2;
    	
    	return result;
    	
    }
    /**
     * @return date
     */
    public GregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the coordinates to a specified date by calculating the difference
     * in days from the current date to the new date
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
    	Math.floor(julian);
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
        	planet.setSize((int)(planet.getScaleFactor() * scale));

        	//Hide inner planets if scale is too small
        	if (scale < 2) {
        		if (i == MERCURY || i == VENUS || i == EARTH 
        				|| i == MARS || i == TOUTATIS)
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
    	mercury.setSemiMajorAxis(5.79 * scale);
    	mercury.setInclination(7);
    	mercury.offsetNegation(true, false);
    	mercury.setOffsets();
    	mercury.setColor(Color.BLACK);
    	mercury.setPattern(new ImagePattern(new Image("mercury.png")));
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
    	venus.setSemiMajorAxis(10.8 * scale);
    	venus.setInclination(3);
    	venus.setOffsets();
    	venus.setColor(Color.ORANGE);
    	venus.setPattern(new ImagePattern(new Image("venus.png")));
    	venus.setX(getXPosition(venus));
        venus.setY(getYPosition(venus));
        venus.setShowLine(false);
    	bodies.add(venus);

    	//Earth
    	Body earth = new Body();
    	earth.setScaleFactor(1.66);
    	earth.setSize((int)(earth.getScaleFactor() * scale));
    	earth.setInclination(0);
    	earth.setOrbitalPeriod(1);
    	earth.setEccentricity(0.0167);
    	earth.setSemiMajorAxis(15 * scale);
    	earth.setOffsets();
    	earth.setX(earth.getSemiMajorAxis());
    	earth.setColor(Color.BLUE);
    	earth.setPattern(new ImagePattern(new Image("earth.png")));
    	earth.setShowLine(false);
    	bodies.add(earth);

    	//Mars
    	Body mars = new Body();
    	mars.setScaleFactor(1.06);
    	mars.setSize((int)(mercury.getScaleFactor() * scale));
    	mars.setOrbitalPeriod(1.88);
    	mars.setEccentricity(0.0934);
    	mars.setSemiMajorAxis(22.8 * scale);
    	mars.setInclination(1.85);
    	mars.offsetNegation(false, true);
    	mars.setOffsets();
    	mars.setColor(Color.RED);
    	mars.setPattern(new ImagePattern(new Image("mars.png")));
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
    	jupiter.setSemiMajorAxis(77.8 * scale);
    	jupiter.setInclination(1.3);
    	jupiter.setOffsets();
    	jupiter.setColor(Color.DARKORANGE);
    	jupiter.setPattern(new ImagePattern(new Image("jupiter.png")));
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
    	saturn.setSemiMajorAxis(143 * scale);
    	saturn.setInclination(2.49);
    	saturn.setOffsets();
    	saturn.setColor(Color.ORANGE);
    	saturn.setPattern(new ImagePattern(new Image("saturn.png")));
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
    	uranus.setSemiMajorAxis(287 * scale);
    	uranus.setInclination(0.77);
    	uranus.setOffsets();
    	uranus.setColor(Color.AQUA);
    	uranus.setPattern(new ImagePattern(new Image("uranus.png")));
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
    	neptune.setSemiMajorAxis(450 * scale);
    	neptune.setInclination(1.77);
    	neptune.offsetNegation(true, true);
    	neptune.setOffsets();
    	neptune.setColor(Color.DARKBLUE);
    	neptune.setPattern(new ImagePattern(new Image("neptune.png")));
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
    	pluto.setSemiMajorAxis(590 * scale);
    	pluto.setInclination(17.2);
    	pluto.setOffsets();
    	pluto.setColor(Color.BROWN);
    	pluto.setPattern(new ImagePattern(new Image("pluto.png")));
    	pluto.setX(getXPosition(pluto));
    	pluto.setY(getYPosition(pluto));
    	pluto.setShowLine(false);
    	bodies.add(pluto);

    	//Halley's Comet
    	Body halley = new Body();
    	halley.setScaleFactor(0);
    	halley.setSize((int)(halley.getScaleFactor() * scale));
    	halley.setOrbitalPeriod(75);
    	halley.setEccentricity(0.967);
    	halley.setSemiMajorAxis(266 * scale);
    	halley.setInclination(17.76);
    	halley.offsetNegation(false, true);
    	halley.setOffsets();
    	halley.setInitOrbitalAngle(Math.PI);
    	halley.setColor(Color.DARKGRAY);
    	halley.setPattern(new ImagePattern(new Image("mercury.png")));
        halley.setAngle(getAngularDistance(halley.getOrbitalPeriod(), 0));  //TODO: fix this
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
        toutatis.setSemiMajorAxis(16.9387 * scale);
        toutatis.setInclination(0.45);
        toutatis.offsetNegation(false, false);
        toutatis.setOffsets();
        toutatis.setInitOrbitalAngle(Math.PI);
        toutatis.setColor(Color.DARKGRAY);
        toutatis.setPattern(new ImagePattern(new Image("mercury.png")));
        toutatis.setX(getXPosition(toutatis));
        toutatis.setY(getYPosition(toutatis));
        toutatis.setShowLine(false);

    }
}