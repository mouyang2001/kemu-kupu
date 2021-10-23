package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Stored leaderboard of the top 3 players across all quizzes. */
public class Leaderboard {
  private static Path file = Paths.get(".stats", "leaderboard");

  private static List<LeaderboardScore> scores;

  /**
   * Gets the scores on the leaderboard in descending order of score.
   *
   * @return List of scores on the leaderboard.
   * @throws IOException If and I/O error occurred.
   */
  public static List<LeaderboardScore> getScores() throws IOException {
    read();

    return scores;
  }

  /**
   * Add a score to the leaderboard if it is high enough.
   *
   * @param name The name of the user.
   * @param score The score the user obtained.
   * @param topic The topic that was quizzed.
   * @return The leaderboard score if the score was high enough, otherwise null.
   * @throws IOException If an I/O error occurred.
   */
  public static LeaderboardScore add(String name, int score, String topic) throws IOException {
    read();

    LeaderboardScore leaderboardScore = new LeaderboardScore(1, name, score, topic);

    scores.add(leaderboardScore);

    // Sort by highest score.
    scores.sort((a, b) -> a.getScore() > b.getScore() ? -1 : a.getScore() == b.getScore() ? 0 : 1);

    // Remove the lowest score if it isn't
    // on the leaderboard anymore.
    if (scores.size() > 3) {
      scores.remove(scores.size() - 1);
    }

    // Correct the placings.
    for (int i = 0; i < scores.size(); i++) {
      scores.get(i).setPlacing(i + 1);
    }

    write();

    return leaderboardScore;
  }

  /**
   * Checks if a score will make the leaderboard.
   *
   * @param score The score to check if it makes the leaderboard.
   * @return If the score makes the leaderboard.
   * @throws IOException If an I/O error occurred.
   */
  public static boolean makesLeaderboard(int score) throws IOException {
    read();

    if (scores.isEmpty()) {
      return true;
    }

    return score > scores.get(scores.size() - 1).getScore();
  }

  /**
   * Read the scores from the leaderboard file.
   *
   * @throws IOException If an I/O error occurred.
   */
  private static void read() throws IOException {
    if (scores != null) {
      return;
    }

    scores = new ArrayList<>();

    // Treat a non existent file
    // the same as an empty one.
    if (Files.notExists(file)) {
      return;
    }

    int placing = 1;

    for (String line : Files.readAllLines(file)) {
      // Scores are stores as "Name|Score|Topic".
      String[] partition = line.split("[|]");
      String name = partition[0];
      int score = Integer.parseInt(partition[1]);
      String topic = partition[2];

      scores.add(new LeaderboardScore(placing, name, score, topic));

      placing++;
    }
  }

  /**
   * Write the scores to the leaderboard file.
   *
   * @throws IOException If an I/O error occurred.
   */
  private static void write() throws IOException {
    // Create parent directories and file if required.
    Files.createDirectories(file.getParent());

    if (Files.notExists(file)) {
      Files.createFile(file);
    }

    // Store scores as "Name|Score|Topic".
    List<String> lines =
        scores.stream()
            .limit(3)
            .map(score -> score.getName() + "|" + score.getScore() + "|" + score.getTopic())
            .collect(Collectors.toList());

    Files.write(file, lines);
  }
}
