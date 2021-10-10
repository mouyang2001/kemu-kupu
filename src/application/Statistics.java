package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/** Stats from single spelling quiz * */
public class Statistics {

  private int score;

  private float time;

  private String word;

  private String type;

  private File statsFile;

  /**
   * set word currently being tested
   *
   * @param word
   */
  public void setWord(String word) {
    this.word = word;
  }

  /**
   * set score of word just tested
   *
   * @param score
   */
  public void setScore(int score) {
    this.score = score;
    setType();
  }

  /**
   * set time taken to answer word just tested
   *
   * @param time
   */
  public void setTime(float time) {
    this.time = time;
  }

  /** helper method to identify if word was correct, incorrect or skipped */
  public void setType() {
    if (score < 0) {
      type = "Skipped";
    } else if (score == 0) {
      type = "Incorrect";
    } else {
      type = "Correct";
    }
  }

  /** creates files used to store stats */
  public void makeFiles() {
    bash("mkdir -p .stats");
    bash("rm ./.stats/.words.txt");
    bash("touch .stats/.words.txt");
    statsFile = new File("./.stats/.words.txt");
  }

  /**
   * helper function for executing bash commands
   *
   * @param command to execute
   */
  public void bash(String command) {
    try {
      ProcessBuilder builder = new ProcessBuilder("bash", "-c", command);
      Process process = builder.start();

      BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
      BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

      int exitStatus = process.waitFor();

      if (exitStatus == 0) {
        String line;
        while ((line = stdout.readLine()) != null) {
          System.out.println(line);
        }
      } else {
        String line;
        while ((line = stderr.readLine()) != null) {
          System.err.println(line);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Append a word to a statistic file.
   *
   * @param file The file to add the word to.
   * @param word The word to add into the file.
   * @throws IOException If an I/O error occurs.
   */
  public void append() throws IOException {
    FileWriter fw = new FileWriter(statsFile, true);
    BufferedWriter bw = new BufferedWriter(fw);

    // Write the word to its own line.
    bw.write(word + System.lineSeparator());
    bw.write(type + System.lineSeparator());
    bw.write(String.valueOf(time) + System.lineSeparator());
    bw.write(String.valueOf(score) + System.lineSeparator());

    bw.close();
  }
}
