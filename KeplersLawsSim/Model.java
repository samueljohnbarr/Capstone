import java.awt.Color;
import java.util.ArrayList;

/**
 * Models each planet
 * Just Earth for the time being
 * 
 * @author barrsj
 * @version 1/31/18
 */
public class Model {
    public ArrayList<Body> bodies;
    
    public Model() {
        bodies = new ArrayList<Body>();
        bodies.add(new Body("Earth", 30, 1, 0.167, 15, Color.BLUE, 0, 0));
    }
    
    /**
     * Steps all planet coordinates by one day
     * @param forward step (true) / backward step (false)
     * @param circumference of orbit in pixels
     */
    public void step(boolean forward, int circumference) {
        for (int i = 0; i < bodies.size(); i++) {
            //blah
        }
    }
}
