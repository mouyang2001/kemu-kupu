package application.scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Stats {

  @FXML private Button back;

  @FXML private GridPane table;

  @FXML private Label numCorrectLabel;

  @FXML private Label avTime;

  @FXML private Label scoreLabel;

  private int score;

  private int numCorrect;

  private double timeTotal;

  /** @param scoreVal Score at end of quiz. */
  public void initialise(int scoreVal) {
    numCorrect = 0;
    timeTotal = 0.0;
    score = scoreVal;
    try {
      table();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    DecimalFormat df = new DecimalFormat("#.##");
    scoreLabel.setText(String.valueOf(score));
    numCorrectLabel.setText("You got " + numCorrect + " out of 5 correct");
    avTime.setText("You took " + df.format(timeTotal) + " seconds");
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
    Label timeLabel = new Label("Time (s)");
    Label scoreLabel = new Label("Score");

    table.add(wordLabel, 1, 0);
    table.add(isCorrectLabel, 2, 0);
    table.add(timeLabel, 3, 0);
    table.add(scoreLabel, 4, 0);

    File statsFile = new File("./.stats/.words.txt");
    Scanner scanner = new Scanner(statsFile);
    int lines = 1;

    while (scanner.hasNext()) {
      lines++;
      Label word = new Label(scanner.next());
      String typeString = scanner.next();
      if (typeString.equals("Correct")) {
        numCorrect++;
      }
      Label type = new Label(typeString);
      String timeString = scanner.next();
      Label time = new Label(timeString);
      timeTotal += Float.parseFloat(timeString);
      Label score = new Label(scanner.next());

      table.add(word, 1, lines);
      table.add(type, 2, lines);
      table.add(time, 3, lines);
      table.add(score, 4, lines);
    }
    scanner.close();
  }
}
