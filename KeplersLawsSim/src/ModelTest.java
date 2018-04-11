import java.util.Calendar;

/**
 * General Test class for Model 
 */
public class ModelTest {
    /**
     * Uses a radius of 10.
     */
    public static void main(String[] args) {
        Model model = new Model();
        //stepTest(model, 10);
        //dateTest(model, 10);
        model.setScale(1);
        Body earth = model.getBodies().get(Model.MARS);
    
        //Get mean anomaly
        double meanAnomaly = getMeanAnomaly(0, earth.getOrbitalPeriod(), 30);
        
        //Get eccentric anomaly
        double eccentricAnomaly = getEccentricAnomaly(meanAnomaly, earth.getEccentricity());
        
        //Get true anomaly
        double trueAnomaly = getTrueAnomaly(earth.getEccentricity(), eccentricAnomaly);
        
        //Find the angle :)
        double angle = getAngle(earth.getSemiMajorAxis(), earth.getEccentricity(), trueAnomaly);

        System.out.println("Eccentricity: " + earth.getEccentricity());
        System.out.println("Mean Anomaly: " + meanAnomaly);      
        System.out.println("Eccentric Anomaly: " + eccentricAnomaly);
        System.out.println("True Anomaly: " + trueAnomaly);
        //System.out.println("Angle: " + angle);
    }
    
    /**
     * Calculates the mean anomaly
     * @param perihelionTime time at which planet is at perihelion
     * @param period of the planet in Earth years
     * @param days to step 
     * @return mean anomaly
     */
    private static double getMeanAnomaly(double perihelionTime, double period, int days) {
		double n = (2 * Math.PI) / (period * Model.EARTH_DAYS);
    	return n * (days - perihelionTime);
    }
    
    /**
     * Finds eccentric anomaly using iterative calculation
     * @param meanAnomaly
     * @param eccentricity
     * @return eccentric anomaly
     */
    private static double getEccentricAnomaly(double meanAnomaly, double eccentricity) {
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
    private static double getTrueAnomaly(double eccentricity, double eccentricAnomaly) {
    	double x = Math.sqrt(1 - eccentricity) * Math.cos(eccentricAnomaly/2);
    	double y = Math.sqrt(1 + eccentricity) * Math.sin(eccentricAnomaly/2);
    	
    	//atan2 is a polar argument vector
    	return 2*Math.atan2(y, x);
    }
    
    /**
     * Calculates the angle from the center of the ellipse that the planet should move
     * Uses some right angle trig-foo to calculate angle from focus to center
     * @param a semiMajorAxis
     * @param e eccentricity
     * @param trueAnomaly
     * @return angle of movement
     */
    private static double getAngle(double a, double e, double trueAnomaly) {
    	//Find radius from focus to point
    	double trueRadius = a * ((1-Math.pow(e, 2))/
    						(1 + e * Math.cos(trueAnomaly)));
    	//Get angle from other side of point
    	double inverseAnomaly = Math.PI - trueAnomaly;
    	
    	//Use Law of Cosines to find length from center to point
    	double r = Math.sqrt(Math.pow((e*a), 2) + Math.pow(trueRadius, 2) - 2*(e*a)*
    			trueRadius * Math.cos(inverseAnomaly));
    	
    	//Use Law of Sines to find angle from center to point
    	return Math.asin((Math.sin(inverseAnomaly)/r)*trueRadius);
    }
    
    
    
    
    
    private static void getAngularDistance(Model model) {
    	Body body = model.getBodies().get(Model.PLUTO);
    	//Get required area (Must be stacked like angle is)
    	double areaRequirement = -Math.round((Math.PI * body.getSemiMajorAxis() * body.getSemiMinorAxis()) /
    			(body.getOrbitalPeriod() * 365.25));
    	//Retrieve a baseline angle
    	double angle = model.getAngularDistance(body.getOrbitalPeriod(), 1);
    	
    	//Round to 3 decimal places
    	//angle = Math.round(angle * 1000000) / 1000000;
    	
    	//Calc base area
    	double baseArea = calculateArea(angle, body);
    	
    	System.out.println("Required: " + areaRequirement);
    	System.out.println("Projected: " + baseArea);
    	System.out.println("Angle: " + angle + "pi");
    	
    	//Do a loop to alter the angle to the correct area requirement step by 0.001
    	if (areaRequirement < baseArea)
    		while(areaRequirement != baseArea) { 
    			angle -= 0.0001;
    			baseArea = calculateArea(angle, body);
    		}
    	else if (areaRequirement > baseArea) {
    		while(areaRequirement != baseArea) { 
    			angle += 0.0001;
    			baseArea = calculateArea(angle, body);
    		}
    	}
    			
    	System.out.println("\nDone - Results:");
    	System.out.println("Required: " + areaRequirement);
    	System.out.println("Projected: " + baseArea);
    	System.out.println("Angle: " + angle + "pi");
    	
    }
    
    private static double calculateArea(double angle, Body body) {
    	double e = body.getEccentricity();
    	double a = body.getSemiMajorAxis();
    	double b = body.getSemiMinorAxis();
    	
    	double eAnomoly = 2*Math.atan(Math.sqrt((1-e)/(1+e)) * Math.tan(angle/2));
    
    	return Math.round(0.5*a*b*(eAnomoly - e*Math.sin(eAnomoly)));
    			
    }
    
    /**
     * Runs the step method for the day equivalent of four years.
     * Passes if Earth ends in the same position it started in.
     */
    private static void stepTest(Model model, int radius) {
        int days = 1461;
        int passX = radius;
        int passY = 0;
        Body earth = model.getBodies().get(0);
        System.out.println("***Step Test***");
           
        //Forward Test
        model.step(days);

        if (earth.getX() == radius &&
            earth.getY() == 0) 
            System.out.println("Passed: Forward Step");
         else {
            System.out.println("Failed: Forward Step--Final Coordinates:");
            System.out.println("X: " + earth.getX());
            System.out.println("Y: " + earth.getY());
         }
   

        //Reverse Test
        model.step(-days);

        if (earth.getX() == radius &&
            earth.getY() == 0)
            System.out.println("Passed: Reverse Step");
        else {
            System.out.println("Failed: Reverse Step");
            System.out.println("X: " + earth.getX());
            System.out.println("Y: " + earth.getY());
        }
                            
    }

    private static void dateTest(Model model, int radius) {
        int passX = radius;
        int passY = 0;
        Body earth = model.getBodies().get(0);
        System.out.println("***Date Test***"); 

        //Forward one year
        model.setDate(2004, 12, 21);

        if (earth.getX() == radius &&
            earth.getY() == 0)
            System.out.println("Passed: Future Date");
        else {
            System.out.println("Failed: Future Date--Final Coordinates:");
            System.out.println("X: " + earth.getX());
            System.out.println("Y: " + earth.getY());
         }

        //Reverse Test
        model.setDate(1996, 12, 21);

        if (earth.getX() == radius &&
            earth.getY() == 0)
            System.out.println("Passed: Past Date");
        else {
            System.out.println("Failed: Past Date--Final Coordinates:");
            System.out.println("X: " + earth.getX());
            System.out.println("Y: " + earth.getY());
         }

    }

}
