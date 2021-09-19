package application.scene;

import java.io.IOException;
import java.util.ArrayList;

import application.Words;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	ArrayList<String> topics;

	public void initialize() {
		topics = Words.getTopics("words/");
		topicListView.getItems().addAll(topics);

		topicListView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
			String topic = topicListView.getSelectionModel().getSelectedItem();
			System.out.println(topic);
		});
	}
	
	/**
	 * Click handler for the start quiz button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	public void startQuiz(ActionEvent e) throws IOException {
		String topic = topicListView.getSelectionModel().getSelectedItem();
		if (topic == null) {
			System.out.println("Please select an option");
		}
//		SceneManager.switchToQuizScene();
	}

	/**
	 * Click handler for backing out of topic selection to main menu again.
	 *
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	public void leaveQuiz() throws IOException {
		SceneManager.switchToMenuScene();
	}

}
