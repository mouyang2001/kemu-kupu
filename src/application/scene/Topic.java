package application.scene;

import java.io.IOException;

import application.Words;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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

	/**
	 * Called once the controller is loaded.
	 */
	@FXML
	public void initialize() {
		// Add items to table view.
		try {
			topicListView.getItems().addAll(Words.getTopics());
		} catch (IOException e) {
			// TODO: Error handling.
		}
		
		// Enable start button once an item is selected.
		topicListView.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			start.setDisable(topicListView.getSelectionModel().getSelectedItem() == null);
		});
	}

	/**
	 * Click handler for the start quiz button.
	 * 
	 * @param e Event action information.
	 * @throws IOException If FXML or CSS resources fail to load.
	 */
	@FXML
	public void startQuiz(ActionEvent e) throws IOException {
		SceneManager.switchToQuizScene(topicListView.getSelectionModel().getSelectedItem());
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
