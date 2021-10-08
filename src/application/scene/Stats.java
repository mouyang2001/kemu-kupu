package application.scene;

import application.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Stats {

  @FXML private Button back;

  private int score;
  
  private int[] scores;

  private long[] times;

  private String[] words;
  
  private Statistics stats;

  /** @param scoreVal Score at end of quiz. */
  public void initialise(int scoreVal) {
    score = scoreVal;
    stats = new Statistics();
    scores = stats.getScores();
    times = stats.getTimes();
    words = stats.getWords();
  }

  /** click handler to go back to finish screen */
  @FXML
  private void back() {
    SceneManager.switchToFinishScene(score);
  }

  /** function to populate tableview */
  private void table() {
	  for (int i = 0; i < 5; i++) {
		  
	  }
  }
  	
}
