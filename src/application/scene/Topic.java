package application.scene;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Topic selection screen for the user
 * to choose which word list to quiz.
 */
public class Topic {
	@FXML
	private Label title;
	
	@FXML
	private ListView<Void> topics;
	
	@FXML
	private Button start;
	
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
