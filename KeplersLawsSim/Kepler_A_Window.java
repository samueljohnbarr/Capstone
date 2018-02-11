import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;



public class Kepler_A_Window extends Application{

Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
private Controller controller = new Controller();
private ArrayList<Body> bodies;
private Group rings;
private Group planets;
    
    public void start(Stage primary) throws Exception {
        BorderPane root = new BorderPane();
        
        //Set background image
        Image backgroundImage = new Image("Starry-Sky-004.png");
        BackgroundSize bs = new BackgroundSize(screen.getWidth(),screen.getHeight(), false, false, false, false);
        root.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, bs)));
        
        Scene primeScene = new Scene(root,screen.getWidth(),screen.getHeight());
        primeScene.setFill(Color.BLACK);
        
        //Create menu bar
        MenuBar mainMenu = setUpMenus();
        mainMenu.prefWidthProperty().bind(primary.widthProperty());
        root.setTop(mainMenu);
        
        //Create context space to paint on
        Canvas space = new Canvas(screen.getWidth(),screen.getHeight());
        GraphicsContext spaceContext = space.getGraphicsContext2D();
        draw(spaceContext);
        root.getChildren().add(space);
        
        //Initialize rings
        rings = new Group();
        rings = initRings(rings);
        root.getChildren().add(rings);
        
        //Initialize planets
        planets = new Group();
        planets = initPlanets(planets);
        root.getChildren().add(planets);

        primary.setScene(primeScene);
        primary.setTitle("Kepler");
        primary.getIcons().add(new Image(Kepler_A_Window.class.getResourceAsStream("icon.png")));
        primary.setMaximized(true);
        primary.show();
        
    }

    public void draw(GraphicsContext gc) {
        double centerX = screen.getWidth()/2;
        double centerY = screen.getHeight()/2;
        int sunSize = 100;
        //Draw Sun
        gc.setFill(Color.YELLOW);
        gc.setLineWidth(5);
        gc.fillOval(centerX - sunSize/2, centerY - sunSize/2,
                sunSize, sunSize);
    }
    
    /**
     * Initializes the planets of the simulation
     * @param g planet group
     * @return ring group
     */
    private Group initPlanets(Group g) {
        bodies = controller.getBodies();
        for (int i = 0; i < bodies.size(); i++) {
            Body planet = bodies.get(i);
            
            //Create planet
            Circle p = new Circle();
            p.setFill(planet.getColor());
            p.setRadius(planet.getSize()/2);
            p.setCenterX(screen.getWidth()/2 + planet.getX());
            p.setCenterY(screen.getHeight()/2 + planet.getY());
            
            //Add to group
            g.getChildren().add(p);
        }
        return g;
    }
    
    /**
     * Initializes the orbital rings of the simulation
     * @param g ring group
     * @return initialized ring group
     */
    private Group initRings(Group g) {
        bodies = controller.getBodies();
        for (int i = 0; i < bodies.size(); i++) {
            Body planet = bodies.get(i);
            
            //Create orbital ring
            Circle orbit = new Circle();
            orbit.setFill(null);
            orbit.setStroke(Color.GRAY);
            orbit.setRadius(planet.getSemiMajorAxis());
            orbit.setCenterX(screen.getWidth()/2);
            orbit.setCenterY(screen.getHeight()/2);
            
            //Add to group
            g.getChildren().add(orbit);
        }
        return g;
    }
    
    private void updatePlanets() {
        bodies = controller.getBodies();
        
        ObservableList<Node> collection = planets.getChildren();
        for (int i = 0; i < bodies.size(); i++) {
            Body planet = bodies.get(i);
            collection.get(i).relocate(planet.getX(), planet.getY());
        }
        
    }
    
    public MenuBar setUpMenus() {
        MenuBar mainMenu = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        
            MenuItem exitFileMenu = new MenuItem("Exit");
        
            fileMenu.getItems().add(exitFileMenu);
        
        Menu editMenu = new Menu("Edit");
            
        Menu helpMenu = new Menu("Help");
        
            MenuItem aboutHelpMenu = new MenuItem("About");
            
            MenuItem helpHelpMenu = new MenuItem("Help");
            
            helpMenu.getItems().addAll(aboutHelpMenu, helpHelpMenu);
        
        mainMenu.getMenus().addAll(fileMenu, editMenu, helpMenu);
        
        return mainMenu;
        
    }
    
    public void update() {
        updatePlanets();
    }
    
    public void run(String[] args) {
        launch(args);
    }
    
}

