/**
 * Class that defines a body that orbits
 *
 * @author barrsj, shieldsjpt
 * @version 1.0.0
 */
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Body {
    private String name;
    private int size;  //In pixels
    private double scaleFactor;
    private double orbitalPeriod; //In Earth years
    private double eccentricity; //e.g. 0 is perfect circle
    private double semiMajorAxis; //(*10^10 m)
    private double semiMinorAxis;
    private Color color;
    private ImagePattern pattern;
    private double x;
    private double xOffset;
    private boolean negX;
    private double y;
    private double yOffset;
    private boolean negY;
    private double orbitalAngle; //Angle at which the ellipse is rotated
    private double inclination; //Angle at which the orbit is tilted
    private boolean flipOrbit; //Used to change a body's perihelion
    private double lastMeanAnom;
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
             double angle, ImagePattern pattern) {
        this.setName(name);
        this.setSize(size);
        this.setOrbitalPeriod(orbitalPeriod);
        this.setEccentricity(eccentricity);
        this.setSemiMajorAxis(semiMajorAxis);
        this.setColor(color);
        this.pattern = pattern;
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
    	pattern = null;
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

    public double getOrbitalAngle() { return orbitalAngle; }

    public double getAngle() { return angle; }
    
    public double getInclination() { return inclination; }
    
    public double getLastMeanAnomaly() { return lastMeanAnom; }

    public boolean isVisible() { return visible; }

	public ImagePattern getPattern() { return pattern; }

	public boolean getShowLine() { return showLine; }


    /***************** Mutators ********************/
    public void setName(String name) { this.name = name; }

    public void setScaleFactor(double scaleFactor) { this.scaleFactor = scaleFactor; }

    public void setEccentricity(double eccentricity) { this.eccentricity = eccentricity; }

    public void setOrbitalPeriod(double orbitalPeriod) { this.orbitalPeriod = orbitalPeriod; }

    public void setColor(Color color) { this.color = color; }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }

    public void setVisible(boolean d) { visible = d; }

	public void setShowLine(boolean showLine) { this.showLine = showLine; }

	public void setPattern(ImagePattern pattern) { this.pattern = pattern; }

    public void setAngle(double angle) { 
    	angle %= (2*Math.PI);
    	if (angle < 0)
    		this.angle = ((2*Math.PI)-angle);
    	else
    	    this.angle = angle; 
    }


    public void flipOrbit() { this.flipOrbit = true; }
    
    /**
     * Sets the inclination of the orbit
     * Semimajor axis must be set before calling this method
     * @param angle
     */
    public void setInclination(double angle) { 
    	this.inclination = Math.toRadians(angle); 
        
        //Calculate inclination adjustment 
        if (inclination != 0)
        	semiMajorAxis = semiMajorAxis*(Math.cos(inclination));
    } 
    
    public void setLastMeanAnomaly(double meanAnomaly) { this.lastMeanAnom = meanAnomaly; }

    public void setOrbitalAngle(double angle) { this.orbitalAngle = (angle % (2* Math.PI)); }


    /**
     * @param size to set
     */
    public void setSize(int size) {
    	if (size < 8)
    		this.size = 8;
    	else if (size > 90)
    		this.size = 90;
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
     * Sets x & y offsets based on the eccentricity and
     * the semimajor/minor axis.
     *
     * Eccentricity, semiMajorAxis, and offsetNegation must
     * be set before calling this method.
     */
    public void setOffsets() {
    	/*
    	xOffset = eccentricity * semiMajorAxis;
        yOffset = eccentricity * semiMinorAxis;
        */
    	double distance = eccentricity * semiMajorAxis;
    	xOffset = Math.sqrt((Math.pow(distance, 2)/2));
    	yOffset = xOffset;
        if (negX) xOffset = -xOffset;
        if (negY) yOffset = -yOffset;
        
        //Add flipAngle
        double flip = 0;
        if (flipOrbit)
        	flip = Math.PI;

        //Set angle
        orbitalAngle = Math.atan((yOffset/xOffset)) + flip;
    }

    /**
     * Sets control fields to negate x and/or y offsets.
     * While offsets can be computed algorithmically, their +/- positioning cannot.
     * @param negX negate xOffset
     * @param negY negate yOffset
     */
    public void offsetNegation(boolean negX, boolean negY) {
    	this.negX = negX;
    	this.negY = negY;
    }


}