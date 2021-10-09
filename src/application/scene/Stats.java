package application.scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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

  /** @param scoreVal Score at end of quiz. */
  public void initialise(int scoreVal) {
    numCorrect = 0;
    timeTotal = 0;
    score = scoreVal;
    try {
      table();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    numCorrectLabel.setText("You got " + numCorrect + " out of 5 correct");
    avTime.setText("You took " + timeTotal + " milliseconds");
  }

  /** click handler to go back to finish screen */
  @FXML
  private void back() {
    SceneManager.switchToFinishScene(score);
  }

  /**
   * function to populate tableview
   *
   * @throws FileNotFoundException
   */
  private void table() throws FileNotFoundException {
    Label wordLabel = new Label("Word");
    Label isCorrectLabel = new Label("Result");
    Label timeLabel = new Label("Time (ms)");
    Label scoreLabel = new Label("Score");

    table.add(wordLabel, 0, 0);
    table.add(isCorrectLabel, 1, 0);
    table.add(timeLabel, 2, 0);
    table.add(scoreLabel, 3, 0);

    File statsFile = new File("./.stats/.words.txt");
    Scanner scanner = new Scanner(statsFile);
    int lines = 1;

    while (scanner.hasNext()) {
      lines++;
      Text word = new Text(scanner.next());
      Text type = new Text(scanner.next());
      Text time = new Text(scanner.next());
      Text score = new Text(scanner.next());

      table.add(word, 0, lines);
      table.add(type, 1, lines);
      table.add(time, 2, lines);
      table.add(score, 3, lines);
    }
  }
}
