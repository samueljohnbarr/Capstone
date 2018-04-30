
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


public class Controller {
    private static Model model;
    private static Model initialModel = new Model();
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
     * If parameter is incorrectly formatted, stepDays
     * will default to 1
     * @param days
     */
    public void setDays(String days) {
    	int d = 1;
    	//Attempt to change days
    	try { d = Integer.parseInt(days); } catch(NumberFormatException e) {
    		d = 1;
    	}
    	
    	if (d < 1)
    		stepDays = 1;
    	else
    		stepDays = d;
    }
    
    /**
     * Sets the model's date for the simulation
     * If any part of the date is incorrectly formatted,
     * setDate will use the current year/month/day instead
     * @param year
     * @param month
     * @param day
     */
    public void setDate(String year, String month, String day) {
    	while (model == null);
    	//Collect current date information
    	GregorianCalendar date = model.getDate();
    	int d = date.get(GregorianCalendar.DAY_OF_MONTH);
    	int m = date.get(GregorianCalendar.MONTH) + 1;
    	int y = date.get(GregorianCalendar.YEAR);
    	
    	//Attempt to set variables to new values
    	try { y = Integer.parseInt(year); } catch(NumberFormatException e) {
    		y = date.get(GregorianCalendar.YEAR); }
    	try { m = Integer.parseInt(month); } catch(NumberFormatException e) {
    		m = date.get(GregorianCalendar.MONTH) + 1;}
    	try { d = Integer.parseInt(day); } catch(NumberFormatException e) {
    		d = date.get(GregorianCalendar.DAY_OF_MONTH);
    	}   	
    	
    	model.setDate(y, m, d);
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
    
    public String getMonth() {
    	GregorianCalendar date = model.getDate();
    	return (date.get(GregorianCalendar.MONTH) + 1) + "";
    }
    
    public String getDay() {
    	GregorianCalendar date = model.getDate();
    	return (date.get(GregorianCalendar.DAY_OF_MONTH)) + "";
    }
    
    public String getYear() {
    	GregorianCalendar date = model.getDate();
    	return (date.get(GregorianCalendar.YEAR)) + "";
    }
    
    public static void setScale(double scale) {
    	model.setScale(scale);
    	window.refresh();
    	window.update();
    }

	public static Model getInitialModel() {
		return initialModel;
	}

	public static void setInitialModel(Model initialModel) {
		Controller.initialModel = initialModel;
	}
    
    
}
