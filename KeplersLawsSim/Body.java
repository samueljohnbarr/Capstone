import java.awt.Color; //javafx.scene.paint.Color

/**
 * Class that defines a body that orbits 
 * 
 * @author barrsj
 * @version 1/31/2018
 */

public class Body {
    private String name;
    private int size;  //In pixels
    private float orbitalPeriod; // In Earth years
    private float eccentricity; // e.g. 0 is perfect circle
    private float semiMajorAxis;
    private Color color;
    
    /**
     * Body Constructor
     * @param name of planetary object
     * @param size in pixels of object
     * @param orbitalPeriod in Earth years
     * @param eccentricity how ecliptic the orbital path is
     * @param semiMajorAxis essential radius
     * @param color of object
     */
    public Body(String name, int size, float orbitalPeriod, 
             float eccentricity, float semiMajorAxis, Color color) {
        this.setName(name);
        this.setSize(size);
        this.setOrbitalPeriod(orbitalPeriod);
        this.setEccentricity(eccentricity);
        this.setSemiMajorAxis(semiMajorAxis);
        this.setColor(color);
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return semiMajorAxis
     */
    public float getSemiMajorAxis() {
        return semiMajorAxis;
    }

    /**
     * @param semiMajorAxis to set
     */
    public void setSemiMajorAxis(float semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    /**
     * @return eccentricity
     */
    public float getEccentricity() {
        return eccentricity;
    }
    
    /**
     * @param eccentricity
     */
    public void setEccentricity(float eccentricity) {
        this.eccentricity = eccentricity;
    }

    /**
     * @return the orbitalPeriod
     */
    public float getOrbitalPeriod() {
        return orbitalPeriod;
    }

    /**
     * @param orbitalPeriod the orbitalPeriod to set
     */
    public void setOrbitalPeriod(float orbitalPeriod) {
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
    
    
    
}
