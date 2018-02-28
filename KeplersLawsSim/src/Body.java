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
    private double scaleFactor;
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
    private boolean visible;
    private boolean showLine;
    
    /**
     * Body Constructor
     * @param name of planetary object
     * @param size in pixels of object
     * @param orbitalPeriod in Earth years
     * @param eccentricity how elliptic the orbital path is
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
        this.visible = true;
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
    	visible = true;
    }

    /********************* Accessors *************************/
    public String getName() { return name; }
    
    public int getSize() { return size; }
    
    public double getScaleFactor() { return scaleFactor; }
    
    public double getSemiMajorAxis() { return semiMajorAxis; }
    
    public double getSemiMinorAxis() { return semiMinorAxis; }
    
    public double getEccentricity() { return eccentricity; }
    
    public double getOrbitalPeriod() { return orbitalPeriod; }
    
    public Color getColor() { return color; }
    
    public double getX() { return x; }
    
    public double getXOffset() { return xOffset; }
    
    public double getY() { return y; }
    
    public double getYOffset() { return yOffset; }
    
    public double getAngle() { return angle; }
    
    public boolean isVisible() { return visible; }
    

    /***************** Mutators ********************/
    public void setName(String name) { this.name = name; }
    
    public void setScaleFactor(double scaleFactor) { this.scaleFactor = scaleFactor; }
    
    public void setEccentricity(double eccentricity) { this.eccentricity = eccentricity; }

    public void setOrbitalPeriod(double orbitalPeriod) { this.orbitalPeriod = orbitalPeriod; }
    
    public void setColor(Color color) { this.color = color; }

    public void setX(double x) { this.x = x; }
    
    public void setY(double y) { this.y = y; } 

    public void setVisible(boolean d) { visible = d; }
    
    public void setAngle(double angle) {
         this.angle = (angle % (2 * Math.PI));
    }
    
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
     * Sets semiMajorAxis, semiMinorAxis, and x & y offsets
     * Eccentricity must be set to calculate correctly
     * @param semiMajorAxis to set
     */
    public void setSemiMajorAxis(double semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
        semiMinorAxis = semiMajorAxis * Math.sqrt(1 - Math.pow(eccentricity, 2));
    }
    
    /**
     * Sets x & y offsets
     * Eccentricity and semimajor/minor axis must be set
     */
    public void setOffsets() {
    	xOffset = eccentricity * semiMajorAxis;
        yOffset = -eccentricity * semiMinorAxis;
    }

	public boolean getShowLine() {
		return showLine;
	}

	public void setShowLine(boolean showLine) {
		this.showLine = showLine;
	}
}