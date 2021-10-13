package application.scene;

import application.QuizGame;
import application.Statistic;
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
  
  private Statistics stats;

  /** @param scoreVal Score at end of quiz. */
  public void initialise(Statistics stats) {
    this.stats = stats;
	  
    table();

    numCorrectLabel.setText("You got " + stats.getNumCorrect() + " out of " + QuizGame.NUMBER_OF_ROUNDS + " correct");
    avTime.setText("You took " + formatTime(stats.getTotalTime()) + " seconds");
  }

  /** click handler to go back to finish screen */
  @FXML
  private void back() {
    SceneManager.switchToFinishScene(stats, false);
  }
  
  private String formatTime(float time) {
	  return String.format("%.02f", time);
  }

  /**
   * function to populate tableview
   *
   * @throws FileNotFoundException
   */
  private void table() {
    Label wordLabel = new Label("Word");
    Label isCorrectLabel = new Label("Result");
    Label timeLabel = new Label("Time (s)");
    Label scoreLabel = new Label("Score");

    table.add(wordLabel, 1, 0);
    table.add(isCorrectLabel, 2, 0);
    table.add(timeLabel, 3, 0);
    table.add(scoreLabel, 4, 0);

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
}
