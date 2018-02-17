import javafx.scene.paint.Color;

/**
 * Class that defines a body that orbits 
 * 
 * @author barrsj
 * @version 1/31/2018
 */

public class Body {
    private String name;
    private int size;  //In pixels
    private double orbitalPeriod; //In Earth years
    private double eccentricity; //e.g. 0 is perfect circle
    private double semiMajorAxis; //(*10^10 m)
    private double semiMinorAxis;
    private Color color;
    private double x;
    private double xOffset;
    private double y;
    private double yOffset;
    private double angle;
    
    /**
     * Body Constructor
     * @param name of planetary object
     * @param size in pixels of object
     * @param orbitalPeriod in Earth years
     * @param eccentricity how ecliptic the orbital path is
     * @param semiMajorAxis essential radius
     * @param color of object
     */
    public Body(String name, int size, double orbitalPeriod, 
             double eccentricity, double semiMajorAxis, Color color,
             double angle) {
        this.setName(name);
        this.setSize(size);
        this.setOrbitalPeriod(orbitalPeriod);
        this.setEccentricity(eccentricity);
        this.setSemiMajorAxis(semiMajorAxis);
        this.setColor(color);
        this.x = semiMajorAxis;
        this.y = 0;
        this.angle = angle;
    }
    
    public Body() {
    	name = "";
    	size = 0;
    	orbitalPeriod = 0;
    	eccentricity = 0;
    	semiMajorAxis = 0;
    	color = null;
    	x = 0;
    	y = 0;
    	angle = 0;
    }

    /**
     * @return name
     */
    public String getName() { return name; }

    /**
     * @param name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return size
     */
    public int getSize() { return size; }

    /**
     * @param size to set
     */
    public void setSize(int size) {
    	if (size < 5)
    		this.size = 5;
    	else
    	    this.size = size; 
    }

    /**
     * @return semiMajorAxis
     */
    public double getSemiMajorAxis() { return semiMajorAxis; }

    /**
     * Sets semiMajorAxis, semiMinorAxis, and x & y offsets
     * Eccentricity must be set to calculate correctly
     * @param semiMajorAxis to set
     */
    public void setSemiMajorAxis(double semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
        semiMinorAxis = semiMajorAxis * Math.sqrt(1 - Math.pow(eccentricity, 2));
        
        
    }

    /**
     * @return semiMinorAxis
     */
    public double getSemiMinorAxis() { return semiMinorAxis; }
    
    /**
     * @return eccentricity
     */
    public double getEccentricity() { return eccentricity; }
    
    /**
     * @param eccentricity
     */
    public void setEccentricity(double eccentricity) {
        this.eccentricity = eccentricity;
    }

    /**
     * @return the orbitalPeriod
     */
    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    /**
     * @param orbitalPeriod the orbitalPeriod to set
     */
    public void setOrbitalPeriod(double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return x
     */
    public double getX() {
        return x;
    }
    
    /**
     * @param x to set
     */
    public void setX(double x) {
       this.x = x;
    }

    /**
     * @return y
     */
    public double getY() {
        return y;
    }
    
    /**
     * @param y to set
     */
    public void setY(double y) {
       this.y = y;
    } 

    /**
     * @return angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * @param angle to set
     */
    public void setAngle(double angle) {
         this.angle = (angle % (2 * Math.PI));
    }
}
