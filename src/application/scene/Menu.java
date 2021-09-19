package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * Main menu for the user to chose what they want to do.
 * Provides navigation to all other scenes.
 */
public class Menu {
	// this method is for pre loading things
	public void initialize() {

	}

	// new quiz action
	public void newQuiz(ActionEvent e) throws IOException {
		SceneManager.switchToTopicScene();
	}

	// quit game action
	public void quitGame(ActionEvent e) {
		SceneManager.closeWindow();
	}
}
