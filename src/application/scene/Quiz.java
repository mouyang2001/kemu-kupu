package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * Quiz screen that quizzes the users spelling.
 */
public class Quiz {
	/**
	 * Click handler for the submit button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	public void submit(ActionEvent e) throws IOException {
		// if correct
		// TODO: correct popup.
		finished(e);
		
		// if incorrect 1st attempt
		// TODO: incorrect try again popup
		SceneManager.switchToQuizScene();
		
		//if incorrect 2nd attempt
		// TODO: incorrect popup
		finished(e);
	}
	
	/**
	 * Click handler for the skip button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	public void skip(ActionEvent e) throws IOException {
		// TODO: incorrect popup
		finished(e);
	}
	
	/**
	 * When the user has finished the attempts for the current word.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	private void finished(ActionEvent e) throws IOException {
		//if word list is empty
		SceneManager.switchToFinishScene();
		//else
		SceneManager.switchToQuizScene();
	}
	
	public void sound() {
		
	}
}
