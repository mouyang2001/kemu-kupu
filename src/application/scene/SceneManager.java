package application.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls which scene is displayed to the
 * user through changing the JavaFX stage.
 */
public class SceneManager {
	private static Stage stage;
	
	/**
	 * Show the main menu scene to the user.
	 * This must be called before any switch... methods.
	 * 
	 * @param stage The JavaFX stage to use.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
    public static void show(Stage stage) throws IOException {
    	SceneManager.stage = stage;
    	
    	// Load empty to allow scene switch.
    	stage.setScene(new Scene(new Group()));
    	
    	// Show menu scene.
    	switchToMenuScene();
    	stage.show();
    }
    
    /**
     * Show the menu scene to the user.
     * 
     * @throws IOException If FXML or CSS resources fail to load.
     */
    public static void switchToMenuScene() throws IOException {
        changeScene("Menu");
    }
    
    /**
     * Show the topic scene to the user.
     * 
     * @throws IOException If FXML or CSS resources fail to load.
     */
    public static void switchToTopicScene() throws IOException {
        changeScene("Topic");
    }
    
    /**
     * Show the quiz scene to the user.
     * 
     * @throws IOException If FXML or CSS resources fail to load.
     */
    public static void switchToQuizScene() throws IOException {
        changeScene("Quiz");
    }
    
    /**
     * Show the finish scene to the user.
     * 
     * @throws IOException If FXML or CSS resources fail to load.
     */
    public static void switchToFinishScene() throws IOException {
        changeScene("Finish");
    }
    
    /**
     * Changes the currently displayed scene to the user.
     * 
     * @param name The name of the FXML and CSS files without the file extension.
     * @throws IOException If FXML or CSS resources fail to load.
     */
    private static void changeScene(String name) throws IOException {
    	// Load resources.
    	Parent root = FXMLLoader.load(SceneManager.class.getResource("fxml/" + name + ".fxml"));
    	String css = SceneManager.class.getResource("css/" + name + ".css").toExternalForm();
    	
    	// Show new scene.
    	Scene scene = stage.getScene();
    	scene.setRoot(root);
    	
    	// Apply CSS.
    	scene.getStylesheets().clear();
    	scene.getStylesheets().add(css);
    }
    
    /**
     * Closes the JavaFX window.
     */
    public static void closeWindow() {
    	stage.close();
    }
}
