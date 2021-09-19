package application.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
	private static Stage stage;
	
    public static void show(Stage stage) throws IOException {
    	SceneManager.stage = stage;
    	
    	// Load empty to allow scene switch.
    	stage.setScene(new Scene(new Group()));

    	switchToMenuScene();
    	stage.show();
    }
    
    public static void switchToMenuScene() throws IOException {
        changeScene("Menu");
    }

    public static void switchToTopicScene() throws IOException {
        changeScene("Topic");
    }
    
    public static void switchToQuizScene() throws IOException {
        changeScene("Quiz");
    }
    
    public static void switchToFinishScene() throws IOException {
       changeScene("Finish");
    }
    
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
    
    public static void closeWindow() {
    	stage.close();
    }
}
