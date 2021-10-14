package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LeaderboardControl {
  private int score;
  private int place;

  private Highscore first;
  private Highscore second;
  private Highscore third;
  private Highscore player;
  private File file;

  private boolean winner;

  /**
   * initialise with current score
   *
   * @param score
   */
  public LeaderboardControl(int score) {
    this.score = score;
    try {
      initialise();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    winner = true;
    check();
  }
  /**
   * initialse with current highscores
   *
   * @throws FileNotFoundException
   */
  private void initialise() throws FileNotFoundException {
    bash("mkdir -p .stats");
    bash("touch .stats/.leaderboard.txt");
    file = new File("./.stats/.leaderboard.txt");
    if (file.length() == 0) {
      first = new Highscore(null, 0);
      second = new Highscore(null, 0);
      third = new Highscore(null, 0);
    } else {
      Scanner scanner = new Scanner(file);
      first = new Highscore(scanner.next(), scanner.nextInt());
      second = new Highscore(scanner.next(), scanner.nextInt());
      third = new Highscore(scanner.next(), scanner.nextInt());
      scanner.close();
    }
  }

  /*
   * check if player beat any highscores and update placings
   */
  private void check() {
    if (score > first.getScore()) {
      place = 1;
      third.setName(second.getName());
      third.setScore(second.getScore());
      second.setName(first.getName());
      second.setScore(first.getScore());
      player = first;
    } else if (score > second.getScore()) {
      place = 2;
      third.setName(second.getName());
      third.setScore(second.getScore());
      player = second;
    } else if (score > third.getScore()) {
      place = 3;
      player = third;
    } else {
      place = 4;
      winner = false;
    }
  }

  /**
   * update leaderboard file with new scores
   *
   * @throws IOException
   */
  public void updateFile() throws IOException {
    bash("rm .stats/.leaderboard.txt");
    bash("touch .stats/.leaderboard.txt");
    FileWriter fw = new FileWriter(file, true);
    BufferedWriter bw = new BufferedWriter(fw);
    System.out.println(first.getName());
    System.out.println(second.getName());
    bw.write(first.getName() + System.lineSeparator());
    bw.write(first.getScore() + System.lineSeparator());
    bw.write(second.getName() + System.lineSeparator());
    bw.write(second.getScore() + System.lineSeparator());
    bw.write(third.getName() + System.lineSeparator());
    bw.write(third.getScore() + System.lineSeparator());

    bw.close();
  }

  /**
   * getter for result
   *
   * @return if player beat a highscore
   */
  public boolean isWinner() {
    return winner;
  }

  /**
   * setter method for name
   *
   * @param player name
   */
  public void setName(String name) {
    player.setName(name);
    player.setScore(score);
    try {
      updateFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * getter method for place
   *
   * @return player place
   */
  public int getPlace() {
    return place;
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
}
