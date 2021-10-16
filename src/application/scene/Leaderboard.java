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

  private boolean quiz;

  private Statistics statistics;

  private LeaderboardScore leaderboardScore;

  /**
   * Show the scene based on the leaderboard.
   *
   * @param quiz If being shown after a quiz.
   * @param statistic The stats to return to, if after a quiz.
   * @param leaderboardScore The score obtained on the leaderboard, if after a quiz.
   */
  public void initialise(boolean quiz, Statistics statistics, LeaderboardScore leaderboardScore) {
    this.quiz = quiz;
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
    if (!quiz) {
      label.setText("Leaderboard");
      return;
    }

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
    // Add headers.
    Label positionHeader = new Label("Position");
    Label nameHeader = new Label("Name");
    Label scoreHeader = new Label("Score");
    Label topicHeader = new Label("Topic");

    table.add(positionHeader, 1, 0);
    table.add(nameHeader, 2, 0);
    table.add(scoreHeader, 3, 0);
    table.add(topicHeader, 4, 0);

    // Add each score.
    int i = 1;

    for (LeaderboardScore score : application.Leaderboard.getScores()) {
      Label placeLabel = new Label(placing(score.getPlacing()));
      Label nameLabel = new Label(score.getName());
      Label scoreLabel = new Label(String.valueOf(score.getScore()));
      Label topicLabel = new Label(score.getTopic());

      table.add(placeLabel, 1, i);
      table.add(nameLabel, 2, i);
      table.add(scoreLabel, 3, i);
      table.add(topicLabel, 4, i);

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
    if (quiz) {
      SceneManager.switchToFinishScene(statistics, true);
    } else {
      SceneManager.switchToMenuScene();
    }
  }
}
