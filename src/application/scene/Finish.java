package application.scene;

import application.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/** Finish screen that shows the users score after the quiz. */
public class Finish {
  @FXML private Label wellDone;

  @FXML private Label scoreTitle;

  @FXML private Label score;

  @FXML private Button quit;

  @FXML private Button playAgain;

  private final double MAX_SCORE = 25.0;

  private int scoreValue;

  /**
   * Shows current score and sets feedback message.
   *
   * @param scoreVal Score at end of quiz.
   */
  public void initialise(int scoreVal) {
    scoreValue = scoreVal;
    setDynamicMessage(scoreVal);
    score.setText(String.valueOf(scoreVal));
  }

  /** Click handler for the new quiz button. */
  @FXML
  private void newQuiz() {
    SceneManager.switchToTopicScene();
  }

  /** Click handler for the stats button. */
  @FXML
  private void showStats() {
    SceneManager.switchToStatsScene(scoreValue);
  }

  /** Click handler for the quit button. */
  @FXML
  private void quit() {
    SceneManager.closeWindow();
  }

  /**
   * Helper method to adjust wellDone label message depending on score
   *
   * @param score Score to base the message off.
   */
  public void setDynamicMessage(int score) {
    double percentage = score / MAX_SCORE;
    wellDone.setStyle("-fx-text-fill: " + "#9AF1A3");
    if (percentage < 0.8) {
      wellDone.setText("Kia Kaha, keep learning!");
    } else {
      wellDone.setText("Ka Pai, great work!");
    }
  }
}
