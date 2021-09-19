package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Finish screen that shows the
 * users score after the quiz.
 */
public class Finish {
	@FXML
	private Label wellDone;
	
	@FXML
	private Label scoreTitle;
	
	@FXML
	private Label score;
	
	@FXML
	private Button quit;
	
	@FXML
	private Button playAgain;
	
	/**
	 * Click handler for the new quiz button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void newQuiz(ActionEvent e) throws IOException {
		SceneManager.switchToTopicScene();
	}
	
	/**
	 * Click handler for the quit button.
	 * 
	 * @param e Event action information.
	 */
	@FXML
	public void quit(ActionEvent e) throws IOException {
		SceneManager.closeWindow();
	}
}
