package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * Main menu for the user to chose what they want to do.
 * Provides navigation to all other scenes.
 */
public class Menu {
	/**
	 * Click handler for the new quiz button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	public void newQuiz(ActionEvent e) throws IOException {
		SceneManager.switchToTopicScene();
	}
	
	/**
	 * Click handler for the quit button.
	 * 
	 * @param e Event action information.
	 */
	public void quitGame(ActionEvent e) {
		SceneManager.closeWindow();
	}
}
