package application.scene;

import javafx.event.ActionEvent;
import java.io.IOException;


/**
 * Main menu for the user to chose what they want to do.
 * Provides navigation to all other scenes.
 */
public class Menu extends SceneManager {

	// this method is for pre loading things
	public void initialize() {
		
	}

	// new quiz action
	public void newQuiz(ActionEvent e) throws IOException {
		switchToTopicScene(e);
	}

	// quit game action
	public void quitGame(ActionEvent e) throws IOException {
		closeWindow(e);
	}
}
