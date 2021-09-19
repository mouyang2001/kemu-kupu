package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * Finish screen that shows the
 * users score after the quiz.
 */
public class Finish {
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
	public void quitGame(ActionEvent e) throws IOException {
		SceneManager.closeWindow();
	}
}
