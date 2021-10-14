package application.scene;

import application.Statistics;
import javafx.fxml.FXML;

public class Leaderboard {
	private int score;
	private Statistics stats;

	public void initialise(Statistics statistics) {
		this.stats = stats;
		score = statistics.getScore();

	}
	
	  /** Click handler to go back to finish screen. */
	  @FXML
	  private void back() {
	    SceneManager.switchToFinishScene(stats, false);
	  }

}
