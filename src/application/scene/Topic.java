package application.scene;

import java.io.IOException;

import application.Words;
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
	private ListView<String> topicListView;
	
	@FXML
	private Button start;

	@FXML
	public void initialize() {
		topicListView.getItems().addAll(Words.getTopics());
	}

	/**
	 * Click handler for the start quiz button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void startQuiz(ActionEvent e) throws IOException {
		String topic = topicListView.getSelectionModel().getSelectedItem();
		if (topic == null) {
			// TODO: add a prompt to user to select an option?
			System.out.println("Please select an option");
		} else {
			SceneManager.switchToQuizScene(topic);
		}
	}

	/**
	 * Click handler for backing out of topic selection to main menu again.
	 *
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void leaveQuiz() throws IOException {
		SceneManager.switchToMenuScene();
	}

}
