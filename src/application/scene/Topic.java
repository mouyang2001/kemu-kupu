package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * Topic selection screen for the user
 * to choose which word list to quiz.
 */
public class Topic {
	/**
	 * Click handler for the start quiz button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	public void startQuiz(ActionEvent e) throws IOException {
		SceneManager.switchToQuizScene();
	}
	
	// TODO: functionality of choosing topic from list
}
