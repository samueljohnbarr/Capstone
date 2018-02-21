
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Controller {
    private static Model model;
    private static Kepler_A_Window window;

    public static void main(String[] args) throws InterruptedException {     
    	model = new Model();
    	//Start the GUI
        new Thread() {
            public void run() {
                javafx.application.Application.launch(Kepler_A_Window.class);
            }
        }.start();
        
        window = Kepler_A_Window.waitForWindow();    
        setScale(14);
        autoRun();
    }
    
    /**
     * Waits for model object to be created
     * @return bodies
     */
    public ArrayList<Body> getBodies() {
    	while(model == null);
        return model.getBodies();
    }
    
    public static void autoRun() throws InterruptedException {
    	/*
    	//Scaling
    	for (double i = 20; i > 0; i -= 0.5) {
    		setScale(i);
    		TimeUnit.SECONDS.sleep(1);
    		window.update();
    	}
    	*/
    	
    	
        //for (int i = 0; i < 1451; i++) {
    	while (true) {
    	    model.getJulian();
            model.step(1);
            window.update();
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        
        
        
        
        
    }
    
    public static void setScale(double scale) {
    	model.setScale(scale);
    	window.refresh();
    	window.update();
    }
    
    
}

