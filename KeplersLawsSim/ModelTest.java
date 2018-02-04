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
        dateTest(model, 10);
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
        Body earth = model.bodies.get(0);
        System.out.println("***Date Test***"); 

        //Forward one year
        model.setDate(2004, 11, 21);

        if (earth.getX() == radius &&
            earth.getY() == 0)
            System.out.println("Passed: Future Date");
        else {
            System.out.println("Failed: Future Date--Final Coordinates:");
            System.out.println("X: " + earth.getX());
            System.out.println("Y: " + earth.getY());
         }

        //Reverse Test
        model.setDate(1999, 11, 21);

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
