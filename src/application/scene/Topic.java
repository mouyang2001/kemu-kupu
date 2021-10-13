package application.scene;

import application.Words;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/** Topic selection screen for the user to choose which word list to quiz. */
public class Topic {
  @FXML private Label title;

  @FXML private ListView<String> topicListView;

  @FXML private Button back;

  @FXML private Button start;

  @FXML private Button practice;

  @FXML private CheckBox randomTopicCheck;

  private List<String> topics;

  /** Called once the controller is loaded. */
  @FXML
  public void initialize() {
    // Try populate ListView.
    try {
      topics = Words.getTopics();
      topicListView.getItems().addAll(topics);
      buttonSetUp();
    } catch (IOException e) {
      SceneManager.alert("Could not load topics.");
    }

    // Enable buttons once an item is selected.

    topicListView.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        e -> {
          // Disable random check button.
          randomTopicCheck.selectedProperty().set(false);

          // Make sure a topic is selected.
          start.setDisable(topicListView.getSelectionModel().getSelectedItem() == null);
          practice.setDisable(topicListView.getSelectionModel().getSelectedItem() == null);
        });

    randomTopicCheck
        .selectedProperty()
        .addListener(
            (observableValue, aBoolean, t1) -> {
              // Disable select from topic listView.
              topicListView.getSelectionModel().select(-1);

              // Make sure random checkbox is ticked.
              start.setDisable(!randomTopicCheck.isSelected());
              practice.setDisable(!randomTopicCheck.isSelected());
            });
  }
  
  private void buttonSetUp() {
	  Tooltip quiz = new Tooltip("pātaitai | test your knowledge");
	  quiz.setStyle("-fx-font-size: 20");
	  start.setTooltip(quiz);
	  
	  Tooltip practise = new Tooltip("whakaharatau | practise your spelling");
	  practise.setStyle("-fx-font-size: 20");
	  practice.setTooltip(practise);
  }

  /** Click handler for the start quiz button. */
  @FXML
  private void startQuiz() {
    String topic =
        (randomTopicCheck.isSelected())
            ? topics.get(new Random().nextInt(topics.size()))
            : topicListView.getSelectionModel().getSelectedItem();
    SceneManager.switchToQuizScene(topic);
  }

  /** Click handler for the start practice button. */
  @FXML
  private void startPractice() {
    String topic =
        (randomTopicCheck.isSelected())
            ? topics.get(new Random().nextInt(topics.size()))
            : topicListView.getSelectionModel().getSelectedItem();
    SceneManager.switchToPracticeScene(topic);
  }

  /** Click handler for backing out of topic selection to main menu again. */
  @FXML
  private void leaveQuiz() {
    SceneManager.switchToMenuScene();
  }
}
