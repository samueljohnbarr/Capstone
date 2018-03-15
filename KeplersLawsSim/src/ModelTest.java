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
        model.setScale(5);
        getAngularDistance(model);
    }
    
    private static void getAngularDistance(Model model) {
    	Body body = model.getBodies().get(Model.PLUTO);
    	//Get required area (Must be stacked like angle is)
    	double areaRequirement = -Math.round((Math.PI * body.getSemiMajorAxis() * body.getSemiMinorAxis()) /
    			(body.getOrbitalPeriod() * 365.25));
    	//Retrieve a baseline angle
    	double angle = model.getAngularDistance(body.getOrbitalPeriod(), 1);
    	//Round to 3 decimal places
    	//angle = Math.round(angle * 100000) / 100000;
    	
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
