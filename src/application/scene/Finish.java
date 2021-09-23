package application.scene;

import javafx.event.ActionEvent;
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

  /*
   * Shows current score.
   *
   * @param scoreVal Score at end of quiz.
   */
  public void initialise(int scoreVal) {
    score.setText(String.valueOf(scoreVal));
  }

  /**
   * Click handler for the new quiz button.
   *
   * @param e Event action information.
   */
  @FXML
  public void newQuiz(ActionEvent e) {
    SceneManager.switchToTopicScene();
  }

  /**
   * Click handler for the quit button.
   *
   * @param e Event action information.
   */
  @FXML
  public void quit(ActionEvent e) {
    SceneManager.closeWindow();
  }
}
