package application.scene;

import application.QuizGame.Mode;
import application.Words;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

  @FXML private CheckBox randomTopicCheck;

  /** Called once the controller is loaded. */
  @FXML
  public void initialize() {
    // Try to show topics to the user.
    try {
      topicListView.getItems().addAll(Words.getTopics());
    } catch (IOException e) {
      SceneManager.alert("Could not load topics.");
    }

    // Enable buttons once an item is selected.
    topicListView.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        e -> {
          boolean disabled = getTopic() == null;
          randomTopicCheck.selectedProperty().set(disabled);
          start.setDisable(disabled);
          practice.setDisable(disabled);
        });

    randomTopicCheck
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Disable select from topic listView.
              topicListView.getSelectionModel().select(-1);

              // Make sure random checkbox is ticked.
              start.setDisable(!randomTopicCheck.isSelected());
              practice.setDisable(!randomTopicCheck.isSelected());
            });
  }

  /** Click handler for the start quiz button. */
  @FXML
  private void startQuiz() {
    start(Mode.Game);
  }

  /** Click handler for the start practice quiz button. */
  @FXML
  private void startPractice() {
    start(Mode.Practice);
  }

  /** Click handler for backing out of topic selection to main menu again. */
  @FXML
  private void leaveQuiz() {
    SceneManager.switchToMenuScene();
  }

  /**
   * Start a quiz in the given mode.
   *
   * @param mode The mode to start the quiz in.
   */
  private void start(Mode mode) {
    List<String> topics = topicListView.getItems();

    // Randomly select topic if required.
    String topic =
        randomTopicCheck.isSelected()
            ? topics.get(new Random().nextInt(topics.size()))
            : topicListView.getSelectionModel().getSelectedItem();

    SceneManager.switchToQuizScene(topic, mode);
  }

  /**
   * Get the selected topic from the list view.
   *
   * @return The selected topic.
   */
  private String getTopic() {
    return topicListView.getSelectionModel().getSelectedItem();
  }
}
