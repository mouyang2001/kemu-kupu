package application.scene;

import application.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/** Finish screen that shows the users score after the quiz. */
public class Finish {
  @FXML private Label wellDone;

  @FXML private Label scoreTitle;

  @FXML private Label score;
  
  @FXML private Button stats;

  @FXML private Button menu;

  @FXML private Button playAgain;

  @FXML private ImageView image;

  private final double MAX_SCORE = 25.0;

  private Statistics statistics;

  /**
   * Shows current score and sets feedback message.
   *
   * @param scoreVal Score at end of quiz.
   */
  public void initialise(Statistics statistics, boolean playSound) {
    this.statistics = statistics;
    
    score.setText(String.valueOf(statistics.getScore()));
    setDynamicMessage();
    
    if (playSound) {
    	playSound();    	
    }
  }

  /** Click handler for the new quiz button. */
  @FXML
  private void newQuiz() {
    SceneManager.switchToTopicScene();
  }

  /** Click handler for the stats button. */
  @FXML
  private void showStats() {
    SceneManager.switchToStatsScene(statistics);
  }

  /** Click handler for the menu button. */
  @FXML
  private void menu() {
    SceneManager.switchToMenuScene();
  }

  /** Helper method to play celebration song on scene change */
  public void playSound() {
    Sound.play(Sound.Complete);
  }

  /**
   * Helper method to adjust wellDone label message depending on score
   *
   * @param score Score to base the message off.
   */
  public void setDynamicMessage() {
    double percentage = statistics.getScore() / MAX_SCORE;
    
    if (percentage < 0.8) {
      wellDone.setText("Kia Kaha, keep learning!");
    } else {
      wellDone.setText("Ka Pai, great work!");
    }
  }
}
