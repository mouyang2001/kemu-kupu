package application.scene;

import application.Leaderboard;
import application.LeaderboardScore;
import application.QuizGame;
import application.Statistics;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
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

  private final double MAX_SCORE = QuizGame.MAX_SCORE * QuizGame.NUMBER_OF_ROUNDS;

  private Statistics statistics;
  
  private LeaderboardScore leaderboardScore;

  /**
   * Show the finish screen based on the statistics of the quiz.
   *
   * @param statistics Statistics of the quiz.
   * @param returning If returning to the finish screen.
   */
  public void initialise(Statistics statistics, boolean returning) {
    this.statistics = statistics;

    setMessages();

    // Only play sound and update leaderboard if it
    // is the first time showing the finish screen.
    if (!returning) {
      Sound.play(Sound.Complete);
      
      addToLeaderboard();
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

  /** Click handler for the leaderboard button. */
  @FXML
  private void leaderboard() {
    SceneManager.switchToLeaderboardScene(statistics, leaderboardScore);
  }

  /** Click handler for the menu button. */
  @FXML
  private void menu() {
    SceneManager.switchToMenuScene();
  }

  /** Show score and message based off the score. */
  private void setMessages() {
    score.setText(String.valueOf(statistics.getScore()));

    double percentage = statistics.getScore() / MAX_SCORE;

    if (percentage < 0.8) {
      wellDone.setText("Kia Kaha, keep learning!");
    } else {
      wellDone.setText("Ka Pai, great work!");
    }
  }

  /** Prompt the user for their name and add them to the leaderboard. */
  private void addToLeaderboard() {
	try {
	  if (!Leaderboard.makesLeaderboard(statistics.getScore())) {
		return;
	  }
	} catch (IOException e) {
	  SceneManager.alert("Could not load leaderboard");
	}
	
    // Prompt user for their name.
	TextInputDialog dialog = new TextInputDialog("");
    dialog.setTitle("Congratulations!");
    dialog.setHeaderText("You set a new highscore!");
    dialog.setContentText("Please enter your name:");
    
    String name = dialog.showAndWait().orElseGet(() -> "Unknown Player");
    
    // Try updating the leaderboard.
    try {
      leaderboardScore = Leaderboard.add(name, statistics.getScore());
    } catch (IOException e) {
      SceneManager.alert("Could not update leaderboard");
    }
  }
}
