package application.scene;

import application.Wordlist;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * SceneManager manages the current view of JavaFX.
 * The current scene can be changed with the show... methods.
 * A dialog box can be shown to the user with the alert method.
 */
public class SceneManager {
	private Stage stage;
	
	private Scene scene;

	/**
	 * Creates a SceneManager to manage a JavaFX stage.
	 *
	 * @param stage JavaFX stage to manage.
	 */
	public SceneManager(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Shows the initial scene to the user.
	 * Defaults to the menu scene.
	 * This must be called before any other show... methods.
	 */
	public void show() {
		// Create menu scene.
		scene = new Menu(this);
		
		// Get nodes to display.
		Parent root = scene.render();
		scene.didMount();
		
		// Create new JavaFX scene with the rendered nodes.
		javafx.scene.Scene scene = new javafx.scene.Scene(root);
		
		// Show menu to the user.
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Shows the main menu scene to the user.
	 */
	public void showMenu() {
		mount(new Menu(this));
	}

	/**
	 * Shows the quiz scene to the user.
	 *
	 * @param title Title to pass to the Quiz scene.
	 * @param wordlist Wordlist to pass to the Quiz scene.
	 */
	public void showQuiz(String title, Wordlist wordlist) {
		mount(new Quiz(this, title, wordlist));
	}

	/**
	 * Shows the statistics scene to the user.
	 *
	 * @param wordlist Wordlist to pass to the Statistics scene.
	 */
	public void showStatistics(Wordlist wordlist) {
		mount(new Statistics(this, wordlist));
	}
	
	/**
	 * Updates the GUI.
	 * This should be called whenever the state
	 * used to render the scene has changed.
	 */
	public void update() {
		render();
		scene.didUpdate();
	}

	/**
	 * Mounts a and renders a new scene to be shown to the user.
	 * 
	 * @param scene The scene to be mounted.
	 */
	private void mount(Scene scene) {
		// Unmount the old scene.
		this.scene.willUnmount();
		this.scene = scene;
		
		// Run the initial render.
		render();
		scene.didMount();
	}
	
	/**
	 * Renders the scene to the screen.
	 */
	private void render() {
		stage.getScene().setRoot(scene.render());
	}
	
	/**
	 * Gets the width of the scene.
	 *
	 * @return Width of the scene.
	 */
	public double getWidth() {
		return stage.getWidth();
	}
	
	/**
	 * Gets the height of the scene.
	 *
	 * @return Height of the scene.
	 */
	public double getHeight() {
		return stage.getHeight();
	}
	
	/**
	 * Displays a dialog box to the user.
	 *
	 * @param title Title on the dialog box window.
	 * @param message Message on the dialog box.
	 */
	public static void alert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}
