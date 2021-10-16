package application.scene;

import application.LeaderboardScore;
import application.Statistics;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/** Leaderboard scene that shows the top scores from the quiz. */
public class Leaderboard {
  @FXML private Button back;

  @FXML private Label label;

  @FXML private GridPane table;
  
  private Statistics statistics;
  
  private LeaderboardScore leaderboardScore;

  /**
   * Show the scene based on the leaderboard.
   * 
   * @param statistic The stats to return to.
   * @param leaderboardScore The score obtained on the leaderboard.
   */
  public void initialise(Statistics statistics, LeaderboardScore leaderboardScore) {
    this.statistics = statistics;
    this.leaderboardScore = leaderboardScore;

    try {
	  showMessage();
	  showLeaderboard();
    } catch (IOException e) {
      SceneManager.alert("Could not load leaderboard");
    }
  }
  
  /**
   * Helper method to print message depending on your score.
   * 
   * @throws IOException If an I/O error occured.
   */
  private void showMessage() throws IOException {
    if (leaderboardScore == null) {
      label.setText("You didn't set a new high score.\nPractise more and try again!");
      return;
    }
    
    switch (leaderboardScore.getPlacing()) {
	    case 1:
	    	label.setText("Congratulations!\nYou set a new high score!");
	    	break;
	    case 2:
	    	label.setText("Congratulations!\nYou came 2nd!");
	    	break;
	    case 3:
	    	label.setText("Congratulations!\nYou came 3rd!");
	    	break;
    }
  }

  /**
   * Populate the leaderboard with the top scores.
   * 
   * @throws IOException If an I/O error occurred.
   */
  private void showLeaderboard() throws IOException {
	int i = 0;
	
	for (LeaderboardScore score : application.Leaderboard.getScores()) {
		Label placeLabel = new Label(placing(score.getPlacing()));
		Label nameLabel = new Label(score.getName());
		Label scoreLabel = new Label(String.valueOf(score.getScore()));

		table.add(placeLabel, 1, i);
		table.add(nameLabel, 2, i);
		table.add(scoreLabel, 3, i);
		
		i++;
	}
  }
  
  /**
   * Gets the suffixed placing from the corresponding integer.
   * 
   * @param place The placing from 1-3 inclusive.
   * @return The suffixed placing.
   */
  private String placing(int place) {
	  switch (place) {
		  case 1:
			  return "1st";
		  case 2:
			  return "2nd";
		  case 3:
			  return "3rd";
		  default:
			  throw new IllegalArgumentException("Placing out of range");
	  }
  }

  /** Click handler to go back to finish screen. */
  @FXML
  private void back() {
    SceneManager.switchToFinishScene(statistics, true);
  }
}
