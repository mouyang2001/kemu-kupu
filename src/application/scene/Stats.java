package application.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Stats {

  @FXML private Button back;

  private int score;

  /** @param scoreVal Score at end of quiz. */
  public void initialise(int scoreVal) {
    score = scoreVal;
  }

  /** click handler to go back to finish screen */
  @FXML
  private void back() {
    SceneManager.switchToFinishScene(score);
  }
}
