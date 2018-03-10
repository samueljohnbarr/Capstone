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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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
    private Controller controller = new Controller();
    private ArrayList<Body> bodies;
    private Group rings;
    private Group lines;
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
        space.toBack();
        root.getChildren().add(space);
        
        //Get controller variables
        bodies = controller.getBodies();
        julian = new SimpleStringProperty();
        gregorian = new SimpleStringProperty();
        julian.setValue(controller.getJulianString());        
        gregorian.setValue(controller.getGregorianString());
        
        /****** Initialize Sim Objects ******/
        //Initialize rings
        rings = new Group();
        rings = initRings(rings);
        root.getChildren().add(rings);
        
        //Initialize lines
        lines = new Group();
        lines = initLines(lines);
        root.getChildren().add(lines);
              
        //Initialize planets
        planets = new Group();
        planets = initPlanets(planets);
        root.getChildren().add(planets);
        planets.toBack();
        lines.toBack();
        rings.toBack();
        
        /****** Create menu bar ******/
        MenuBar mainMenu = setUpMenus();
        mainMenu.prefWidthProperty().bind(primary.widthProperty());
        mainMenu.toFront();
        root.setTop(mainMenu);
        
        /******* Initialize Control Panel *******/
        TilePane contPan = new TilePane();
        root.setBottom(contPan);
        contPan.setAlignment(Pos.BASELINE_CENTER);
        contPan.setPrefColumns(3);
        contPan.setHgap(290);
        contPan.setStyle("-fx-background-color: black");
        
        HBox scalePan = initScaler();
        scalePan.setAlignment(Pos.CENTER_LEFT);
        HBox buttPan = initButtons();
        buttPan.setAlignment(Pos.BASELINE_CENTER);
        VBox inPan = initInputs(); 
        inPan.setAlignment(Pos.BASELINE_RIGHT);
        
        //Add all & align
        contPan.getChildren().addAll(scalePan, buttPan, inPan);
        
        /****** Initialize Date Labels *******/
        VBox labPan = initLabels();
        labPan.setAlignment(Pos.BASELINE_RIGHT);
        root.setRight(labPan);
        

        primary.setScene(primeScene);
        primary.setTitle("Kepler");
        primary.getIcons().add(new Image(Kepler_A_Window.class.getResourceAsStream("icon.png")));
        primary.setMaximized(true);
        primary.show();
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
            ring.setRotate(Math.toDegrees(planet.getOrbitalAngle()));
            ring.toBack();
            
            //Set visibility
            ring.setVisible(planet.isVisible());
                      
            //Add to group
            g.getChildren().addAll(ring);    
        }
        return g;
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
     * Creates line objects from planets to sun
     * @param line group
     * @return initialized lineGroup
     */
    private Group initLines(Group g) {
    	for (int i = 0; i < bodies.size(); i++) {
    		Body planet = bodies.get(i);
    		//create line
    		Line line = new Line();
    		line.setStartX(screen.getWidth()/2);
    		line.setStartY(screen.getHeight()/2);
    		line.setEndX(screen.getWidth()/2  + planet.getX());// - planet.getSize()/2);
    		line.setEndY(screen.getHeight()/2 + planet.getY());// - planet.getSize()/2);
    		line.setStroke(Color.WHITE);
    		line.setVisible(planet.getShowLine()); 
    		
    		//Add to group
    		g.getChildren().add(line);
    	}
    	return g;
    }
    
    /**
     * Updates planet locations and lines on the model
     */
    private void updatePlanetCoordinates() {
        Platform.runLater(new Runnable() {
            public void run() {
                bodies = controller.getBodies();
                ObservableList<Node> nPlanets = planets.getChildren();
                ObservableList<Node> nLines = lines.getChildren();

                for (int i = 0; i < bodies.size(); i++) {
                    Body planet = bodies.get(i);
                    
                    nPlanets.get(i).relocate(
                            screen.getWidth()/2  + planet.getX() - planet.getSize()/2, 
                            screen.getHeight()/2 + planet.getY() - planet.getSize()/2);
                    
                    Line line = (Line) nLines.get(i);
                    line.setEndX(screen.getWidth()/2  + planet.getX()); 
                    line.setEndY(screen.getHeight()/2 + planet.getY());
                    nLines.set(i, line);                   
                }
            }
        });
        
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
            	rings = new Group();
            	lines = new Group();
            	planets = new Group();

            	root.getChildren().set(0, rings); 
            	root.getChildren().set(1, lines); 
                root.getChildren().set(2, planets);
                root.requestLayout();
            	
            	rings = initRings(rings);
            	lines = initLines(lines);
            	planets = initPlanets(planets);

            	root.getChildren().set(0, rings);
            	root.getChildren().set(1, lines);
            	root.getChildren().set(2, planets);
            	
            	//Update root
            	root.requestLayout();
            	
            }
    	});  	
    }
    
    
    /**
     * Initializes the menu bar in the simulation
     * @param 
     * @return  mainMenu
     */
    public MenuBar setUpMenus() {
        MenuBar mainMenu = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        
            MenuItem resetFileMenu = new MenuItem("Reset");
            resetFileMenu.setOnAction(e -> {});
        	MenuItem exitFileMenu = new MenuItem("Exit");
        	exitFileMenu.setOnAction(e -> {System.exit(1);});
        
            fileMenu.getItems().addAll(resetFileMenu, exitFileMenu);
        
        Menu viewMenu = new Menu("View");
        
        Menu zoomTo = new Menu("Focous On");
		MenuItem focousOnMercury = new MenuItem("Mercury");
		focousOnMercury.setOnAction(e -> {Controller.setScale(69);});
		MenuItem focousOnVenus = new MenuItem("Venus");
		focousOnVenus.setOnAction(e -> {Controller.setScale(37);});
		MenuItem focousOnEarth = new MenuItem("Earth");
		focousOnEarth.setOnAction(e -> {Controller.setScale(26);});
		MenuItem focousOnMars = new MenuItem("Mars");
		focousOnMars.setOnAction(e -> {Controller.setScale(20);});
		MenuItem focousOnJupiter = new MenuItem("Jupiter");
		focousOnJupiter.setOnAction(e -> {Controller.setScale(5.2);});
		MenuItem focousOnSatern = new MenuItem("Saturn");
		focousOnSatern.setOnAction(e -> {Controller.setScale(3);});
		MenuItem focousOnUrnis = new MenuItem("Uranus");
		focousOnUrnis.setOnAction(e -> {Controller.setScale(1.5);});
		MenuItem focousOnNeptune = new MenuItem("Neptune");
		focousOnNeptune.setOnAction(e -> {Controller.setScale(.9);});
		MenuItem focousOnPluto = new MenuItem("Pluto");
		focousOnPluto.setOnAction(e -> {Controller.setScale(.7);});
		MenuItem focousOnHC = new MenuItem("HC");
		MenuItem focousOnComit = new MenuItem("The other one");
		zoomTo.getItems().addAll(focousOnMercury, focousOnVenus, focousOnEarth, focousOnMars, focousOnJupiter, focousOnSatern, focousOnUrnis, focousOnNeptune, focousOnPluto, focousOnHC, focousOnComit);


        	
		Menu lineOn = new Menu("Show Line");
		CheckMenuItem lineOnMercury = new CheckMenuItem("Mercury");
		lineOnMercury.setOnAction(e -> {controller.getBodies().get(1).setShowLine(!controller.getBodies().get(1).getShowLine());refresh();update();});
		CheckMenuItem lineOnVenus = new CheckMenuItem("Venus");
		lineOnVenus.setOnAction(e -> {controller.getBodies().get(2).setShowLine(!controller.getBodies().get(2).getShowLine());refresh();update();});
		CheckMenuItem lineOnEarth = new CheckMenuItem("Earth");
		lineOnEarth.setOnAction(e -> {controller.getBodies().get(3).setShowLine(!controller.getBodies().get(3).getShowLine());refresh();update();});
		CheckMenuItem lineOnMars = new CheckMenuItem("Mars");
		lineOnMars.setOnAction(e -> {controller.getBodies().get(4).setShowLine(!controller.getBodies().get(4).getShowLine());refresh();update();});
		CheckMenuItem lineOnJupiter = new CheckMenuItem("Jupiter");
		lineOnJupiter.setOnAction(e -> {controller.getBodies().get(5).setShowLine(!controller.getBodies().get(5).getShowLine());refresh();update();});
		CheckMenuItem lineOnSatern = new CheckMenuItem("Saturn");
		lineOnSatern.setOnAction(e -> {controller.getBodies().get(6).setShowLine(!controller.getBodies().get(6).getShowLine());refresh();update();});
		CheckMenuItem lineOnUrnis = new CheckMenuItem("Uranus");
		lineOnUrnis.setOnAction(e -> {controller.getBodies().get(7).setShowLine(!controller.getBodies().get(7).getShowLine());refresh();update();});
		CheckMenuItem lineOnNeptune = new CheckMenuItem("Neptune");
		lineOnNeptune.setOnAction(e -> {controller.getBodies().get(8).setShowLine(!controller.getBodies().get(8).getShowLine());refresh();update();});
		CheckMenuItem lineOnPluto = new CheckMenuItem("Pluto");
		lineOnPluto.setOnAction(e -> {controller.getBodies().get(9).setShowLine(!controller.getBodies().get(9).getShowLine());refresh();update();});
		CheckMenuItem lineOnHC = new CheckMenuItem("HC");
		CheckMenuItem lineOnComit = new CheckMenuItem("The other one");
		lineOn.getItems().addAll(lineOnMercury, lineOnVenus, lineOnEarth, lineOnMars, lineOnJupiter,
		         lineOnSatern, lineOnUrnis, lineOnNeptune, lineOnPluto, lineOnHC, lineOnComit);
        	
        	viewMenu.getItems().addAll(zoomTo, lineOn);
            
        Menu helpMenu = new Menu("Help");
        
            MenuItem aboutHelpMenu = new MenuItem("About"); 
            MenuItem helpHelpMenu = new MenuItem("Help");
            helpMenu.getItems().addAll(aboutHelpMenu, helpHelpMenu);
        
        mainMenu.getMenus().addAll(fileMenu, viewMenu, helpMenu);
        mainMenu.toFront();
        
        return mainMenu;
        
    }
    
    public HBox initButtons() {
    	HBox buttonLine = new HBox(10);
    	
    	//Make button shape
    	Circle s = new Circle();
    	s.setRadius(10);
    	
    	//Step buttons
    	Button autoAdvance = new Button(">>");
    	autoAdvance.setShape(s);
    	Button advance = new Button("> ");
    	advance.setShape(s);
    	Button pause = new Button("||");
    	pause.setShape(s);
    	Button reverse = new Button(" <");
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
    	buttonLine.toFront();
    	
    	return buttonLine;
    }
    
    
    public VBox initLabels() {
    	VBox labels = new VBox(5);
    	
    	HBox julianb = new HBox(5);
    	Label jul = new Label("   Julian:");
    	jul.setTextFill(Color.WHITE);
    	jul.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	Label julianDay = new Label("");
    	julianDay.setFont(Font.font("Arial", FontPosture.ITALIC, 20));
    	julianDay.setTextFill(Color.WHITE);
    	julianDay.textProperty().bind(julian);
    	julianb.getChildren().addAll(jul, julianDay);
    	
    	HBox gregb = new HBox(5);
    	Label greg = new Label("   Gregorian:");
    	greg.setTextFill(Color.WHITE);
    	greg.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	Label gregDate = new Label("");
    	gregDate.setFont(Font.font("Arial", FontPosture.ITALIC, 20));
    	gregDate.setTextFill(Color.WHITE);
    	gregDate.textProperty().bind(gregorian);
    	gregb.getChildren().addAll(greg, gregDate);
    	
    	labels.getChildren().addAll(gregb, julianb);
    	VBox.setMargin(labels, new Insets(5, 5, 5, 5));
    	
    	labels.setAlignment(Pos.CENTER_LEFT);
    	
    	return labels;
    }
    
    public VBox initInputs() {
    	//Step day input & label
    	HBox advanceByLine = new HBox();
    	advanceByDate = new TextField("1");
    	advanceByDate.setPrefColumnCount(2);
    	advanceByDate.setEditable(true);
    	Label advanceByLabel = new Label("  Days to step ");
    	
    	advanceByLabel.setTextFill(Color.WHITE);
    	advanceByLine.getChildren().addAll(advanceByLabel, advanceByDate);
    	
   	
    	//Gregorian date input and label
    	HBox gregLine = new HBox();
    	TextField month = new TextField();
    	TextField day = new TextField();
    	TextField year = new TextField();
    	month.setPrefColumnCount(2);
    	day.setPrefColumnCount(2);
    	year.setPrefColumnCount(2);
    	
    	Label slash = new Label(" / ");
    	Label slash1 = new Label(" / ");
    	
    	Label gregLabel = new Label("  Set Date ");
    	gregLabel.setTextFill(Color.WHITE);
    	gregLine.getChildren().addAll(gregLabel, month, slash, day, slash1, year);
    	
    	VBox inPanel = new VBox();
    	inPanel.getChildren().addAll(advanceByLine, gregLine);
    	
    	inPanel.setAlignment(Pos.CENTER_RIGHT);
    	
    	return inPanel;
    }
    
    public HBox initScaler() {
    	HBox zoom = new HBox();
    	Slider scaler = new Slider(0.5, 30, 15);
    	scaler.setShowTickMarks(true);
    	scaler.setMaxWidth(200);
    	
    	scaler.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    Controller.setScale(new_val.doubleValue());
            }
        });
    	
    	Label zLabel = new Label("Scaler  ");
    	zLabel.setTextFill(Color.WHITE);
    	zLabel.setAlignment(Pos.CENTER);
    	
    	zoom.getChildren().addAll(zLabel, scaler);
    	HBox.setMargin(zoom, new Insets(5,5,5,5));
    	
    	return zoom;
    }
    
    public void stop() throws Exception {
    	System.exit(0);
    }

    public void draw(GraphicsContext gc) {
        
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}


