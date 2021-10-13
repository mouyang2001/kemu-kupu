package application.scene;

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

    numCorrectLabel.setText("You got " + stats.getNumCorrect() + " out of 5 correct");
    avTime.setText("You took " + stats.getTotalTime() + " seconds");
  }

  /** click handler to go back to finish screen */
  @FXML
  private void back() {
    SceneManager.switchToFinishScene(stats);
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

    table.add(wordLabel, 0, 0);
    table.add(isCorrectLabel, 1, 0);
    table.add(timeLabel, 2, 0);
    table.add(scoreLabel, 3, 0);

    int lines = 2;
    
    for (Statistic stat : stats.getStats()) {
      Text word = new Text(stat.getWord());
      Text result = new Text(stat.getType().toString());
      Text time = new Text(String.valueOf(stat.getTime()));
      Text score = new Text(String.valueOf(stat.getScore()));
      
      table.add(word, 0, lines);
      table.add(result, 1, lines);
      table.add(time, 2, lines);
      table.add(score, 3, lines);
      
      lines++;
    }
  }
}
