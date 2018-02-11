import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Controller {
    private static Model model;
    private static Kepler_A_Window window;

    public static void main (String[] args) throws InterruptedException {
        //Create the model
        model = new Model();
        
        //Start the GUI
        window = new Kepler_A_Window();
        window.run(args);
        
        autoRun();
    }
    
    public ArrayList<Body> getBodies() {
        return model.getBodies();
    }
    
    public static void autoRun() throws InterruptedException {
        for (int i = 0; i < 1461; i++) {
            model.step(1);
            window.update();
            TimeUnit.SECONDS.sleep(1);
        }
    }
    
    
}
