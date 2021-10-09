package application.scene;

import application.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Stats {

  @FXML private Button back;

  @FXML private GridPane table;

  @FXML private Label numCorrectLabel;

  @FXML private Label avTime;

  private int score;

  private int numCorrect;

  private long timeTotal;

  private int[] scores;

  private long[] times;

  private String[] words;

  private Statistics stats;

  /** @param scoreVal Score at end of quiz. */
  public void initialise(int scoreVal, Statistics stats) {
    numCorrect = 0;
    timeTotal = 0;
    score = scoreVal;
    // stats = new Statistics();
    scores = stats.getScores();
    times = stats.getTimes();
    words = stats.getWords();
    table();
    numCorrectLabel.setText("You got " + numCorrect + " out of 5 correct");
    avTime.setText("You took " + timeTotal + " milliseconds");
  }

  /** click handler to go back to finish screen */
  @FXML
  private void back() {
    SceneManager.switchToFinishScene(score, stats);
  }

  /** function to populate tableview */
  private void table() {
    Label wordLabel = new Label("Word");
    Label isCorrectLabel = new Label("Result");
    Label timeLabel = new Label("Time (ms)");
    Label scoreLabel = new Label("Score");

    table.add(wordLabel, 0, 0);
    table.add(isCorrectLabel, 1, 0);
    table.add(timeLabel, 2, 0);
    table.add(scoreLabel, 3, 0);

    for (int i = 0; i < 5; i++) {
      timeTotal += times[i];
      Text wordText = new Text(words[i]);
      Text correctText;
      Text timeText = new Text(String.valueOf(times[i]));
      Text scoreText = new Text(String.valueOf(scores[i]));
      if (scores[i] > 0) {
        correctText = new Text("Correct");
        numCorrect++;
      } else if (scores[i] == 0) {
        correctText = new Text("Incorrect");
      } else {
        correctText = new Text("Skipped");
        scoreText = new Text("0");
      }

      table.add(wordText, 0, i + 1);
      table.add(correctText, 1, i + 1);
      table.add(timeText, 2, i + 1);
      table.add(scoreText, 3, i + 1);
    }
  }
}
