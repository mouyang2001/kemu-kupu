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
	 * This must be called before an other show... methods.
	 */
	public void show() {
		// Render menu scene.
		Parent root = new Menu().render(this);
		
		// Create new JavaFX scene with the rendered nodes.
		javafx.scene.Scene scene = new javafx.scene.Scene(root);
		
		// Show scene to the user.
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Shows the main menu scene to the user.
	 */
	public void showMenu() {
		this.setScene(new Menu());
	}

	/**
	 * Shows the quiz scene to the user.
	 *
	 * @param title Title to pass to the Quiz scene.
	 * @param wordlist Wordlist to pass to the Quiz scene.
	 */
	public void showQuiz(String title, Wordlist wordlist) {
		this.setScene(new Quiz(title, wordlist));
	}

	/**
	 * Shows the statistics scene to the user.
	 *
	 * @param wordlist Wordlist to pass to the Statistics scene.
	 */
	public void showStatistics(Wordlist wordlist) {
		this.setScene(new Statistics(wordlist));
	}

	/**
	 * Shows the provided scene to the user.
	 *
	 * @param scene Scene to display to the user.
	 */
	private void setScene(Scene scene) {
		stage.getScene().setRoot(scene.render(this));
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
	
	/**
	 * Gets the width of the window.
	 *
	 * @return Width of the window.
	 */
	public double getWidth() {
		return stage.getWidth();
	}
	
	/**
	 * Gets the height of the window.
	 *
	 * @return Height of the window.
	 */
	public double getHeight() {
		return stage.getHeight();
	}
}
