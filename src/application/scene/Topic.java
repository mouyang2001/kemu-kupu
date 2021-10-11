package application.scene;

import application.Words;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/** Topic selection screen for the user to choose which word list to quiz. */
public class Topic {
  @FXML private Label title;

  @FXML private ListView<String> topicListView;

  @FXML private Button back;

  @FXML private Button start;

  @FXML private Button practice;

  /** Called once the controller is loaded. */
  @FXML
  public void initialize() {
    try {
      // Add items to table view.
      topicListView.getItems().add(0, Words.getTopics().get(12));
      topicListView.getItems().addAll(Words.getTopics());

    } catch (IOException e) {
      SceneManager.alert("Could not load topics.");
    }

    // Enable start button once an item is selected.
    topicListView.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        e -> {
          start.setDisable(topicListView.getSelectionModel().getSelectedItem() == null);
          practice.setDisable(topicListView.getSelectionModel().getSelectedItem() == null);
        });
  }

  /** Click handler for the start quiz button. */
  @FXML
  private void startQuiz() {
    SceneManager.switchToQuizScene(topicListView.getSelectionModel().getSelectedItem());
  }

  @FXML
  private void startPractice() {
    SceneManager.switchToPracticeScene(topicListView.getSelectionModel().getSelectedItem());
  }
  ;

  /** Click handler for backing out of topic selection to main menu again. */
  @FXML
  private void leaveQuiz() {
    SceneManager.switchToMenuScene();
  }
}
