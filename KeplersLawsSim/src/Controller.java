
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.concurrent.Task;
import javafx.application.Application;


public class Controller {
    private static Model model;
    private static Kepler_A_Window window;

    public static void main(String[] args) throws InterruptedException {     
        //Start the GUI
        new Thread() {
            public void run() {
                javafx.application.Application.launch(Kepler_A_Window.class);
            }
        }.start();
        model = new Model();
        window = Kepler_A_Window.waitForWindow();    
        
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

