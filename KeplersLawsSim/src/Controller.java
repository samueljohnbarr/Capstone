
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


public class Controller {
    private static Model model;
    private static Kepler_A_Window window;
    private static int stepDays;
    private static boolean run;

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
        //autoRun();
    }
    
    /**
     * Waits for model object to be created
     * @return bodies
     */
    public ArrayList<Body> getBodies() {
    	while(model == null);
        return model.getBodies();
    }
    
    /**
     * Waits for model object to be created
     * Steps the model to step forward stepDays
     */
    public void stepForward() {
    	while (model == null);
    	if (stepDays < 1)
    		stepDays = 1;
    	model.step(stepDays);
    	window.update();
    }
    
    /**
     * Waits for model object to be created
     * Steps the model to step forward stepDays
     */
    public void stepBackward() {
    	while (model == null);
    	if (stepDays < 1)
    		stepDays = 1;
    	model.step(-stepDays);
    	window.update();
    }
    
    
    /**
     * Sets the amount of days to step
     * @param days
     */
    public void setDays(String days) {
    	System.out.println(days);
    	int d = 1;
    	/*
    	//If string is incorrectly formatted, set to 1
    	try {
            d = Integer.parseInt(days);
    	} catch(NumberFormatException e) {
    		d = 1;
    	}
    	*/
    	if (d < 1)
    		stepDays = 1;
    	else
    		stepDays = d;
    }
    
    public void setDate(int year, int month, int day) {
    	while (model == null);
    	model.setDate(year, month, day);
    }
    
    
    /**
     * @return julian date in string
     */
    public String getJulianString() {
    	while (model == null);
    	double julian = model.getJulian();
    	return Double.toString(julian);
    }
    
    /**
     * @return gregorian date in string
     */
    public String getGregorianString() {
    	while (model == null);
    	//Get date
    	GregorianCalendar date = model.getDate();
    	int day = date.get(GregorianCalendar.DAY_OF_MONTH);
    	int month = date.get(GregorianCalendar.MONTH) + 1;
    	int year = date.get(GregorianCalendar.YEAR);
    	
    	//Convert to String
    	String dateStr = month + "/" + day + "/" + year; 	
    	
    	return dateStr;
    }
    
    public void pause() {
    	run = false;
    }
    
    public void autoRun(){
    	run = true;
    	while (run) {
    	    model.getJulian();
            model.step(stepDays);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
            	e.printStackTrace();
            }
        }
        
        
        
        
        
    }
    
    public static void setScale(double scale) {
    	model.setScale(scale);
    	window.refresh();
    	window.update();
    }
    
    
}

