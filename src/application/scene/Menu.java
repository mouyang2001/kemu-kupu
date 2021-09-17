package application.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


/**
 * Main menu for the user to chose what they want to do.
 * Provides navigation to all other scenes.
 */
public class Menu extends SceneManager {

	// grab fxml elements by id
	@FXML
	Button quizButton;
	@FXML
	Button quitButton;

	// set on click events to buttons
	public void initialize() {
		// quiz button action
		quizButton.setOnAction(e -> {
			System.out.println("Start new quiz");

			try {
				switchToTopicScene(e);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}

		});

		// quit button action
		quitButton.setOnAction(e -> {
			System.out.println("Quit game");

			try {
				closeWindow(e);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}

		});
	}
}
