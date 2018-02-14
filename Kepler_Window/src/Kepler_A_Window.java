import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javafx.application.Application;
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



public class Kepler_A_Window extends Application{


Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

ArrayList<planet> planets = new ArrayList<planet>();

double centerX;
double centerY;
double sunCenter = 100;


	public void start(Stage primary) throws Exception {
		
		BorderPane root = new BorderPane();
		
		Image backgroundImage = new Image("Starry-Sky-004.png");
		BackgroundSize bs = new BackgroundSize(screen.getWidth(),screen.getHeight(), false, false, false, false);
		root.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, bs)));
		
		Scene primeScene = new Scene(root,screen.getWidth(),screen.getHeight());
		primeScene.setFill(Color.BLACK);
		
		MenuBar mainMenu = setUpMenus();
		mainMenu.prefWidthProperty().bind(primary.widthProperty());
		root.setTop(mainMenu);
		
		Canvas space = new Canvas(screen.getWidth(),screen.getHeight());
		GraphicsContext gc = space.getGraphicsContext2D();
		draw(gc);
		root.getChildren().add(space);
		
		
		primary.setScene(primeScene);
		primary.setTitle("Kepler");
		primary.getIcons().add(new Image(Kepler_A_Window.class.getResourceAsStream("icon.png")));
		primary.setMaximized(true);
		primary.show();
		
	}

	public void draw(GraphicsContext gc) {
		
		centerX = screen.getWidth()/2;
		centerY = screen.getHeight()/2;
		gc.setFill(Color.YELLOW);
		gc.setLineWidth(5);
		gc.fillOval(centerX-(sunCenter/2), centerY-(sunCenter/2), sunCenter, sunCenter);
		
		gc.setStroke(Color.RED);
		gc.setLineWidth(3);
		gc.strokeOval(centerX-250, centerY-250, 500, 500);
		
		gc.setFill(Color.DARKRED);
		gc.setLineWidth(5);
		gc.fillOval(centerX-200, centerY-200, 50, 50);
		

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
	
	public static void main (String[] args) {
		launch(args);
	}
	
}
