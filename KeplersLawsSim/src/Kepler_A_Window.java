import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import java.awt.Toolkit;


public class Kepler_A_Window extends Application {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private static final CountDownLatch latch = new CountDownLatch(1);
    private static Kepler_A_Window keplerWindow = null;
    private volatile BorderPane root;
    TextField advanceByDate;
    TextField newDate;
    private Controller controller = new Controller();
    private ArrayList<Body> bodies;
    private Group rings;
    private Group planets;
    private StringProperty julian;
    private StringProperty gregorian;
    
    /**
     * Waits for the window to be created
     * @return window object
     */
    public static Kepler_A_Window waitForWindow() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return keplerWindow;
    }
    
    /**
     * Sets the window
     * @param w window to set
     */
    public static void setWindow(Kepler_A_Window w) {
        keplerWindow = w;
        latch.countDown();
    }
    
    /**
     * Sets the window to itself
     */
    public Kepler_A_Window() {
        setWindow(this);
    }
    
    
    public void start(Stage primary) throws Exception {
        root = new BorderPane();
        
        //Set background image
        Image backgroundImage = new Image("milky.png");
        BackgroundSize bs = new BackgroundSize(screen.getWidth(),screen.getHeight(), false, false, false, false);
        root.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, bs)));
        
        Scene primeScene = new Scene(root,screen.getWidth(),screen.getHeight());
        primeScene.setFill(Color.BLACK);
        
        //Create context space to paint on
        Canvas space = new Canvas(screen.getWidth(),screen.getHeight());
        GraphicsContext spaceContext = space.getGraphicsContext2D();
        draw(spaceContext);
        root.getChildren().add(space);
        
        //Create menu bar
        MenuBar mainMenu = setUpMenus();
        mainMenu.prefWidthProperty().bind(primary.widthProperty());
        root.setTop(mainMenu);
        
        
        //Get controller variables
        bodies = controller.getBodies();
        julian = new SimpleStringProperty();
        gregorian = new SimpleStringProperty();
        julian.setValue(controller.getJulianString());        
        gregorian.setValue(controller.getGregorianString());
        
        /******* Initialize Control Panel *******/
        //TODO: Stack pane does not work
        Pane contPan = new StackPane();
        contPan.setStyle("-fx-background-color: black");
        
        VBox labPan = initLabels();
        HBox buttPan = initButtons();
        VBox inPan = initInputs(); 
        
        //Add all & align
        contPan.getChildren().addAll(labPan, buttPan, inPan);
        StackPane.setAlignment(labPan, Pos.CENTER_LEFT);
        StackPane.setAlignment(buttPan, Pos.CENTER);
        StackPane.setAlignment(inPan, Pos.CENTER_RIGHT);
        root.setBottom(contPan);
        
        
        /****** Initialize Sim Objects ******/
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
    
    public HBox initButtons() {
    	HBox buttonLine = new HBox(10);
    	
    	//Make button shape
    	Circle s = new Circle();
    	s.setRadius(10);
    	
    	//Step buttons
    	Button autoAdvance = new Button(">>");
    	autoAdvance.setShape(s);
    	Button advance = new Button(">");
    	advance.setShape(s);
    	Button pause = new Button("||");
    	pause.setShape(s);
    	Button reverse = new Button("<");
    	reverse.setShape(s);
    	Button autoReverse = new Button("<<");
    	autoReverse.setShape(s);
    	
    	buttonLine.getChildren().addAll(autoReverse, reverse, pause, advance, autoAdvance);
    	
    	//Set margins and align
    	HBox.setMargin(autoAdvance, new Insets(10, 0, 10, 0));
    	HBox.setMargin(advance, new Insets(10, 0, 10, 0));
    	HBox.setMargin(pause, new Insets(10, 0, 10, 0));
    	HBox.setMargin(reverse, new Insets(10, 0, 10, 0));
    	HBox.setMargin(autoReverse, new Insets(10, 0, 10, 0));
    	buttonLine.setAlignment(Pos.CENTER);
    	
    	
    	//TODO: this shit breaks shit
    	autoAdvance.setOnAction(e -> {
    		controller.autoRun();
    		update();
    	});
    	
    	advance.setOnAction(e -> {
    		controller.setDays(advanceByDate.getText());
    		controller.stepForward();
    		update();
    	});
    	
    	pause.setOnAction(e -> {
    		controller.pause();
    	});
    	
    	reverse.setOnAction(e -> {
    		controller.setDays(advanceByDate.getText());
    		controller.stepBackward();
    		update();
    	});
    	
    	return buttonLine;
    }
    
    
    public VBox initLabels() {
    	VBox labels = new VBox(5);
    	
    	HBox julianb = new HBox(5);
    	Label jul = new Label("Julian:");
    	jul.setTextFill(Color.WHITE);
    	Label julianDay = new Label("");
    	julianDay.setTextFill(Color.WHITE);
    	julianDay.textProperty().bind(julian);
    	julianb.getChildren().addAll(jul, julianDay);
    	
    	HBox gregb = new HBox(5);
    	Label greg = new Label("Gregorian:");
    	greg.setTextFill(Color.WHITE);
    	Label gregDate = new Label("");
    	gregDate.setTextFill(Color.WHITE);
    	gregDate.textProperty().bind(gregorian);
    	gregb.getChildren().addAll(greg, gregDate);
    	
    	HBox.setMargin(julianb, new Insets(5, 5, 5, 5));
    	HBox.setMargin(gregb, new Insets(5, 5, 5, 5));
    	labels.getChildren().addAll(julianb, gregb);
    	VBox.setMargin(labels, new Insets(5, 5, 5, 5));
    	
    	labels.setAlignment(Pos.CENTER_LEFT);
    	
    	return labels;
    }
    
    public VBox initInputs() {
    	//Step day input & label
    	HBox advanceByLine = new HBox();
    	advanceByDate = new TextField("1");
    	advanceByDate.setPrefColumnCount(10);
    	advanceByDate.setEditable(true);
    	Label advanceByLabel = new Label("Days to step ");
    	
    	advanceByLabel.setTextFill(Color.WHITE);
    	advanceByLine.getChildren().addAll(advanceByLabel, advanceByDate);
    	
   	
    	//Gregorian date input and label
    	HBox gregLine = new HBox();
    	TextField newDate = new TextField();
    	newDate.setPrefColumnCount(10);
    	newDate.setEditable(false);
    	Label gregLabel = new Label("New Date ");
    	gregLabel.setTextFill(Color.WHITE);
    	gregLine.getChildren().addAll(gregLabel, newDate);
    	
    	VBox inPanel = new VBox();
    	inPanel.getChildren().addAll(advanceByLine, gregLine);
    	
    	inPanel.setAlignment(Pos.CENTER_RIGHT);
    	
    	return inPanel;
    }
    
    public void stop() throws Exception {
    	System.exit(0);
    }

    public void draw(GraphicsContext gc) {
        
    }
    
    /**
     * Initializes the planets of the simulation
     * @param g planet group
     * @return ring group
     */
    private Group initPlanets(Group g) {
        for (int i = 0; i < bodies.size(); i++) {
            Body planet = bodies.get(i);
            
            //Create planet
            Circle p = new Circle();
            p.setFill(planet.getColor());
            p.setRadius(planet.getSize()/2);
            p.setCenterX(screen.getWidth()/2 + planet.getX());
            p.setCenterY(screen.getHeight()/2 + planet.getY());
            
            //Set visibility
            p.setVisible(planet.isVisible());
            
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
        for (int i = 1; i < bodies.size(); i++) {
            Body planet = bodies.get(i);
            //Create orbital ring
            Ellipse ring = new Ellipse();
            ring.setFill(null);
            ring.setStroke(Color.GRAY);
            ring.setRadiusX(planet.getSemiMajorAxis());
            ring.setRadiusY(planet.getSemiMinorAxis());
            ring.setCenterX(planet.getXOffset() + screen.getWidth()/2);
            ring.setCenterY(planet.getYOffset() + screen.getHeight()/2);
            
            //Set visibility
            ring.setVisible(planet.isVisible());
            
            //Add to group
            g.getChildren().add(ring);
            
        }
        return g;
    }
    
    /**
     * Updates planet locations from the model
     */
    private void updatePlanetCoordinates() {
        Platform.runLater(new Runnable() {
            public void run() {
                bodies = controller.getBodies();
                //while (planets == null);
                ObservableList<Node> collection = planets.getChildren();
                for (int i = 0; i < bodies.size(); i++) {
                    Body planet = bodies.get(i);
                    
                    collection.get(i).relocate(
                            screen.getWidth()/2  + planet.getX() - planet.getSize()/2, 
                            screen.getHeight()/2 + planet.getY() - planet.getSize()/2);
                }
            }
        });
        
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
        updatePlanetCoordinates();
        julian.setValue(controller.getJulianString());        
        gregorian.setValue(controller.getGregorianString());
    }
    
    /**
     * Reinitializes all visual planet and ring objects
     * Used for zoom controls
     */
    public void refresh() {
    	Platform.runLater(new Runnable() {
            public void run() {
            	bodies = controller.getBodies();
            	
            	//Kill off current models
            	//while (root == null);
            	ObservableList<Node> children = root.getChildren();          	

            	rings = new Group();
            	planets = new Group();
            	root.getChildren().set(children.size()-2, rings);
                root.getChildren().set(children.size()-1, planets);
                root.requestLayout();
            	
            	rings = initRings(rings);
            	planets = initPlanets(planets);
            	
                root.getChildren().set(children.size()-2, rings);
                root.getChildren().set(children.size()-1, planets);
                
                //Update coordinates
                //update();
            	
            	//Update root
            	root.requestLayout();
            	
            }
    	});
    	
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}

