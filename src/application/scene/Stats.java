package application.scene;

import application.QuizGame;
import application.Statistic;
import application.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/** The stats screen with the results of the quiz. */
public class Stats {
  @FXML private Button back;

  @FXML private GridPane table;

  @FXML private Label scoreLabel;

  @FXML private Label numCorrectLabel;

  @FXML private Label avTime;

  private Statistics stats;

  /**
   * Initialise the screen with the stats.
   *
   * @param stats The stats to show.
   */
  public void initialise(Statistics stats) {
    this.stats = stats;

    displayMessages();
    loadTable();
  }

  /** Click handler to go back to finish screen. */
  @FXML
  private void back() {
    SceneManager.switchToFinishScene(stats, false);
  }

  /** Display the score and overall statistics to the user. */
  private void displayMessages() {
    scoreLabel.setText(String.valueOf(stats.getScore()));
    numCorrectLabel.setText(
        "You got " + stats.getNumCorrect() + " out of " + QuizGame.NUMBER_OF_ROUNDS + " correct");
    avTime.setText("You took " + formatTime(stats.getTotalTime()) + " seconds");
  }

  /** Show the statistics of each question to the user. */
  private void loadTable() {
    // Add headers.
    Label wordLabel = new Label("Word");
    Label isCorrectLabel = new Label("Result");
    Label timeLabel = new Label("Time (s)");
    Label scoreLabel = new Label("Score");

    table.add(wordLabel, 1, 0);
    table.add(isCorrectLabel, 2, 0);
    table.add(timeLabel, 3, 0);
    table.add(scoreLabel, 4, 0);

    // Add each statistic.
    int lines = 2;

    for (Statistic stat : stats.getStats()) {
      Text word = new Text(stat.getWord());
      Text result = new Text(stat.getType().toString());
      Text time = new Text(formatTime(stat.getTime()));
      Text score = new Text(String.valueOf(stat.getScore()));

      table.add(word, 1, lines);
      table.add(result, 2, lines);
      table.add(time, 3, lines);
      table.add(score, 4, lines);

      lines++;
    }
  }

  /**
   * Format a time in seconds to 2 decimal places.
   *
   * @param time The time to format.
   * @return The formatted time.
   */
  private String formatTime(float time) {
    return String.format("%.02f", time);
  }
}
