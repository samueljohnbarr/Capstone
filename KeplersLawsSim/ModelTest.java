public class ModelTest {

    /**
     * Uses a radius of 10.
     */
    public static void main(String[] args) {
        Model model = new Model();
        for (int i = 0; i < 90; i++) {
            model.step(true, 10);
            System.out.println("X: " + model.bodies.get(0).getX());
            System.out.println("Y: " + model.bodies.get(0).getY());
            System.out.println("\n");
        }
               
    }
}
