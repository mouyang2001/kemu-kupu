package application.scene;

import application.QuizGame;
import application.Statistic;
import application.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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
    SceneManager.switchToFinishScene(stats, true);
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
    Label wordHeader = new Label("Word");
    Label isCorrectHeader = new Label("Result");
    Label timeHeader = new Label("Time (s)");
    Label scoreHeader = new Label("Score");

    table.add(wordHeader, 1, 0);
    table.add(isCorrectHeader, 2, 0);
    table.add(timeHeader, 3, 0);
    table.add(scoreHeader, 4, 0);

    // Add each statistic.
    int i = 2;

    for (Statistic stat : stats.getStats()) {
      Label word = new Label(stat.getWord());
      Label result = new Label(stat.getType().toString());
      Label time = new Label(formatTime(stat.getTime()));
      Label score = new Label(String.valueOf(stat.getScore()));

      table.add(word, 1, i);
      table.add(result, 2, i);
      table.add(time, 3, i);
      table.add(score, 4, i);

      i++;
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
