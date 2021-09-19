package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Quiz screen that quizzes the users spelling.
 */
public class Quiz {
	@FXML
	private Label score;
	
	@FXML
	private Label correct;
	
	@FXML
	private Button sound;
	
	@FXML
	private Button skip;
	
	@FXML
	private Button submit;
	
	@FXML
	private TextField input;
	
	@FXML
	private Label hint;

	/**
	 * Method allows SceneManager to access and transfer data topic selection data.
	 */
	public void beginQuiz(String topic) {
		System.out.println("begin quiz with: " + topic);
		// TODO: begin a new QuizGame instance
		// eg QuizGame = new QuizGame(topic: ... );
	}
	
	/**
	 * Click handler for the submit button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void submit(ActionEvent e) throws IOException {
//		// if correct
//		// TODO: correct popup.
//		finished(e);
//		
//		// if incorrect 1st attempt
//		// TODO: incorrect try again popup
//		SceneManager.switchToQuizScene();
//		
//		//if incorrect 2nd attempt
//		// TODO: incorrect popup
//		finished(e);
		
		SceneManager.switchToFinishScene();
	}
	
	/**
	 * Click handler for the skip button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void skip(ActionEvent e) throws IOException {
		// TODO: incorrect popup
//		finished(e);
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
	}
	
	/**
	 * Click handler for the sound button.
	 * 
	 * @param e Event action information.
	 */
	@FXML
	public void sound(ActionEvent e) {
		
	}
}
