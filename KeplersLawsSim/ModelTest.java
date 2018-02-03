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
        stepTest(model, 10);
        //Calendar date = Calendar.getInstance();
        //date.set(2000, 11, 21);
        //System.out.println(date.get(Calendar.DAY_OF_YEAR));
    }
    
    /**
     * Runs the step method for the day equivalent of four years.
     * Passes if Earth ends in the same position it started in.
     */
    private static void stepTest(Model model, int radius) {
        int days = 1461;
        int passX = radius;
        int passY = 0;
        Body earth = model.bodies.get(0);
        
        //Forward Test
        for (int i = 0; i < days; i++) 
            model.step(true, radius);

        if (earth.getX() == radius &&
            earth.getY() == 0) 
            System.out.println("Passed: Forward Step");
         else {
            System.out.println("Failed: Forward Step--Final Coordinates:");
            System.out.println("X: " + earth.getX());
            System.out.println("Y: " + earth.getY());
         }
   

        //Reverse Test
        for (int i = 0; i < days; i++)
            model.step(false, radius);

        if (earth.getX() == radius &&
            earth.getY() == 0)
            System.out.println("Passed: Reverse Step");
        else {
            System.out.println("Failed: Reverse Step");
            System.out.println("X: " + earth.getX());
            System.out.println("Y: " + earth.getY());
        }
                            
    }
}
